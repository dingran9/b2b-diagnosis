package com.eedu.diagnosis.manager.model.response;

import com.eedu.auth.beans.AuthClassBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/4/20.
 */
public class AuthUserManagerModel {

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

    private String userSexStr;

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

    private Date createDate;

    private Date updateDate;

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
        if(null != userSex && 1 == userSex){
            this.userSexStr = "男";
        }else{
            this.userSexStr = "女";
        }
    }

    public String getUserSexStr() {
        return userSexStr;
    }

    public void setUserSexStr(String userSexStr) {
        this.userSexStr = userSexStr;
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

    public Integer getUserGradeIden() {
        return userGradeIden;
    }

    public void setUserGradeIden(Integer userGradeIden) {
        this.userGradeIden = userGradeIden;
    }

    public Date getUserJoinClassDate() {
        return userJoinClassDate;
    }

    public void setUserJoinClassDate(Date userJoinClassDate) {
        this.userJoinClassDate = userJoinClassDate;
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
}
