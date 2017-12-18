package com.eedu.diagnosis.weixin.service.impl;


import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import com.eedu.auth.service.AuthUserWeChatBindService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthUserWeChatBindBean;
import com.eedu.diagnosis.weixin.model.WeiXinOpenIdModel;
import com.eedu.diagnosis.weixin.model.WeiXinTokenModel;
import com.eedu.diagnosis.weixin.model.WeiXinUserInfoModel;
import com.eedu.diagnosis.weixin.service.WeiXinService;
import com.eedu.diagnosis.weixin.util.FileUtil;
import com.eedu.diagnosis.weixin.util.HttpRequest;
import com.eedu.diagnosis.weixin.util.HttpServletStream;
import com.eedu.diagnosis.weixin.util.PathUtil;

@Service
public class WeiXinServiceImpl implements WeiXinService {


	private static final String FromUserName="gh_2640410a32d9";

	@Value("${weixin.appID}")
	private String appID;
	@Value("${weixin.appsecret}")
	private String appsecret;
	@Value("${weixin.oauth2_access_token}")
	private String oauth2AccessTokenUrl;
	@Value("${weixin.userInfoUrl}")
	private String userInfoUrl;
	@Value("${weixin.getAccessTokenUrl}")
	private String getAccessTokenUrl;
	@Value("${weixin.createMenuUrl}")
	private String createMenuUrl;


	@Autowired
	private AuthUserWeChatBindService authUserWeChatBindService ;

	/**
	 * 获取openId
	 */
	@SuppressWarnings("null")
	@Override
	public String getOpenId(String code) throws Exception{
		/**获取token**/
		String oauth2AccessTokenUrls = oauth2AccessTokenUrl.replace("${appID}", appID).replace("${appsecret}", appsecret).replace("${code}", code);
		WeiXinTokenModel tokenModel = getAccessToken(oauth2AccessTokenUrls);
		if (null != tokenModel.getOpenid()) {
			/**获取用户信息**/
			WeiXinUserInfoModel userInfoModel = getUserInfo(tokenModel);
			if (null != userInfoModel.getOpenid()) {
				AuthUserWeChatBindBean userWeChatBindBean = new AuthUserWeChatBindBean();
				userWeChatBindBean.setOpenId(userInfoModel.getOpenid());
				AuthUserWeChatBindBean userWeChatBind = authUserWeChatBindService.getUserWeChatBind(userWeChatBindBean);
				if (null == userWeChatBind) {
					userWeChatBind = new AuthUserWeChatBindBean();
					userWeChatBind.setOpenId(userInfoModel.getOpenid());
					userWeChatBind.setNickName(userInfoModel.getNickname());
					userWeChatBind.setCountry(userInfoModel.getCountry());
					userWeChatBind.setProvince(userInfoModel.getProvince());
					userWeChatBind.setCity(userInfoModel.getCity());
					userWeChatBind.setHeadImgUrl(userInfoModel.getHeadimgurl());
					userWeChatBind.setSex(userInfoModel.getSex());
					userWeChatBind.setPrivilege(userInfoModel.getPrivilege());
					userWeChatBind.setUnionId(userInfoModel.getUnionid());
					int save = authUserWeChatBindService.addUserWeChatBind(userWeChatBind);
					if (save == 0) {
						return null;
					}
				}
				return userInfoModel.getOpenid();
			}
		}
		return null;
	}
	/**获取token**/
	public  WeiXinTokenModel getAccessToken(String url){
		String accessTokenJson = HttpRequest.getRequest(url);
		WeiXinTokenModel accessToken = JSONObject.parseObject(accessTokenJson,WeiXinTokenModel.class);
		System.out.println("weixin return  accessToken = "+JSONObject.toJSONString(accessToken));
		return accessToken ;
	}
	/**获取用户信息**/
	public  WeiXinUserInfoModel getUserInfo(WeiXinTokenModel tokenModel){
		String userInfos = userInfoUrl.replace("${accessToken}", tokenModel.getAccess_token()).replace("${openid}", tokenModel.getOpenid());
		String request = HttpRequest.getRequest(userInfos);
		WeiXinUserInfoModel userInfoModel = JSONObject.parseObject(request,WeiXinUserInfoModel.class);
		return userInfoModel ;
	}
	@Override
	public String creatMenu() {
		String sj = new FileUtil().readFile(PathUtil.getClassPath() + "conf/weixin_menu.text");
		String getAccessTokenUrls = getAccessTokenUrl.replace("${appID}", appID).replace("${appsecret}", appsecret);
		WeiXinTokenModel accessToken = getAccessToken(getAccessTokenUrls);
        String createMenuUrls = createMenuUrl.replace("${accessToken}", accessToken.getAccess_token());
		String reJson = HttpRequest.postRequest(createMenuUrls , sj.toString()) ;
		return reJson ;
	}
	@Override
	public void replyTextMessage(HttpServletResponse response, String userOpenid , String message) {
		long time = new Date().getTime()/1000;
		String xml ="<xml>"+
				"<ToUserName><![CDATA["+userOpenid+"]]></ToUserName>"+
				"<FromUserName><![CDATA["+FromUserName+"]]></FromUserName>"+
				"<CreateTime>"+time+"</CreateTime>"+
				"<MsgType><![CDATA[text]]></MsgType>"+
				"<Content><![CDATA["+message+"]]></Content>"+
				"</xml>";
		HttpServletStream.putString(xml, response);

	}
}

	
