package com.eedu.diagnosis.protal.model.response;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/14
 * Time: 18:05
 * Describe:用户信息
 */
public class AuthUserVo {

	private Integer userId;

	private String userName;

	private String userAccount;

	private String userPassword;

	private Integer userType;

	private String userSecretkey;

	private String userAccesskey;

	private String userOpenId;

	private Integer userSubject;

	private String[] userSubjects;

	private Integer userProductId;

	private Integer userSex;

	private String userPhone;

	private String userCard;

	private String userImage;

	private Integer schoolId;

	private String schoolName;

	private Integer gradeId;

	private String gradeName;

	private Integer gradeIden;

	private Date userJoinClassDate;

	private Integer classId;

	private String className;

	private Date userLoginDate;

	private Date createDate;

	private Date updateDate;

	private String validateCode;

	private Integer validateType; //验证类型
	
	private Integer type; //

	private Integer artType;  //文理类型  0文   1理科

	private Integer isBind;

	private String  machineCode;

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public Integer getIsBind() {
		return isBind;
	}

	public void setIsBind(Integer isBind) {
		this.isBind = isBind;
	}

	public Integer getArtType() {
		return artType;
	}

	public void setArtType(Integer artType) {
		this.artType = artType;
	}

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

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserSecretkey() {
		return userSecretkey;
	}

	public void setUserSecretkey(String userSecretkey) {
		this.userSecretkey = userSecretkey;
	}

	public String getUserAccesskey() {
		return userAccesskey;
	}

	public void setUserAccesskey(String userAccesskey) {
		this.userAccesskey = userAccesskey;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public Integer getUserSubject() {
		return userSubject;
	}

	public void setUserSubject(Integer userSubject) {
		this.userSubject = userSubject;
	}

	public String[] getUserSubjects() {
		return userSubjects;
	}

	public void setUserSubjects(String[] userSubjects) {
		this.userSubjects = userSubjects;
	}

	public Integer getUserProductId() {
		return userProductId;
	}

	public void setUserProductId(Integer userProductId) {
		this.userProductId = userProductId;
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

	public String getUserCard() {
		return userCard;
	}

	public void setUserCard(String userCard) {
		this.userCard = userCard;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public Integer getGradeIden() {
		return gradeIden;
	}

	public void setGradeIden(Integer gradeIden) {
		this.gradeIden = gradeIden;
	}

	public Date getUserJoinClassDate() {
		return userJoinClassDate;
	}

	public void setUserJoinClassDate(Date userJoinClassDate) {
		this.userJoinClassDate = userJoinClassDate;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getUserLoginDate() {
		return userLoginDate;
	}

	public void setUserLoginDate(Date userLoginDate) {
		this.userLoginDate = userLoginDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public Integer getValidateType() {
		return validateType;
	}

	public void setValidateType(Integer validateType) {
		this.validateType = validateType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	

}
