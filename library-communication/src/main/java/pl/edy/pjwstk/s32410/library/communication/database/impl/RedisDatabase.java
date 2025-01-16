package pl.edy.pjwstk.s32410.library.communication.database.impl;

import pl.edy.pjwstk.s32410.library.communication.database.DatabaseLink;
import redis.clients.jedis.JedisPooled;
import redis.clients.jedis.UnifiedJedis;

public class RedisDatabase extends DatabaseLink {
	private UnifiedJedis jedis;
	
	private String host;
	private int port;
	
	private String username;
	private String password;
	
	public RedisDatabase(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	@Override
	public void connect() {
		setStatus(Status.CONNECTING);
		jedis = new JedisPooled(host, port, username, password);
		setStatus(Status.CONNECTED);
	}
	
	@Override
	public <D> void store(String key, D data) {
		
	}

	@Override
	public <D> void retrieve(String key, Class<D> type) {
		
	}

	@Override
	public boolean contains(String key) {
		return false;
	}

}
