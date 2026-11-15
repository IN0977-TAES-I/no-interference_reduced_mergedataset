class TomcatEmbeddedServletContainerFactory {
    List<TomcatConnectorCustomizer> tomcatConnectorCustomizers;

    protected void customizeConnector(Connector connector) {
    	int port = (getPort() >= 0 ? getPort() : 0);
    	connector.setPort(port);
    	if (connector.getProtocolHandler() instanceof AbstractProtocol) {
    		if (getAddress() != null) {
    			((AbstractProtocol) connector.getProtocolHandler())
    					.setAddress(getAddress());
    		}
    	}
    	if (getUriEncoding() != null) {
    		connector.setURIEncoding(getUriEncoding());
    	}

    	connector.setProperty("bindOnInit", "false");
    	for (TomcatConnectorCustomizer customizer : this.tomcatConnectorCustomizers) {
    		customizer.customize(connector);
    	}
    }
}
