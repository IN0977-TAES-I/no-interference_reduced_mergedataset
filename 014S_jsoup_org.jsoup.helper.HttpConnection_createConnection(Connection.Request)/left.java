// class name : org.jsoup.helper.HttpConnection
// method name: createConnection(Connection.Request)
// side       : left
// region     : lines 609-617 (affected lines: [609, 610, 611, 612, 613, 614, 617])
class HttpConnection {
    void createConnection_Connection_Request_() {
            if (conn instanceof HttpsURLConnection) {
                if (!req.isValidateSSLCertificates()) {
                    initUnSecureSSL();
                    ((HttpsURLConnection)conn).setSSLSocketFactory(sslSocketFactory);
                    ((HttpsURLConnection)conn).setHostnameVerifier(getInsecureVerifier());
                }
            }

            if (req.method() == Method.POST)
    }
}
