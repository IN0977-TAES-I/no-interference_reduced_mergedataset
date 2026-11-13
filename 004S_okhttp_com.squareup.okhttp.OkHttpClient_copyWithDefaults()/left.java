// class name : com.squareup.okhttp.OkHttpClient
// method name: copyWithDefaults()
// side       : left
// region     : lines 277-293 (affected lines: [277, 287, 293])
class OkHttpClient {
    void copyWithDefaults__() {
    result.proxy = proxy;
    result.proxySelector = proxySelector != null ? proxySelector : ProxySelector.getDefault();
    result.cookieHandler = cookieHandler != null ? cookieHandler : CookieHandler.getDefault();
    result.responseCache = responseCache != null ? responseCache : ResponseCache.getDefault();
    result.sslSocketFactory = sslSocketFactory != null
        ? sslSocketFactory
        : HttpsURLConnection.getDefaultSSLSocketFactory();
    result.hostnameVerifier = hostnameVerifier != null
        ? hostnameVerifier
        : HttpsURLConnection.getDefaultHostnameVerifier();
    result.authenticator = authenticator != null
        ? authenticator
        : HttpAuthenticator.SYSTEM_DEFAULT;
    result.connectionPool = connectionPool != null ? connectionPool : ConnectionPool.getDefault();
    result.followProtocolRedirects = followProtocolRedirects;
    result.transports = transports != null ? transports : DEFAULT_TRANSPORTS;
    return result;
    }
}
