package pl.edu.pjwstk.s32410.library.api.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class CacheConfig {
	@Value("${redis.cache.enabled}")
	private boolean cacheEnabled;
	
    @Value("${redis.cache.host}")
    private String redisHost;

    @Value("${redis.cache.port}")
    private int redisPort;

    @Value("${redis.cache.username}")
    private String redisUsername;

    @Value("${redis.cache.password}")
    private String redisPassword;
}
