// class name : tachyon.hadoop.HdfsFileInputStream
// method name: HdfsFileInputStream(TachyonFS, int, Path, Configuration, int)
// side       : left
// region     : lines 44-58 (affected lines: [44, 45, 52, 53, 56, 58])
class HdfsFileInputStream {
    void HdfsFileInputStream_TachyonFS__int__Path__Configuration__int_() {
        bufferSize);
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
    }
}
