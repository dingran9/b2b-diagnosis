package com.eedu.diagnosis.protal.model.user;

import java.io.Serializable;


public class UserRequestModel  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String password;
    private String phone;
	private String macAddress;
    //注册设备类型
	private String equipmentType;
    //产品类型
	private String productType;
	private String remoteAddress;
	private String refreshToken;
	private String oldPassword;
	private String userAccessKey;
	private String userSecretKey;
	private String email;
	private String userCode;
    private String friendCode;

    //好学生对战版自身PO 实体需要数据
    private String channelSource;//市场渠道来源
    private String registerSource;//注册来源 011 好学生_小学版 012 好学生_初中版 013好学生_高中版 009CIBN_好学生 010好学生_TV 014测试用户
    private String systemVersion;//系统版本

    private String school;
    private String headImgUrl;
    private String birthday;
    private String grade;
    private String area;
    private String country;

	private String type;
	private String smsType;
    
    //uuims 短信重置密码
	private String  ticket;//凭证
	
	private String  code;//验证码
	
	private Integer status;//返回状态
		
	private String action;//请求case
    
    
    
    //keepMark 学生表
    //学生用户名
    private String userName;
    //学生姓名
    private String name;
    //省
    private String province;
    //市
    private String city;
    //县区
    private String county;
    //身份证号码
    private String identCard;
    //手机号
    private String mobile;
    //上课类型(0平时班,1平时晚班+周末)
    private Integer courseType;
    //缴费状态（0否，1已缴体验费，2已缴正式费，3已缴体验和正式费）
    private Integer payType;
    //是否缴费（0否，1是）
    private Integer payState;
    //入学年份
    private String accessionYear;
    //目标类型
    private Integer goalType;
    //学生文理科(0文科，1理科)
    private Integer artType;
    //学生状态(0删除,1禁用,2可用)
    private Integer state;
    //学生类型(0统考,1艺考)
    private Integer  examType;
    //课程类型(0A课程,1B课程,2C课程)
    private Integer lessonLevel;
    //学生所属中心code
    private String centerCode;
    //学生所属班级code
    private String classCode;
    //学生所属分组code
    private String groupCode;
    //学生是否毕业(0没有，1已毕业)
    private Integer graduated;
    //昵称
    private String nickName;
    //微信ID
    private String wxId;
    //详细地址
    private String address;
    //性别（1男，0女）
    private Integer sex;
    
    //学生头像地址
    private String headPortrait;
    //销售人员工号
    private String salemanId;       
    //诊断购买资格码
    private String qualificationCode;
    //购买资格是否可用(0可用,1已使用)
    private Integer used;
    //教学大区code
    private String teachAreaCode;
    //教学大区
    private String teachArea;
    //是否补全(判断学生信息是否完整,0不完整,1完整)
    private Integer completion;
    
    
    private String openId;
    
    private String token;
    //易家教的登录回调用户token失效时间
    private Integer expire;
    //易家教的登录回调用户登录设备标识
    private String clientId;
    
    
    //易家教注册返回值
    private String accessKey;//用户公钥
    private String secretKey;//用户密钥
    private String accd;//网易云信
    private String imToken;//网易云信登录token
    private String imName;//网易云信账号
    private String easeobName;//环信账号
    
    //省市县名称
    private String provinceName;
    private String cityName;
    private String areaName;
    
    //分页属性
    private Integer pageNumber;
    private Integer pageSize ;
    
    private Integer isKeepMarkVip;
    private String eduGoodsPurchaseRecordsCode;//学生购买商品记录code
    
    //删除标志（0未删除，1已删除）
    private Integer delFlag;
    //学段(1小学，2初中，3高中）
    private String stage;
    //学年
    private String year;
    //分班状态(0未分班，1已分班)
    private Integer placementState;
    //学习目标分配状态(0未分配，1已分配)
    private Integer goalState;
    //VIP状态(0非vip，1体验vip，2正式vip)
    private Integer vipState;
    //课程类型(1单科，2全科)
    private Integer lessonType;
    //用户上课科目
    private String userClassSubject;
    //用户账号状态(0未激活，1已激活:app是否能登录的唯一凭证)
    private Integer appState;
    //赠送状态(0未确认赠送，1确认赠送)
    private Integer presentState;
    //电话确认状态(0未电话确认，1已电话确认)
    private Integer phoneState;
    //激活时间点(体验用户激活时间点)
    private String vipDate;
    //仅供 用户查询：查询付款状态不是0时使用
    private Integer payTypeState;
    
       
	

	
	
	
    
    
    
    
    
    
    
    
    
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRemoteAddress() {
		return remoteAddress;
	}
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getUserAccessKey() {
		return userAccessKey;
	}
	public void setUserAccessKey(String userAccessKey) {
		this.userAccessKey = userAccessKey;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserSecretKey() {
		return userSecretKey;
	}
	public void setUserSecretKey(String userSecretKey) {
		this.userSecretKey = userSecretKey;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
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

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getIdentCard() {
		return identCard;
	}
	public void setIdentCard(String identCard) {
		this.identCard = identCard;
	}
	public Integer getCourseType() {
		return courseType;
	}
	public void setCourseType(Integer courseType) {
		this.courseType = courseType;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	public String getAccessionYear() {
		return accessionYear;
	}
	public void setAccessionYear(String accessionYear) {
		this.accessionYear = accessionYear;
	}
	public Integer getGoalType() {
		return goalType;
	}
	public void setGoalType(Integer goalType) {
		this.goalType = goalType;
	}
	public Integer getArtType() {
		return artType;
	}
	public void setArtType(Integer artType) {
		this.artType = artType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	

	public Integer getLessonLevel() {
		return lessonLevel;
	}
	public void setLessonLevel(Integer lessonLevel) {
		this.lessonLevel = lessonLevel;
	}
	public String getCenterCode() {
		return centerCode;
	}
	public void setCenterCode(String centerCode) {
		this.centerCode = centerCode;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public Integer getGraduated() {
		return graduated;
	}
	public void setGraduated(Integer graduated) {
		this.graduated = graduated;
	}
	public String getWxId() {
		return wxId;
	}
	public void setWxId(String wxId) {
		this.wxId = wxId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHeadPortrait() {
		return headPortrait;
	}
	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}
	public String getSalemanId() {
		return salemanId;
	}
	public void setSalemanId(String salemanId) {
		this.salemanId = salemanId;
	}
	public String getQualificationCode() {
		return qualificationCode;
	}
	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
	}
	public String getTeachAreaCode() {
		return teachAreaCode;
	}
	public void setTeachAreaCode(String teachAreaCode) {
		this.teachAreaCode = teachAreaCode;
	}
	public String getTeachArea() {
		return teachArea;
	}
	public void setTeachArea(String teachArea) {
		this.teachArea = teachArea;
	}
	public Integer getCompletion() {
		return completion;
	}
	public void setCompletion(Integer completion) {
		this.completion = completion;
	}
	public Integer getExpire() {
		return expire;
	}
	public void setExpire(Integer expire) {
		this.expire = expire;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
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
	public String getAccd() {
		return accd;
	}
	public void setAccd(String accd) {
		this.accd = accd;
	}
	public String getImToken() {
		return imToken;
	}
	public void setImToken(String imToken) {
		this.imToken = imToken;
	}
	public String getImName() {
		return imName;
	}
	public void setImName(String imName) {
		this.imName = imName;
	}
	public String getEaseobName() {
		return easeobName;
	}
	public void setEaseobName(String easeobName) {
		this.easeobName = easeobName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getIsKeepMarkVip() {
		return isKeepMarkVip;
	}
	public void setIsKeepMarkVip(Integer isKeepMarkVip) {
		this.isKeepMarkVip = isKeepMarkVip;
	}
	public String getEduGoodsPurchaseRecordsCode() {
		return eduGoodsPurchaseRecordsCode;
	}
	public void setEduGoodsPurchaseRecordsCode(String eduGoodsPurchaseRecordsCode) {
		this.eduGoodsPurchaseRecordsCode = eduGoodsPurchaseRecordsCode;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Integer getPlacementState() {
		return placementState;
	}
	public void setPlacementState(Integer placementState) {
		this.placementState = placementState;
	}
	public Integer getGoalState() {
		return goalState;
	}
	public void setGoalState(Integer goalState) {
		this.goalState = goalState;
	}
	public Integer getVipState() {
		return vipState;
	}
	public void setVipState(Integer vipState) {
		this.vipState = vipState;
	}
	public Integer getLessonType() {
		return lessonType;
	}
	public void setLessonType(Integer lessonType) {
		this.lessonType = lessonType;
	}
	public String getUserClassSubject() {
		return userClassSubject;
	}
	public void setUserClassSubject(String userClassSubject) {
		this.userClassSubject = userClassSubject;
	}
	public Integer getAppState() {
		return appState;
	}
	public void setAppState(Integer appState) {
		this.appState = appState;
	}
	public Integer getPresentState() {
		return presentState;
	}
	public void setPresentState(Integer presentState) {
		this.presentState = presentState;
	}
	public Integer getPhoneState() {
		return phoneState;
	}
	public void setPhoneState(Integer phoneState) {
		this.phoneState = phoneState;
	}
	public String getVipDate() {
		return vipDate;
	}
	public void setVipDate(String vipDate) {
		this.vipDate = vipDate;
	}

	public Integer getPayTypeState() {
		return payTypeState;
	}
	public void setPayTypeState(Integer payTypeState) {
		this.payTypeState = payTypeState;
	}
	public Integer getExamType() {
		return examType;
	}
	public void setExamType(Integer examType) {
		this.examType = examType;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSmsType() {
		return smsType;
	}

	public void setSmsType(String smsType) {
		this.smsType = smsType;
	}
}
