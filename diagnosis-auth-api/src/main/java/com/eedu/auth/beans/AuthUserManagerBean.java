package com.eedu.auth.beans;

import com.eedu.auth.beans.enums.EnumBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 9:28
 * Describe:
 */
public class AuthUserManagerBean extends BaseModel implements Serializable {

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

	private Integer userSchoolId;

	private String schoolName;

	private Integer userGradeId;

	private String userGradeName;

	private Integer userGradeIden;

	private Date userJoinClassDate;

	private Integer userClassId;

	private List<AuthClassBean> classBeanList = new ArrayList<AuthClassBean>();

	private List<EnumBean> list = new ArrayList<EnumBean>();

	private List<AuthRoleBean> roleBeanList = new ArrayList<AuthRoleBean>();

	private Date createDate;

	private Date updateDate;

	private String status;
    //学段
	private String[] schoolsections;

	//注册设备类型
	private String equipmentType;

	private String token;

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

	public Integer getUserSubject() {
		return userSubject;
	}

	public void setUserSubject(Integer userSubject) {
		this.userSubject = userSubject;
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

	public Date getUserJoinClassDate() {
		return userJoinClassDate;
	}

	public void setUserJoinClassDate(Date userJoinClassDate) {
		this.userJoinClassDate = userJoinClassDate;
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

	public Integer getUserSchoolId() {
		return userSchoolId;
	}

	public void setUserSchoolId(Integer userSchoolId) {
		this.userSchoolId = userSchoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getUserClassId() {
		return userClassId;
	}

	public void setUserClassId(Integer userClassId) {
		this.userClassId = userClassId;
	}

	public List<AuthClassBean> getClassBeanList() {
		return classBeanList;
	}

	public void setClassBeanList(List<AuthClassBean> classBeanList) {
		this.classBeanList = classBeanList;
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

	public String[] getUserSubjects() {
		return userSubjects;
	}

	public void setUserSubjects(String[] userSubjects) {
		this.userSubjects = userSubjects;
	}

	public Integer getUserGradeIden() {
		return userGradeIden;
	}

	public void setUserGradeIden(Integer userGradeIden) {
		this.userGradeIden = userGradeIden;
	}

	public void setStatus(String status){this.status = status; }

	public String getStatus(){return status; }

	public void setList( List<EnumBean> list ){this.list = list;}

	public  List<EnumBean> getList(){return list; }

	public void setSchoolsections(String[] schoolsections){this.schoolsections = schoolsections; }

	public String[] getSchoolsections(){return schoolsections; }

	public void setEquipmentType(String equipmentType){this.equipmentType = equipmentType; }

	public String getEquipmentType(){return equipmentType; }

	public void setRoleBeanList(List<AuthRoleBean> roleBeanList){ this.roleBeanList = roleBeanList; }

	public List<AuthRoleBean> getRoleBeanList(){ return roleBeanList; }

	public void setToken(String token) { this.token = token; }

	public String getToken() { return token; }

	private Integer groupAreaDistrictId;

	private String groupAreaDistrictName;

	public Integer getGroupAreaDistrictId() {
		return groupAreaDistrictId;
	}

	public void setGroupAreaDistrictId(Integer groupAreaDistrictId) {
		this.groupAreaDistrictId = groupAreaDistrictId;
	}

	public String getGroupAreaDistrictName() {
		return groupAreaDistrictName;
	}

	public void setGroupAreaDistrictName(String groupAreaDistrictName) {
		this.groupAreaDistrictName = groupAreaDistrictName;
	}
}
