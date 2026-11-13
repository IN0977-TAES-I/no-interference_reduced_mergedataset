// class name : retrofit.RestAdapter
// method name: logAndReplaceResponse(String, Response, long)
// side       : base
// region     : lines 415-438 (affected lines: [415, 438])
class RestAdapter {
    void logAndReplaceResponse_String__Response__long_() {
      throws IOException {
    log.log(String.format("<--- HTTP %s %s (%sms)", response.getStatus(), url, elapsedTime));

    if (logLevel.ordinal() >= LogLevel.HEADERS.ordinal()) {
      for (Header header : response.getHeaders()) {
        log.log(header.getName() + ": " + header.getValue());
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
            // Read the entire response body so we can log it and replace the original response
            response = Utils.readBodyToBytesIfNecessary(response);
            body = response.getBody();
          }

    }
}
