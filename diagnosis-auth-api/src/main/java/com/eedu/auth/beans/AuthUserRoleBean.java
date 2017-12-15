package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 14:50
 * Describe:
 */
public class AuthUserRoleBean implements Serializable {

	private Integer id;

	private Integer userId;

	private String userName;

	private String userAccount;

	private String userPhone;

	private String userPassword;

	private Integer status;

	private Integer roleId;

	private String roleName;

	private List<AuthRoleBean> authRoleBeans;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

    public void setAuthRoleBeans(List<AuthRoleBean> authRoleBeans){ this.authRoleBeans = authRoleBeans; }

	public List<AuthRoleBean> getAuthRoleBeans(){ return authRoleBeans; };

    public void setRoleName(String roleName){ this.roleName = roleName; }

    public String getRoleName(){ return roleName; }
}
