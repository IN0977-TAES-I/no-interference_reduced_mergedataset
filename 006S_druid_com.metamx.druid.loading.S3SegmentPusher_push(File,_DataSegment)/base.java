// class name : com.metamx.druid.loading.S3SegmentPusher
// method name: push(File, DataSegment)
// side       : base
// region     : lines 66-139 (affected lines: [66, 105, 110, 118, 125, 139])
class S3SegmentPusher {
    void push_File__DataSegment_() {
  public DataSegment push(File file, DataSegment segment) throws IOException
  {
    log.info("Uploading [%s] to S3", file);
    String outputKey = JOINER.join(
        config.getBaseKey().isEmpty() ? null : config.getBaseKey(),
        segment.getDataSource(),
        String.format(
            "%s_%s",
            segment.getInterval().getStart(),
            segment.getInterval().getEnd()
        ),
        segment.getVersion(),
        segment.getShardSpec().getPartitionNum()
    );

    File indexFilesDir = file;

    long indexSize = 0;
    final File zipOutFile = File.createTempFile("druid", "index.zip");
    ZipOutputStream zipOut = null;
    try {
      zipOut = new ZipOutputStream(new FileOutputStream(zipOutFile));
      File[] indexFiles = indexFilesDir.listFiles();
      for (File indexFile : indexFiles) {
        log.info("Adding indexFile[%s] with size[%,d].  Total size[%,d]", indexFile, indexFile.length(), indexSize);
        if (indexFile.length() >= Integer.MAX_VALUE) {
            throw new ISE("indexFile[%s] too large [%,d]", indexFile, indexFile.length());
        }
        zipOut.putNextEntry(new ZipEntry(indexFile.getName()));
        IOUtils.copy(new FileInputStream(indexFile), zipOut);
        indexSize += indexFile.length();
      }
    }
    finally {
      Closeables.closeQuietly(zipOut);
    }

    try {
      S3Object toPush = new S3Object(zipOutFile);

      final String outputBucket = config.getBucket();
      toPush.setBucketName(outputBucket);
      toPush.setKey(outputKey + "/index.zip");

      log.info("Pushing %s.", toPush);
      s3Client.putObject(outputBucket, toPush);

      DataSegment outputSegment = segment.withSize(indexSize)
                                         .withLoadSpec(
                                             ImmutableMap.<String, Object>of(
                                                 "type", "s3_zip",
                                                 "bucket", outputBucket,
                                                 "key", toPush.getKey()
                                             )
                                         );

      File descriptorFile = File.createTempFile("druid", "descriptor.json");
      StreamUtils.copyToFileAndClose(new ByteArrayInputStream(jsonMapper.writeValueAsBytes(segment)), descriptorFile);
      S3Object descriptorObject = new S3Object(descriptorFile);
      descriptorObject.setBucketName(outputBucket);
      descriptorObject.setKey(outputKey + "/descriptor.json");

      log.info("Pushing %s", descriptorObject);
      s3Client.putObject(outputBucket, descriptorObject);

      log.info("Deleting Index File[%s]", indexFilesDir);
      FileUtils.deleteDirectory(indexFilesDir);

      log.info("Deleting zipped index File[%s]", zipOutFile);
      zipOutFile.delete();

      log.info("Deleting descriptor file[%s]", descriptorFile);
      descriptorFile.delete();

    }
}
