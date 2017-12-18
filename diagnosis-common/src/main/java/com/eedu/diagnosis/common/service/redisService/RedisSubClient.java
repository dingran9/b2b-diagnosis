package com.eedu.diagnosis.common.service.redisService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**redis 订阅信息端
 * @author songwei
 *	Date 2016-04-11
 */
public class RedisSubClient {
	
	private Jedis jedis;

	public RedisSubClient(String host ,int port) {
		if(jedis == null){
			jedis = new Jedis(host,port);
		}
	}
	public void sub(JedisPubSub listener, String channel){
		try {
			jedis.subscribe(listener, channel);
		} catch (Exception e) {
			System.out.println("--------自用测试redis异常--------");
		}finally{
			
		}
	}
	
}
