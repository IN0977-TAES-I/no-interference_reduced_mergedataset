class RestAdapter {
    static int LOG_CHUNK_SIZE;
    Log log;
    LogLevel logLevel;

    private Request logAndReplaceRequest(Request request) throws IOException {
      log.log(String.format("---> HTTP %s %s", request.getMethod(), request.getUrl()));

      if (logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
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
              request = Utils.readBodyToBytesIfNecessary(request);
              body = request.getBody();
            }

            byte[] bodyBytes = ((TypedByteArray) body).getBytes();
            bodySize = bodyBytes.length;
            String bodyCharset = MimeUtil.parseCharset(bodyMime);
            String bodyString = new String(bodyBytes, bodyCharset);
            for (int i = 0, len = bodyString.length(); i < len; i += LOG_CHUNK_SIZE) {
              int end = Math.min(len, i + LOG_CHUNK_SIZE);
              log.log(bodyString.substring(i, end));
            }
          }
        }

        log.log(String.format("---> END HTTP (%s-byte body)", bodySize));
      }

      return request;
    }
}
