// class name : voldemort.VoldemortClientShell
// method name: VoldemortClientShell(ClientConfig, String, BufferedReader, PrintStream, PrintStream)
// side       : right
// region     : lines 123-128 (affected lines: [123, 127, 128])
class VoldemortClientShell {
    void VoldemortClientShell_ClientConfig__String__BufferedReader__PrintStream__PrintStream_() {
            adminClient = new AdminClient(bootstrapUrl, new AdminClientConfig(), new ClientConfig());

            storeDef = StoreUtils.getStoreDef(factory.getStoreDefs(), storeName);

	    Cluster cluster = adminClient.getAdminClientCluster();
	    routingStrategy = new RoutingStrategyFactory().updateRoutingStrategy(storeDef, cluster);
    }
}
