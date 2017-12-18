package com.eedu.diagnosis.protal.model.user;


import java.io.Serializable;
import java.util.Date;

/**
 * Author: zz
 * 用户表
 */
@SuppressWarnings("serial")
public class User implements Serializable{
    private Long id;
    //uuims 唯一标识
    private String openId;
    //uuims 公钥
    private String accessKey;
    //uuims 秘钥
    private String secretKey;
    //好学生 唯一标识
    private String userCode;
    //好学生 用户名
    private String userName;
    //手机号
    private String mobile;
    //邮箱
    private String email;
    //密码
    private String password;
    //是否禁用
    private Integer isBlack;
    //用户在线状态
    private Integer userOnlineState;
    //设备唯一标示
    private String macAddress;//9.27
    //是否是新用户
    private Integer isNew;
    //是否是VIP
    private Integer isVip;
    
    private Date vipStartDate;
    
    private Date vipEndDate;
    
    //注册来源 	011 好学生_小学版 012 好学生_初中版 013好学生_高中版 009CIBN_好学生 010好学生_TV 014测试用户
    private String registerSource;
    
    //注册设备类型 	 Test 测试 Web Android Ios Tv
    private String equipmentType;
    //用户信息完善程度  0-100
    private Integer userInfoDegree;
    //市场渠道来源
    private String channelSource;
    private Date createDate;
    private Date updateDate;




	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
    }

    public Integer getUserOnlineState() {
        return userOnlineState;
    }

    public void setUserOnlineState(Integer userOnlineState) {
        this.userOnlineState = userOnlineState;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public Date getVipStartDate() {
        return vipStartDate;
    }

    public void setVipStartDate(Date vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public Date getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(Date vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }



    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public Integer getUserInfoDegree() {
        return userInfoDegree;
    }

    public void setUserInfoDegree(Integer userInfoDegree) {
        this.userInfoDegree = userInfoDegree;
    }

    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
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

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}