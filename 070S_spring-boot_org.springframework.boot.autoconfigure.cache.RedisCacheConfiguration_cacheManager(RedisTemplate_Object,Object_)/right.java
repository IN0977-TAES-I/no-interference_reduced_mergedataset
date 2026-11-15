class RedisCacheConfiguration {
    CacheProperties cacheProperties;
    CacheManagerCustomizers customizerInvoker;

    @Bean
    public RedisCacheManager cacheManager(RedisTemplate<Object, Object> redisTemplate) {
    	RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    	List<String> cacheNames = this.cacheProperties.getCacheNames();
    	if (!cacheNames.isEmpty()) {
    		cacheManager.setCacheNames(cacheNames);
    	}
    	return this.customizerInvoker.customize(cacheManager);
    }
}
