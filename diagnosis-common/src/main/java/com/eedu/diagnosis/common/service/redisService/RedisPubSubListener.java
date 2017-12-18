package com.eedu.diagnosis.common.service.redisService;

import redis.clients.jedis.JedisPubSub;

/**
 * redis,监听失效，返回信息处理
 * @author songwei
 *	Date 2016-04-11
 *	Describe 最好自己部署一个redis服务器，修改服务器的redis.conf文件里的
 *	notify-keyspace-events " " 为notify-keyspace-events "AKE" 。 
 */
public class RedisPubSubListener extends JedisPubSub {
	
	private String message;
	@Override
	public void onMessage(String channel, String message) {
		this.message = message;
//		System.out.println("返回的channel：" + channel + "---message：" + message);
		if(!this.message.equals("") || !this.message.equals(null)){
			System.out.println("处理message：" + message);
		}
		//取消订阅
		if(message.equalsIgnoreCase("quit")){
			this.unsubscribe(channel);
		}
		this.message = "";
//		System.out.println("this.message中的信息为：" + this.message);
	}

	@Override
	public void onPMessage(String partten, String channel, String message) {
		
		System.out.println("返回的parrten"+ partten +"---channel：" + channel + "---message：" + message);
	}

	@Override
	public void onPSubscribe(String channel, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPUnsubscribe(String channel, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSubscribe(String channel, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnsubscribe(String channel, int arg1) {
		// TODO Auto-generated method stub
		System.out.println("取消订阅的频道为：" + channel + "---其他为：" + arg1);
	}

}
