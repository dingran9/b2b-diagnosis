package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthUserWeChatBindBean;
import com.eedu.auth.dao.AuthUserWeChatBindDao;
import com.eedu.auth.service.AuthUserWeChatBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/7
 * Time: 11:01
 * Describe:
 */
@Service("userWeChatBindServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserWeChatBindServiceImpl implements AuthUserWeChatBindService {

	@Autowired
	private AuthUserWeChatBindDao userWeChatBindDao;
	/**
	 * 添加用户微信绑定记录
	 *
	 * @param userWeChatBindBean
	 * @return
	 */
	@Override
	public int addUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean) throws Exception{
		return userWeChatBindDao.addUserWeChatBind(userWeChatBindBean);
	}

	/**
	 * 查询用户微信绑定记录
	 *
	 * @param userWeChatBindBean
	 * @return
	 */
	@Override
	public AuthUserWeChatBindBean getUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean) throws Exception{
		return userWeChatBindDao.getUserWeChatBind(userWeChatBindBean);
	}

	/**
	 * 修改用户微信绑定记录
	 *
	 * @param userWeChatBindBean
	 * @return
	 */
	@Override
	public int updateUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean) throws Exception{
		return userWeChatBindDao.updateUserWeChatBind(userWeChatBindBean);
	}
}
