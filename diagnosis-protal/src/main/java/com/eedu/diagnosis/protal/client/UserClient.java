package com.eedu.diagnosis.protal.client;

import com.eedu.diagnosis.protal.model.user.UserRequestModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

/**
 *  访问uuims调用api接口
 */
public interface UserClient {
    /**
     * 验证手机号是否已经被注册
     */
    public BaseResponse validateByMobile(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户注册
	 */
	public BaseResponse userRegister(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 *修改 密码
	 */
	public BaseResponse updatePassword(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户登录
	 */
	public BaseResponse userLogin(UserRequestModel userRequestModel) throws ClientProtocolException, IOException, Exception;
	/**
	 * 重置密码
	 */
	public BaseResponse resetPwd(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 校验手机号
	 */
	public BaseResponse checkPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户激活
	 */
	public BaseResponse userActivation(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 修改手机号
	 */
	public BaseResponse updateUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
    /**
     * 刷新令牌
     */
	public BaseResponse refreshToken(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 根据手机号获取用户信息
	 */
	public BaseResponse describeByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;

	/**
	 * 用户重置密码 - 发送手机验证码
	 */
	public BaseResponse sendSms(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;

	/**
	 * 用户重置密码 - 校验手机验证码
	 */
	public BaseResponse validateSms(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;

	/**
	 * 用户重置密码 - 用户重置密码
	 */
	public BaseResponse restPwdSms(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;

	/**
	 * 根据手机号 获取用户，若不存在 则新增用户
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author dingran
	 */
	public BaseResponse searchByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 根据手机号 获取用户，若不存在 则新增用户，并且免登陆
	 * @param userRequestModel
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @author dingran
	 */
	public BaseResponse freeLoginByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;


}
