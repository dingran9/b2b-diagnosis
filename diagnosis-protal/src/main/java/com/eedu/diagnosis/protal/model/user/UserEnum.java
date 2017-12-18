package com.eedu.diagnosis.protal.model.user;

import org.apache.commons.lang3.StringUtils;

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description:
 */
public class UserEnum {

    private Status status;
    private LoginStatus loginStatus;
    private Sex sex;
    private CreateType createType;
    private ScanStatus scanStatus;
    private VerifyType verifyType;
    private FriendApplyType friendApplyType;
    private ShareType shareType;

    private RegisterSource registerSource;
    private EquipmentType equipmentType;

    private OnlineStatus onlineStatus;
    private Vip vip;

    public enum RegisterSource {
        HXS_XX("011", "好学生_小学版"),
        HXS_CZ("012","好学生_初中版"),
        HXS_GZ("013", "好学生_高中版"),
        CIBN_HXS("009",  "CIBN_好学生"),
        HXS_TV("010",  "好学生_TV"),
        HXS_TEST("014", "好学生测试用户"),
        HXS("014", "好学生用户"),
        UUIMS("015", "UUIMS用户"),
        KEEPMARK("016", "保分计划"),
        DIAGNOSIS_PROTAL("017", "B2B诊断");
        
 
        private final String key;
        private final String desc;
        public String getValue() {
            return key;
        }
        RegisterSource( String key,String desc) {
            this.key = key;
            this.desc = desc;
        }
    }
    public enum EquipmentType  {
        Test ("Test"),
        Web ("Web"),
        Android("Android"),
        Ios("Ios"),
        Tv("Tv");
        private final String value;

        public String getValue() {
            return value;
        }

        EquipmentType(String value) {
            this.value = value;
        }
    }

    public enum Status {
        NoActive(0),
        Disable(1),
        Enable(2),
        IsDelete(3);
        private final int value;

        public int getValue() {
            return value;
        }

        Status(int value) {
            this.value = value;
        }
    }
    public enum OnlineStatus {
        Is_del(0),//删除
        Off_line(1),//禁用
        On_line(2);//可用
        private final int value;

        public int getValue() {
            return value;
        }

        OnlineStatus(int value) {
            this.value = value;
        }
    }
    public enum Vip {
        Ordinary_user(0),//普通用户
        Vip_user(1);//VIP
        private final int value;

        public int getValue() {
            return value;
        }

        Vip(int value) {
            this.value = value;
        }
    }
    public enum LoginStatus {
        UnKnow(0),
        IsLogout(1),
        IsLogin(2);
        private final int value;

        public int getValue() {
            return value;
        }

        LoginStatus(int value) {
            this.value = value;
        }
    }

    public enum Sex {
        Man(0),
        Woman(1),
        UnKnow(2);
        private final int value;

        public int getValue() {
            return value;
        }

        Sex(int value) {
            this.value = value;
        }
    }

    public enum ScanStatus {
        NoScan(0),//未扫码
        IsScan(1),//已扫码
        ConfirmLogin(2),//确认登录
        CancelLogin(3);//取消登录
        private final int value;

        public int getValue() {
            return value;
        }

        ScanStatus(int value) {
            this.value = value;
        }
    }


    /**
     * 创建类型： 0：用户注册 1：管理员创建
     */
    public enum CreateType {
        ManagerAdd(0),//管理员新增方式
        TemplateAdd(1);//模板新增方式

        private final int value;

        public int getValue() {
            return value;
        }

        CreateType(int value) {
            this.value = value;
        }
    }

    public enum VerifyType {
        register(0),
        teacher_certificate(1),
        reset_password(2),
        edit_password(3);
        private final int value;

        public int getValue() {
            return value;
        }

        VerifyType(int value) {
            this.value = value;
        }
    }
    public enum FriendApplyType {
        BeSend("BeSend"),//已发送消息
        IsAgreed("IsAgreed"),//已同意
        DisAgreed("DisAgreed");//未同意

        private final String value;

        public String getValue() {
            return value;
        }

        FriendApplyType(String value) {
            this.value = value;
        }
    }
    public enum FriendApplySourceType {
        Search("Search"),//搜索
        Battle("Battle"),//对战
        Challenge("Challenge");//挑战

        private final String value;

        public String getValue() {
            return value;
        }

        FriendApplySourceType(String value) {
            this.value = value;
        }
    }
    public enum ShareType {
        QQ("qq"),//QQ
        WEIBO("weibo"),//微博
        MOMENTS("moments");//朋友圈

        private final String value;

        public String getValue() {
            return value;
        }

        ShareType(String value) {
            this.value = value;
        }
        
        public static String toEnumValue(String enumType) {
        	if (StringUtils.isBlank(enumType)) {
        		return "";
			}
			for (ShareType an : ShareType.values()) {
				if (an.getValue().equals(enumType)) {
					return an.getValue();
				}
			}
			return "";
	}
    }

    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LoginStatus getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public CreateType getCreateType() {
        return createType;
    }

    public void setCreateType(CreateType createType) {
        this.createType = createType;
    }

    public ScanStatus getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(ScanStatus scanStatus) {
        this.scanStatus = scanStatus;
    }

    public VerifyType getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(VerifyType verifyType) {
        this.verifyType = verifyType;
    }

    public FriendApplyType getFriendApplyType() {
        return friendApplyType;
    }

    public void setFriendApplyType(FriendApplyType friendApplyType) {
        this.friendApplyType = friendApplyType;
    }

    public RegisterSource getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(RegisterSource registerSource) {
        this.registerSource = registerSource;
    }

    public EquipmentType getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(EquipmentType equipmentType) {
        this.equipmentType = equipmentType;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public Vip getVip() {
        return vip;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }

	public ShareType getShareType() {
		return shareType;
	}

	public void setShareType(ShareType shareType) {
		this.shareType = shareType;
	}
    
}
