package com.eeduspace.report;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;



@Slf4j
@Configuration
@EnableAutoConfiguration
@ImportResource({"classpath:disconf.xml","classpath:dubbo-provider.xml"})//引入disconf
@SpringBootApplication
public class B2bReportApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(B2bReportApplication.class, args);
		System.out.println("report service  start up --------------");
	}

}
