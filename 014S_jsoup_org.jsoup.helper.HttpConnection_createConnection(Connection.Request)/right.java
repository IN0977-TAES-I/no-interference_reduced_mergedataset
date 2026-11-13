// class name : org.jsoup.helper.HttpConnection
// method name: createConnection(Connection.Request)
// side       : right
// region     : lines 609-617 (affected lines: [609, 610, 611, 612, 613, 614, 617])
class HttpConnection {
    void createConnection_Connection_Request_() {
            if (previousResponse != null) {
                for (Map.Entry<String, String> prevCookie : previousResponse.cookies().entrySet()) {
                    if (!hasCookie(prevCookie.getKey()))
                        cookie(prevCookie.getKey(), prevCookie.getValue());
                }
            }
        }

        void processResponseHeaders(Map<String, List<String>> resHeaders) {
    }
}
