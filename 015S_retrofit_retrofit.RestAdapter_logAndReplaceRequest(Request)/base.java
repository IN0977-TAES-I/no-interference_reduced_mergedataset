// class name : retrofit.RestAdapter
// method name: logAndReplaceRequest(Request)
// side       : base
// region     : lines 369-398 (affected lines: [369, 398])
class RestAdapter {
    void logAndReplaceRequest_Request_() {
      for (Header header : request.getHeaders()) {
        log.log(header.getName() + ": " + header.getValue());
      }

      long bodySize = 0;
      TypedOutput body = request.getBody();
      if (body != null) {
        bodySize = body.length();
        String bodyMime = body.mimeType();

        if (bodyMime != null) {
          log.log("Content-Type: " + bodyMime);
        }
        if (bodySize != -1) {
          log.log("Content-Length: " + bodySize);
        }

        if (logLevel.ordinal() >= LogLevel.FULL.ordinal()) {
          if (!request.getHeaders().isEmpty()) {
            log.log("");
          }
          if (!(body instanceof TypedByteArray)) {
            // Read the entire response body to we can log it and replace the original response
            request = Utils.readBodyToBytesIfNecessary(request);
            body = request.getBody();
          }

          byte[] bodyBytes = ((TypedByteArray) body).getBytes();
          bodySize = bodyBytes.length;
          String bodyCharset = MimeUtil.parseCharset(bodyMime);
    }
}
