package com.eedu.auth.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuhongfei on 2017/9/28.
 */
public class AuthUserAuthorityManagerBean  extends BaseModel implements Serializable{


    private Integer userId;

    private String userName;

    private String userAccount;

    private String userPassword;

    private Integer userType;

    private Integer userSubject;

    private String userStage;

    private Integer userSex;

    private String userPhone;

    private String equipmentType;

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

    public Integer getUserGroupAreaProvinceId() {
        return userGroupAreaProvinceId;
    }

    public void setUserGroupAreaProvinceId(Integer userGroupAreaProvinceId) {
        this.userGroupAreaProvinceId = userGroupAreaProvinceId;
    }

    public String getUserGroupAreaProvinceName() {
        return userGroupAreaProvinceName;
    }

    public void setUserGroupAreaProvinceName(String userGroupAreaProvinceName) {
        this.userGroupAreaProvinceName = userGroupAreaProvinceName;
    }

    public Integer getUserGroupAreaCityId() {
        return userGroupAreaCityId;
    }

    public void setUserGroupAreaCityId(Integer userGroupAreaCityId) {
        this.userGroupAreaCityId = userGroupAreaCityId;
    }

    public String getUserGroupAreaCityName() {
        return userGroupAreaCityName;
    }

    public void setUserGroupAreaCityName(String userGroupAreaCityName) {
        this.userGroupAreaCityName = userGroupAreaCityName;
    }

    public Integer getUserGroupAreaDistrictId() {
        return userGroupAreaDistrictId;
    }

    public void setUserGroupAreaDistrictId(Integer userGroupAreaDistrictId) {
        this.userGroupAreaDistrictId = userGroupAreaDistrictId;
    }

    public String getUserGroupAreaDistrictName() {
        return userGroupAreaDistrictName;
    }

    public void setUserGroupAreaDistrictName(String userGroupAreaDistrictName) {
        this.userGroupAreaDistrictName = userGroupAreaDistrictName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer userGroupAreaProvinceId;

    private String userGroupAreaProvinceName;

    private Integer userGroupAreaCityId;

    private String userGroupAreaCityName;

    private Integer userGroupAreaDistrictId;

    private String userGroupAreaDistrictName;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getUserStage() {
        return userStage;
    }

    public void setUserStage(String userStage) {
        this.userStage = userStage;
    }
}
