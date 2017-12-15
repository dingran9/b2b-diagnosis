package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthUserWeChatBindBean;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/7
 * Time: 10:59
 * Describe:
 */
@Repository
public interface AuthUserWeChatBindDao {

	/**
	 * 添加用户微信绑定记录
	 * @param userWeChatBindBean
	 * @return
	 */
	int addUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean);

	/**
	 * 查询用户微信绑定记录
	 * @param userWeChatBindBean
	 * @return
	 */
	AuthUserWeChatBindBean getUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean);

	/**
	 * 修改用户微信绑定记录
	 * @param userWeChatBindBean
	 * @return
	 */
	int updateUserWeChatBind(AuthUserWeChatBindBean userWeChatBindBean);
}
