package com.eedu.diagnosis.protal.client.impl;




import com.alibaba.fastjson.JSONObject;
import com.eedu.diagnosis.common.utils.AccountValidatorUtil;
import com.eedu.diagnosis.protal.client.UserClient;
import com.eedu.diagnosis.protal.model.user.UserRequestModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eeduspace.uuims.api.OauthClient;
import com.eeduspace.uuims.api.request.login.LoginRequest;
import com.eeduspace.uuims.api.response.login.LoginResponse;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * 
 * 调用uuims api接口    客户端
 *
 */

@Service
public class UserClientImpl implements UserClient {
	private final Logger logger = LoggerFactory.getLogger(UserClient.class);
	private Gson gson=new Gson();
	
	
	@Value("${diagnosis.protal.uuims.server.url}")
	private String serverUrl;
	@Value("${diagnosis.protal.accessKey}")
	private String accessKey;
	@Value("${diagnosis.protal.secretKey}")
	private String secretKey;
	@Value("${diagnosis.protal.productType}")
	private String productType;

	/**
	 * 用户登录
	 */
	@Override
	public BaseResponse userLogin(UserRequestModel userRequestModel) throws Exception {
		userRequestModel.setProductType(productType);
		OauthClient oauthClient = new OauthClient(serverUrl);
		LoginRequest  loginRequest = new LoginRequest();
		if(AccountValidatorUtil.isMobile(userRequestModel.getPhone())){
			loginRequest.setPhone(userRequestModel.getPhone());
		}else{
			loginRequest.setName(userRequestModel.getName());
		}
		loginRequest.setPassword(userRequestModel.getPassword());
        LoginResponse execute = oauthClient.execute(loginRequest);
//		String action="login";
//		String timestamp=System.currentTimeMillis()+"";
//		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
//		String urlInfo="login?action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5;
//	    String url=serverUrl.concat(urlInfo);
//        logger.debug("userLogin request:{}",url);
//	    String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
	    BaseResponse baseResponse=gson.fromJson(JSONObject.toJSONString(execute), BaseResponse.class);

        return baseResponse;
	}
	/**
	 * 用户注册
	 */
    @Override
	public BaseResponse userRegister(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        userRequestModel.setProductType(productType);
        String action="create";
		String timestamp=System.currentTimeMillis()+"";
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
        //如果用户已经存在则进行激活操作
        if(baseResponse!=null && "Resource.Duplicate".equals(baseResponse.getCode())){
            baseResponse = this.userActivation(userRequestModel);
        }
        return baseResponse;
	}
    /**
     * 验证手机号是否已经被注册
     */
	@Override
    public BaseResponse validateByMobile(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        userRequestModel.setType("phone");
        String action="validate";
        String timestamp=System.currentTimeMillis()+"";
        String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
        String signature="";
        try {
            signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
		System.out.println("serverUrl --------- "+serverUrl);
        String url=serverUrl.concat(urlInfo);
        logger.debug("validate request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("validate reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
    }
	/**
	 * 修改密码
	 */
	@Override
	public BaseResponse updatePassword(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		userRequestModel.setOldPassword(userRequestModel.getOldPassword());
		userRequestModel.setPassword(userRequestModel.getPassword());
		String action="edit_password";
		String timestamp=System.currentTimeMillis()+"";
		String userAccessKey=userRequestModel.getUserAccessKey();
		String token=userRequestModel.getToken();
		String userSecretKey=userRequestModel.getUserSecretKey();
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((userAccessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), userSecretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+userAccessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature+"&token="+token;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updatePwd request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updatePwd reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	/**
	 * 重置密码
	 */
	@Override
	public BaseResponse resetPwd(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="reset_password";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("resetPwd request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("resetPwd reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	/**
	 * 校验手机号
	 */
	@Override
	public BaseResponse checkPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="validate";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("user checkPhone request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("user checkPhone reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	/**
	 * 用户激活
	 */
	@Override
	public BaseResponse userActivation(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="activation";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("user activation  request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("user activation  reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	/**
	 * 修改手机号
	 */
	@Override
	public BaseResponse updateUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="update";
		String timestamp=System.currentTimeMillis()+"";
		String userAccessKey=userRequestModel.getUserAccessKey();
		String token=userRequestModel.getToken();
		String userSecretKey=userRequestModel.getUserSecretKey();
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((userAccessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), userSecretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+userAccessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature+"&token="+token;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	/**
     * 刷新令牌
     */
    @Override
    public BaseResponse refreshToken(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        String action="refresh_token";
        String timestamp=System.currentTimeMillis()+"";
        logger.debug("request body:{}",gson.toJson(userRequestModel));
        String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));

        String urlInfo="token?&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
    }
    /**
	 * 根据手机号获取用户信息
	 */
	@Override
	public BaseResponse describeByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="describe_by_phone";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("describeByPhone request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("describeByPhone reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	/**
	 * 用户重置密码 - 发送手机验证码
	 */
	@Override
	public BaseResponse sendSms(UserRequestModel userRequestModel)throws ClientProtocolException, IOException {
		String action="send_sms";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlInfo="user/restPwd?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	/**
	 * 用户重置密码 - 校验手机验证码
	 */

	@Override
	public BaseResponse validateSms(UserRequestModel userRequestModel)throws ClientProtocolException, IOException {
		String action="validate_code";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlInfo="user/restPwd?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;	}
	/**
	 * 用户重置密码 - 用户重置密码
	 */
	@Override
	public BaseResponse restPwdSms(UserRequestModel userRequestModel)throws ClientProtocolException, IOException {
		String action="reset_password";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlInfo="user/restPwd?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;	}
	/**
	 * 根据手机号 获取用户，若不存在 则新增用户
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author dingran
	 */
	@Override
	public BaseResponse searchByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="search_by_phone";
		String timestamp=System.currentTimeMillis()+"";
		userRequestModel.setProductType(productType);
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
		try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
		String url=serverUrl.concat(urlInfo);
		logger.debug("searchByPhone request:{}",url);
		String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
		logger.debug("searchByPhone reponse:{}",responseString);
		BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	/**
	 * 根据手机号 获取用户，若不存在 则新增用户，并且免登陆
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author dingran
	 */
	@Override
	public BaseResponse freeLoginByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="free_login_by_phone";
		String timestamp=System.currentTimeMillis()+"";
		userRequestModel.setProductType(productType);
		logger.debug("freeLoginByPhone request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
		try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
		String url=serverUrl.concat(urlInfo);
		logger.debug("freeLoginByPhone request:{}",url);
		String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
		logger.debug("freeLoginByPhone reponse:{}",responseString);
		BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}


}
