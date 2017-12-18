package com.eedu.diagnosis.common.enumration;

/**
 * Created by dqy on 2017/3/16.
 */
public enum MoudleEnum {
    EXAM("1","考试服务系统"),
    RESOURCE("2","资源服务系统"),
    REPORT("3","报告服务系统"),
    AUTH("4","组织权限服务系统"),
    SYSTEMMANAGER("5","系统后台系统"),
    PROTALMANAGER("6","门户网站系统");
    private String code;
    private String desc;
    MoudleEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return  desc;
    }
    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
