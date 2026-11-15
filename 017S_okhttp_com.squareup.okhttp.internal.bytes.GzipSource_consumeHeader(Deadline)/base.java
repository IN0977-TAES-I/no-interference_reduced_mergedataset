class GzipSource {
    static byte FHCRC;
    static byte FEXTRA;
    static byte FNAME;
    static byte FCOMMENT;
    OkBuffer buffer;
    CRC32 crc;

    private void consumeHeader(Deadline deadline) throws IOException {
      require(10, deadline);
      byte flags = buffer.byteAt(3);
      boolean fhcrc = ((flags >> FHCRC) & 1) == 1;
      if (fhcrc) updateCrc(buffer, 0, 10);

      short id1id2 = buffer.readShort();
      checkEqual("ID1ID2", (short) 0x1f8b, id1id2);
      buffer.skip(8);

      if (((flags >> FEXTRA) & 1) == 1) {
        require(2, deadline);
        if (fhcrc) updateCrc(buffer, 0, 2);
        int xlen = buffer.readShortLe() & 0xffff;
        require(xlen, deadline);
        if (fhcrc) updateCrc(buffer, 0, xlen);
        buffer.skip(xlen);
      }

      if (((flags >> FNAME) & 1) == 1) {
        long index = seek((byte) 0, deadline);
        if (fhcrc) updateCrc(buffer, 0, index + 1);
        buffer.skip(index + 1);
      }

      if (((flags >> FCOMMENT) & 1) == 1) {
        long index = seek((byte) 0, deadline);
        if (fhcrc) updateCrc(buffer, 0, index + 1);
        buffer.skip(index + 1);
      }

      if (fhcrc) {
        checkEqual("FHCRC", buffer.readShortLe(), (short) crc.getValue());
        crc.reset();
      }
    }
}
