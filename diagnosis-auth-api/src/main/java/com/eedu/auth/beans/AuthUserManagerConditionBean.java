package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/4/17
 * Time: 11:41
 * Describe:
 */
public class AuthUserManagerConditionBean extends BaseModel implements Serializable{

	private Integer userId;

	private String userName;

	private String userAccount;

	private Integer userType;

	private Integer userSex;

	private String userPhone;

	private Integer userSubjectId;

	private String userSubjectName;

	private String userSubjectIden;

	private Integer userSchoolId;

	private String userSchoolName;

	private Integer userGradeId;

	private String userGradeName;

	private String userGradeIden;

	private String status;

	private List<AuthClassBean> classBeanList = new ArrayList<AuthClassBean>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getUserSubjectId() {
		return userSubjectId;
	}

	public void setUserSubjectId(Integer userSubjectId) {
		this.userSubjectId = userSubjectId;
	}

	public String getUserSubjectName() {
		return userSubjectName;
	}

	public void setUserSubjectName(String userSubjectName) {
		this.userSubjectName = userSubjectName;
	}

	public Integer getUserSchoolId() {
		return userSchoolId;
	}

	public void setUserSchoolId(Integer userSchoolId) {
		this.userSchoolId = userSchoolId;
	}

	public String getUserSubjectIden() {
		return userSubjectIden;
	}

	public void setUserSubjectIden(String userSubjectIden) {
		this.userSubjectIden = userSubjectIden;
	}

	public String getUserSchoolName() {
		return userSchoolName;
	}

	public void setUserSchoolName(String userSchoolName) {
		this.userSchoolName = userSchoolName;
	}

	public Integer getUserGradeId() {
		return userGradeId;
	}

	public void setUserGradeId(Integer userGradeId) {
		this.userGradeId = userGradeId;
	}

	public String getUserGradeName() {
		return userGradeName;
	}

	public void setUserGradeName(String userGradeName) {
		this.userGradeName = userGradeName;
	}

	public List<AuthClassBean> getClassBeanList() {
		return classBeanList;
	}

	public void setClassBeanList(List<AuthClassBean> classBeanList) {
		this.classBeanList = classBeanList;
	}

	public String getUserGradeIden() {
		return userGradeIden;
	}

	public void setUserGradeIden(String userGradeIden) {
		this.userGradeIden = userGradeIden;
	}

	public void setStatus(String status){ this.status = status; }

	public String getStatus(){return status; }
}
