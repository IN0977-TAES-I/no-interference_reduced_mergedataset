class DefaultJnlpSlaveReceiver {
    static Logger LOGGER;
    static String COOKIE_NAME;

    @Override
    public boolean handle(String nodeName, JnlpSlaveHandshake handshake) throws IOException, InterruptedException {
        SlaveComputer computer = (SlaveComputer) Jenkins.getInstance().getComputer(nodeName);

        if(computer==null) {
            return false;
        }

        Channel ch = computer.getChannel();
        if(ch !=null) {
            String c = handshake.getRequestProperty("Cookie");
            if (c!=null && c.equals(ch.getProperty(COOKIE_NAME))) {
                LOGGER.info("Disconnecting "+nodeName+" as we are reconnected from the current peer");
                try {
                    computer.disconnect(new ConnectionFromCurrentPeer()).get(15, TimeUnit.SECONDS);
                } catch (ExecutionException e) {
                    throw new IOException("Failed to disconnect the current client",e);
                } catch (TimeoutException e) {
                    throw new IOException("Failed to disconnect the current client",e);
                }
            } else {
                handshake.error(nodeName + " is already connected to this master. Rejecting this connection.");
                return true;
            }
        }

        Properties response = new Properties();
        String cookie = generateCookie();
        response.put("Cookie",cookie);
        handshake.success(response);

        JnlpSlaveAgentProtocol2.Handler handler = (JnlpSlaveAgentProtocol2.Handler)handshake;

        ch = handler.jnlpConnect(computer);

        ch.setProperty(COOKIE_NAME, cookie);

        return true;
    }
}
