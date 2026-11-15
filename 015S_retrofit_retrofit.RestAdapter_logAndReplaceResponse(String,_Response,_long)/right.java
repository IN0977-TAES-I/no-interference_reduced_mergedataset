class RestAdapter {
    static int LOG_CHUNK_SIZE;
    Log log;
    LogLevel logLevel;

    private Response logAndReplaceResponse(String url, Response response, long elapsedTime)
        throws IOException {
      log.log(String.format("<--- HTTP %s %s (%sms)", response.getStatus(), url, elapsedTime));

      if (logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
        for (Header header : response.getHeaders()) {
          log.log(header.toString());
        }

        long bodySize = 0;
        TypedInput body = response.getBody();
        if (body != null) {
          bodySize = body.length();

          if (logLevel.ordinal() >= LogLevel.FULL.ordinal()) {
            if (!response.getHeaders().isEmpty()) {
              log.log("");
            }

            if (!(body instanceof TypedByteArray)) {
              response = Utils.readBodyToBytesIfNecessary(response);
              body = response.getBody();
            }

            byte[] bodyBytes = ((TypedByteArray) body).getBytes();
            bodySize = bodyBytes.length;
            String bodyMime = body.mimeType();
            String bodyCharset = MimeUtil.parseCharset(bodyMime);
            String bodyString = new String(bodyBytes, bodyCharset);
            for (int i = 0, len = bodyString.length(); i < len; i += LOG_CHUNK_SIZE) {
              int end = Math.min(len, i + LOG_CHUNK_SIZE);
              log.log(bodyString.substring(i, end));
            }
          }
        }

        log.log(String.format("<--- END HTTP (%s-byte body)", bodySize));
      }

      return response;
    }
}
