package com.eedu.diagnosis.protal.model.user;

import java.util.Date;
/**
 * Author: dingran
 * Date: 2016/7/13
 * 用户详情表
 */
public class UserInfo {
    private Long id;

    private String uuid;
    //用户code
    private String userCode;
    //学校
    private String school;
    //头像地址
    private String headImgUrl;
    //0:男士 1：女生 2 ：未知 UserEnum.Sex
    private Integer sex;
    //昵称
    private String nickName;
    //生日
    private String birthday;
    //学年
    private String grade;
    //省份
    private String province;
    //城市
    private String city;
    //地区
    private String area;
    //国家
    private String country;
    //创建时间
    private Date createDate;
    //更新时间
    private Date updateDate;

    //附加字段
    private int userInfoDegree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getUserInfoDegree() {
        return userInfoDegree;
    }

    public void setUserInfoDegree(int userInfoDegree) {
        this.userInfoDegree = userInfoDegree;
    }
}