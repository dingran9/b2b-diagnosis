package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 14:50
 * Describe:
 */
public class AuthRoleResourceBean implements Serializable {

	private Integer id;

	private Integer roleId;

	private String roleName;

	private Integer peopleCount;

	private List<AuthResourceBean> resourceList = new ArrayList<AuthResourceBean>();

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

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(Integer peopleCount) {
		this.peopleCount = peopleCount;
	}

	public List<AuthResourceBean> getResourceList() {
		return resourceList;
	}

	public void setResourceList(List<AuthResourceBean> resourceList) {
		this.resourceList = resourceList;
	}
}
