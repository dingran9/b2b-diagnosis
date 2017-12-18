package com.eedu.diagnosis.inclass.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static void main(String[] args) {

//		com.alibaba.dubbo.container.Main.main(args);
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] {"spring-comm-conf.xml"});
		System.out.println("diagnosis-class-test-service start success...");
		while (true) {
			try {
				Thread.sleep(Long.MAX_VALUE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
