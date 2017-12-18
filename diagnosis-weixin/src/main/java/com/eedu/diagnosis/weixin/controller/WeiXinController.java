package com.eedu.diagnosis.weixin.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eedu.diagnosis.weixin.service.WeiXinService;
import com.eedu.diagnosis.weixin.util.HttpServletStream;
import com.eedu.diagnosis.weixin.util.ParaXml;
import com.eedu.diagnosis.weixin.util.WeixinUtil;





/**
 *  用户绑定微信公众号
 * 
 * @author   zz
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController {
    private final Logger logger = LoggerFactory.getLogger(WeiXinController.class);

    //private static final String TOKEN = "b2bDiagnosis";
    private static final String TOKEN ="diagnosisWeixin";
  
    
    
    @Autowired
    private WeiXinService weiXinService;
    
    /**
     *  校验配置微信公众号的token
     * @param request
     * @param response
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * 
     */
    @RequestMapping(value = "callback", method = RequestMethod.GET)
    @ResponseBody
    public void callback(HttpServletRequest request, HttpServletResponse response, String signature, String timestamp, String nonce, String echostr) {
    		signature = request.getParameter("signature");
    		timestamp = request.getParameter("timestamp");
    		nonce = request.getParameter("nonce");
    		echostr = request.getParameter("echostr");
    		//logger.info("回调参数:" + signature + "|" + timestamp + "|" + nonce + "|" + echostr);
    		String sign = WeixinUtil.getSHA1(TOKEN, timestamp, nonce);
    		if (sign.equalsIgnoreCase(signature)) {
    			try {
    				response.getWriter().print(echostr);
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    
    @RequestMapping(value = "callback", method = RequestMethod.POST)
	@ResponseBody
	public void callback(HttpServletRequest request, HttpServletResponse response) {
		String xml = HttpServletStream.getString(request);
		logger.info("-------------------------------------callback----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		logger.info(xml);
		ParaXml px = new ParaXml(xml.trim());
		String ToUserName = px.getValue("ToUserName");
		String FromUserName = px.getValue("FromUserName");
		String CreateTime = px.getValue("CreateTime");
		String MsgType = px.getValue("MsgType");
		String Event = px.getValue("Event");
		String EventKey = px.getValue("EventKey");
        if("SUBSTITUTE_MOTHER".equals(EventKey)){
        	String responseXml = "<xml><ToUserName><![CDATA["+FromUserName+"]]></ToUserName><FromUserName><![CDATA["+ToUserName+"]]></FromUserName><CreateTime>"+CreateTime+"</CreateTime><MsgType><![CDATA[transfer_customer_service]]></MsgType></xml>";
        	HttpServletStream.putString(responseXml, response);
        }
        if("view".equals(Event)){
        	weiXinService.replyTextMessage(response, FromUserName, "学科评测欢迎您！");
        }
		logger.info("----------------------------------------callback--------success-----------------------------------------------------------------------------------------------------------------------------------------------------------");

        weiXinService.replyTextMessage(response, FromUserName, "学科评测欢迎您！！！！");
	}
    /**
     * 查询资源卷
     */
    @RequestMapping(value = "/getOpenid")
    public String getOpenid(HttpServletRequest request, Model model) {
    	logger.debug("----------------------------------------------WeiXinController  getOpenId ---------------------------------------");
    	try {
    		String state = request.getParameter("state");
    		String code = request.getParameter("code");
	    	logger.debug("--WeiXinController  getOpenId ------code ="+code);
	    	logger.debug("--WeiXinController  getOpenId ------state ="+state);
			String openId = weiXinService.getOpenId( code);
	    	logger.debug("----------------------------------------------WeiXinController  getOpenId ------openId ="+openId);
			model.addAttribute("openId", openId);
			model.addAttribute("redirectUrl", state);	
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    /**
     * 创建菜单
     */
    @RequestMapping(value = "/creatMenu")
    @ResponseBody
    public String creatMenu() {
    	logger.debug("WeiXinController  creatMenu ------- ");
    	String result = weiXinService.creatMenu();
    	return result;
   
	
    }  
    
    
}