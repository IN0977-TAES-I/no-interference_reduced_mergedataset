class WebSocketClient {
    WebSocketHandler webSocketHandler;
    Executor executor;
    HttpRequest request;
    Thread.UncaughtExceptionHandler exceptionHandler;
    Thread.UncaughtExceptionHandler ioExceptionHandler;
    byte[] outboundMaskingKey;

    private void adjustPipelineToWebSocket(ChannelHandlerContext ctx, MessageEvent messageEvent, ChannelHandler webSocketFrameDecoder, ChannelHandler webSocketFrameEncoder) {
        NettyHttpRequest httpRequest = new NettyHttpRequest(messageEvent, request, nextId(), timestamp());
        final NettyWebSocketConnection webSocketConnection = new NettyWebSocketConnection(executor, httpRequest, ctx, outboundMaskingKey);
        webSocketConnection.setHybiWebSocketVersion(13);

        ChannelHandler webSocketChannelHandler = new WebSocketConnectionHandler(webSocketConnection, exceptionHandler, ioExceptionHandler, webSocketHandler, executor);

        ChannelPipeline p = ctx.getChannel().getPipeline();
        p.remove("inflater");
        p.replace("decoder", "wsdecoder", webSocketFrameDecoder);
        p.replace("encoder", "wsencoder", webSocketFrameEncoder);
        p.replace("handshakeHandler", "wshandler", webSocketChannelHandler);

        executor.execute(new CatchingRunnable(exceptionHandler) {
            @Override
            public void go() throws Exception {
                webSocketHandler.onOpen(webSocketConnection);
            }
        });
    }
}
