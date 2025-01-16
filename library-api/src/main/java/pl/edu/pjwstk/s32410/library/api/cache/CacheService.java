package pl.edu.pjwstk.s32410.library.api.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import pl.edy.pjwstk.s32410.library.communication.database.impl.RedisDatabase;

@Service
public class CacheService {
	@Autowired private CacheConfig config;
	
	private RedisDatabase database;
	
	@PostConstruct
	public void init() {
		if(!config.isCacheEnabled()) return;
		
		database = new RedisDatabase(
				config.getRedisHost(),
				config.getRedisPort(),
				config.getRedisUsername(),
				config.getRedisPassword()
				);
		
		database.connect();
	}
	
	/* TODO implement cache related methods */
}
