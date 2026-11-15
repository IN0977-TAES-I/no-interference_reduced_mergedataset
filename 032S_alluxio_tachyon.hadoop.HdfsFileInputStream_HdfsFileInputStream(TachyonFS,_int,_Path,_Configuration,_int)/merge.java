class HdfsFileInputStream {
    static Logger LOG;
    long mCurrentPosition;
    TachyonFS mTFS;
    int mFileId;
    Path mHdfsPath;
    Configuration mHadoopConf;
    int mHadoopBufferSize;
    TachyonFile mTachyonFile;
    InStream mTachyonFileInputStream;

    public HdfsFileInputStream(TachyonFS tfs, int fileId, Path hdfsPath, Configuration conf,
        int bufferSize) throws IOException {
      LOG.debug("PartitionInputStreamHdfs({}, {}, {}, {}, {})", tfs, fileId, hdfsPath, conf,
          bufferSize);
      mCurrentPosition = 0;
      mTFS = tfs;
      mFileId = fileId;
      mHdfsPath = hdfsPath;
      mHadoopConf = conf;
      mHadoopBufferSize = bufferSize;
      mTachyonFile = mTFS.getFile(mFileId);
      if (mTachyonFile == null) {
        throw new FileNotFoundException("File " + hdfsPath + " with FID " + fileId + " is not found.");
      }
      mTachyonFile.setUFSConf(mHadoopConf);
      try {
        mTachyonFileInputStream = mTachyonFile.getInStream(ReadType.CACHE);
      } catch (IOException e) {
        LOG.error(e.getMessage());
      }
    }
}
