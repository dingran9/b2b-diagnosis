package com.eedu.diagnosis.weixin.service;

import javax.servlet.http.HttpServletResponse;


public interface WeiXinService {

	String getOpenId(String code) throws Exception;

	String creatMenu();

	void replyTextMessage(HttpServletResponse response, String fromUserName,String string);
}
