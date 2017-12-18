package com.eedu.diagnosis.manager.utils.ueditor.config;

import  com.eedu.diagnosis.manager.utils.ueditor.ActionEnter;
import  com.eedu.diagnosis.manager.utils.ueditor.ConfigManager;
import  com.eedu.diagnosis.manager.utils.ueditor.hunter.FileManager;
import  com.eedu.diagnosis.manager.utils.ueditor.upload.StorageManager;

import com.qiniu.common.Zone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class UeditorAutoConfiguration {

	@Value("${ueditor.access-key}")
	private   String ACCESS_KEY;
	@Value("${ueditor.secret-key}")
	private  String SECRET_KEY;
	@Value("${ueditor.bucket}")
	private  String ueditorbucket;
	@Value("${ueditor.base-url}")
	private   String baseurl;
	@Value("${ueditor.zone}")
	private   String ueditorzone;
	@Value("${ueditor.upload-dir-prefix}")
	private   String ueditorupload;
	@Value("${ueditor.config}")
	private   String ueditorconfig;




	@Bean
	public ActionEnter actionEnter(){
		ActionEnter actionEnter = new ActionEnter( new ConfigManager(ueditorconfig));
		return actionEnter;
	}
	@PostConstruct
	public void storagemanager(){
		StorageManager.accessKey = FileManager.accessKey = ACCESS_KEY;
		StorageManager.secretKey = FileManager.secretKey =SECRET_KEY;
		StorageManager.baseUrl  = FileManager.baseUrl =baseurl;
		StorageManager.bucket = FileManager.bucket  =ueditorbucket;
		StorageManager.baseUrl = FileManager.baseUrl  =baseurl;
		StorageManager.uploadDirPrefix = FileManager.uploadDirPrefix  = ueditorupload;
		StorageManager.zone = FileManager.zone  = Zone.zone0();

		
	}
	
}
