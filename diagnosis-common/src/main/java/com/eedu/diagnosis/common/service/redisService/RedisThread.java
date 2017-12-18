package com.eedu.diagnosis.common.service.redisService;

import redis.clients.jedis.JedisPubSub;


/**redis失效监听线程
 * @author songwei
 *	Date 2016-04-11
 */
public class RedisThread implements Runnable {
	
	final String channel = "__keyevent@0__:expired";
	JedisPubSub listener = new RedisPubSubListener();
	RedisSubClient subClient = new RedisSubClient("127.0.0.1", 6379);
	
	@Override
	public void run() {
		System.out.println("---------------Redis失效监听线程：开始监听----------------");
		subClient.sub(listener, channel);
	}
	
}
