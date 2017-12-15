package com.eedu.auth.beans;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/15
 * Time: 17:03
 * Describe:角色信息
 */
public class AuthRoleBean implements Serializable{

	private Integer id;

	private Integer roleId;

	private String roleName;

	private Integer roleProductId;

	private Integer roleSort;

	private String roleDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public Integer getRoleSort() {
		return roleSort;
	}

	public void setRoleSort(Integer roleSort) {
		this.roleSort = roleSort;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleProductId() {
		return roleProductId;
	}

	public void setRoleProductId(Integer roleProductId) {
		this.roleProductId = roleProductId;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
