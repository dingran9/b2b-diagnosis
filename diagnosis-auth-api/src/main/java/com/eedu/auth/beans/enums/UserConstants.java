package com.eedu.auth.beans.enums;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/21
 * Time: 15:55
 * Describe:
 */
public interface UserConstants {

	/**
	 * 登录用户名
	 */
	String LOGIN_ID = "loginid";
	/**
	 * 登录用户
	 */
	String LOGIN_USER = "login_user";

	String LOGIN_USER_MANAGER = "login_user_manager";

	/**
	 * 登录用户 当前所有菜单权限
	 */
	String LOGIN_MENUS = "login_menus";

	/**
	 * 登陆用户的IP
	 */
	String LOGIN_IP = "login_ip";

	/**
	 * 登录用户拥有的角色
	 */
	String LOGIN_ROLES = "login_roles";
	/**
	 * redis 验证码code   sendIdenCode_phone_手机号
	 */
	String SENDIDENCODE_PHONE = "sendIdenCode_phone_";
}
