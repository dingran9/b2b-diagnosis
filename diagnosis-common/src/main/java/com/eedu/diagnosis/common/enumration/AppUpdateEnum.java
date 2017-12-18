package com.eedu.diagnosis.common.enumration;

/**
 * app更新
 */
public enum AppUpdateEnum {
    ABLE("可用","1"),
    NOTABLE("不可用","0");
    private String desc;
    private String value;
    AppUpdateEnum(String desc,String value){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return  desc;
    }
    public String getValue() {
        return value;
    }
    public String getDesc() {
        return desc;
    }
}
