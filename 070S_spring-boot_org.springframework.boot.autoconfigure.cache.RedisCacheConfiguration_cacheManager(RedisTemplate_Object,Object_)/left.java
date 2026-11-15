class RedisCacheConfiguration {
    CacheProperties cacheProperties;
    CacheManagerCustomizerInvoker customizerInvoker;

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
    	RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    	cacheManager.setUsePrefix(true);
    	List<String> cacheNames = this.cacheProperties.getCacheNames();
    	if (!cacheNames.isEmpty()) {
    		cacheManager.setCacheNames(cacheNames);
    	}
    	this.customizerInvoker.customize(cacheManager);
    	return cacheManager;
    }
}
