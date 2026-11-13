// class name : tachyon.hadoop.HdfsFileInputStream
// method name: HdfsFileInputStream(TachyonFS, int, Path, Configuration, int)
// side       : merge
// region     : lines 44-58 (affected lines: [44, 45, 52, 53, 56, 58])
class HdfsFileInputStream {
    void HdfsFileInputStream_TachyonFS__int__Path__Configuration__int_() {
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
    }
}
