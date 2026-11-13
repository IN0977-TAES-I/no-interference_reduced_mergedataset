// class name : com.squareup.okhttp.internal.bytes.GzipSource
// method name: consumeHeader(Deadline)
// side       : left
// region     : lines 112-138 (affected lines: [112, 138])
class GzipSource {
    void consumeHeader_Deadline_() {
    byte flags = buffer.getByte(3);
    boolean fhcrc = ((flags >> FHCRC) & 1) == 1;
    if (fhcrc) updateCrc(buffer, 0, 10);

    short id1id2 = buffer.readShort();
    checkEqual("ID1ID2", (short) 0x1f8b, id1id2);
    buffer.skip(8);

    // Skip optional extra fields.
    // +---+---+=================================+
    // | XLEN  |...XLEN bytes of "extra field"...| (more-->)
    // +---+---+=================================+
    if (((flags >> FEXTRA) & 1) == 1) {
      require(2, deadline);
      if (fhcrc) updateCrc(buffer, 0, 2);
      int xlen = buffer.readShortLe() & 0xffff;
      require(xlen, deadline);
      if (fhcrc) updateCrc(buffer, 0, xlen);
      buffer.skip(xlen);
    }

    // Skip an optional 0-terminated name.
    // +=========================================+
    // |...original file name, zero-terminated...| (more-->)
    // +=========================================+
    if (((flags >> FNAME) & 1) == 1) {
      long index = seek((byte) 0, deadline);
    }
}
