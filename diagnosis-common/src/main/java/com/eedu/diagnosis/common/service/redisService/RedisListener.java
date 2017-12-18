package com.eedu.diagnosis.common.service.redisService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**redis线程随项目启动
 * @author songwei
 *	Date 2016-04-11
 *  Describe 需要在项目的web.xml文件中添加
 *  <listener><listener-class>com.eeduspace.uuims.oauth.redis.RedisListener</listener-class></listener>
 */
public class RedisListener implements ServletContextListener {
	
	private Runnable subThread;
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if(subThread == null){
			subThread = new RedisThread();
		}
		new Thread(subThread).start();

	}

}
