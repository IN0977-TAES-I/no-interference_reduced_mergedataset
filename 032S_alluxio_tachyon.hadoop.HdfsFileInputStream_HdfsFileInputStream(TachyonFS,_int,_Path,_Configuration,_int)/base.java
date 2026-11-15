class HdfsFileInputStream {
    static Logger LOG;
    long mCurrentPosition;
    TachyonFS mTFS;
    int mFileId;
    Path mHdfsPath;
    Configuration mHadoopConf;
    int mHadoopBufferSize;
    InStream mTachyonFileInputStream;

    public HdfsFileInputStream(TachyonFS tfs, int fileId, Path hdfsPath, Configuration conf,
        int bufferSize) throws IOException {
      LOG.debug("PartitionInputStreamHdfs(" + tfs + ", " + fileId + ", " + hdfsPath + ", " + conf
          + ", " + bufferSize + ")");
      mCurrentPosition = 0;
      mTFS = tfs;
      mFileId = fileId;
      mHdfsPath = hdfsPath;
      mHadoopConf = conf;
      mHadoopBufferSize = bufferSize;

      TachyonFile tachyonFile = mTFS.getFile(mFileId);
      if (tachyonFile == null) {
        throw new FileNotFoundException("File " + hdfsPath + " with FID " + fileId + " is not found.");
      }
      tachyonFile.setUFSConf(mHadoopConf);
      try {
        mTachyonFileInputStream = tachyonFile.getInStream(ReadType.CACHE);
      } catch (IOException e) {
        LOG.error(e.getMessage());
      }
    }
}
