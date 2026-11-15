class OkHttpClient {
    Proxy proxy;
    Set<Route> failedRoutes;
    ProxySelector proxySelector;
    CookieHandler cookieHandler;
    ResponseCache responseCache;
    SSLSocketFactory sslSocketFactory;
    HostnameVerifier hostnameVerifier;
    OkAuthenticator authenticator;
    ConnectionPool connectionPool;
    boolean followProtocolRedirects;

    private OkHttpClient copyWithDefaults() {
      OkHttpClient result = new OkHttpClient();
      result.proxy = proxy;
      result.failedRoutes = failedRoutes;
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
      return result;
    }
}
