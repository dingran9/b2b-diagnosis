package com.eedu.auth.service;

import com.eedu.auth.beans.AuthUserWeChatBindBean;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/7
 * Time: 11:01
 * Describe:
 */
public interface AuthUserWeChatBindService {

	/**
	 * 添加用户微信绑定记录
	 * @param userWeChatBindBean
	 * @return
	 */
	int addUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean) throws Exception;

	/**
	 * 查询用户微信绑定记录
	 * @param userWeChatBindBean
	 * @return
	 */
	AuthUserWeChatBindBean getUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean) throws Exception;

	/**
	 * 修改用户微信绑定记录
	 * @param userWeChatBindBean
	 * @return
	 */
	int updateUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean) throws Exception;
}
