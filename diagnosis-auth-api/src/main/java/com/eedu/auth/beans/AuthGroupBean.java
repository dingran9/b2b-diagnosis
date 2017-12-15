package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/16
 * Time: 17:40
 * Describe:
 */
public class AuthGroupBean extends BaseModel implements Serializable{

	private Integer groupId;  //组织编号

	private String groupName;  //组织名称

	private Integer groupIden;  //组织标识，只有年级存在

	private String groupMaterial;  //教材版本，只有年级存在此字段，JSON串

	private Integer groupType;  //组织类别

	private String groupAddress; //组织地址，只有学校有

	private AuthUserManagerBean schoolmaster; //学校对应，校长信息

	private Integer groupParentId;  //上级ID

	private Integer groupProductId;  //产品id

	private Integer groupAreaProvinceId;  //所属地区：省编号

	private String groupAreaProvinceName;  //所属地区：省名称

	private Integer groupAreaCityId;  //所属地区：市编号

	private String groupAreaCityName;  //所属地区：市名称

	private Integer groupAreaDistrictId;  //所属地区：区县Id

	private String groupAreaDistrictName;  //所属地区：区县Id

	private Date groupCreateDate;  //组织创建时间

	private Date groupUpdateDate;  //组织修改时间

	private Integer groupArt;  //文理类型 0 文 1 理 2无类型

	private Integer groupExternalSchoolId;  //新增-外部学校的id

	private List<String> groupPeriod = new ArrayList<String>();//学校对应的学段

	private List<AuthGroupBean> childGroupBeanList = new ArrayList<AuthGroupBean>(); //子组织

	private List<AuthUserBean> userList = new ArrayList<AuthUserBean>();//包含的用户

	private Integer peopleNumber;   //班级人数

	private Integer teacherNumber;   //教师人数

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupMaterial() {
		return groupMaterial;
	}

	public void setGroupMaterial(String groupMaterial) {
		this.groupMaterial = groupMaterial;
	}

	public String getGroupAddress() {
		return groupAddress;
	}

	public void setGroupAddress(String groupAddress) {
		this.groupAddress = groupAddress;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getGroupIden() {
		return groupIden;
	}

	public void setGroupIden(Integer groupIden) {
		this.groupIden = groupIden;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public Integer getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(Integer groupParentId) {
		this.groupParentId = groupParentId;
	}

	public Integer getGroupProductId() {
		return groupProductId;
	}

	public void setGroupProductId(Integer groupProductId) {
		this.groupProductId = groupProductId;
	}

	public Integer getGroupAreaProvinceId() {
		return groupAreaProvinceId;
	}

	public void setGroupAreaProvinceId(Integer groupAreaProvinceId) {
		this.groupAreaProvinceId = groupAreaProvinceId;
	}

	public String getGroupAreaProvinceName() {
		return groupAreaProvinceName;
	}

	public void setGroupAreaProvinceName(String groupAreaProvinceName) {
		this.groupAreaProvinceName = groupAreaProvinceName;
	}

	public Integer getGroupAreaCityId() {
		return groupAreaCityId;
	}

	public void setGroupAreaCityId(Integer groupAreaCityId) {
		this.groupAreaCityId = groupAreaCityId;
	}

	public String getGroupAreaCityName() {
		return groupAreaCityName;
	}

	public void setGroupAreaCityName(String groupAreaCityName) {
		this.groupAreaCityName = groupAreaCityName;
	}

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

	public Date getGroupCreateDate() {
		return groupCreateDate;
	}

	public void setGroupCreateDate(Date groupCreateDate) {
		this.groupCreateDate = groupCreateDate;
	}

	public Date getGroupUpdateDate() {
		return groupUpdateDate;
	}

	public void setGroupUpdateDate(Date groupUpdateDate) {
		this.groupUpdateDate = groupUpdateDate;
	}

	public List<AuthGroupBean> getChildGroupBeanList() {
		return childGroupBeanList;
	}

	public void setChildGroupBeanList(List<AuthGroupBean> childGroupBeanList) {
		this.childGroupBeanList = childGroupBeanList;
	}

	public AuthUserManagerBean getSchoolmaster() {
		return schoolmaster;
	}

	public void setSchoolmaster(AuthUserManagerBean schoolmaster) {
		this.schoolmaster = schoolmaster;
	}

	public List<AuthUserBean> getUserList() {
		return userList;
	}

	public void setUserList(List<AuthUserBean> userList) {
		this.userList = userList;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public List<String> getGroupPeriod() {
		return groupPeriod;
	}

	public void setGroupPeriod(List<String> groupPeriod) {
		this.groupPeriod = groupPeriod;
	}

	public void setGroupArt(Integer groupArt){ this.groupArt = groupArt; }

	public Integer getGroupArt(){return groupArt; }

	public Integer getGroupExternalSchoolId() {
		return groupExternalSchoolId;
	}

	public void setGroupExternalSchoolId(Integer groupExternalSchoolId) {
		this.groupExternalSchoolId = groupExternalSchoolId;
	}
}
