package com.eedu.auth;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 9:28
 * Describe:
 */
public class Main {

	public static void main(String[] args) {

//		com.alibaba.dubbo.container.Main.main(args);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-config.xml" });
		System.out.println("diagnosis-auth start success...");
		while (true) {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
