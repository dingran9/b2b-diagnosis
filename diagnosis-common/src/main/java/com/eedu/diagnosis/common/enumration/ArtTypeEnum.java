package com.eedu.diagnosis.common.enumration;

/**
 * Created by dqy on 2017/4/25.
 */
public enum ArtTypeEnum {
    LIBERAL("文科",0),
    SCIENCE("理科",1),
    NOTYPE("无类型",2);
    private String desc;
    private Integer value;
    ArtTypeEnum(String desc,Integer value){
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return  desc;
    }
    public Integer getValue() {
        return value;
    }
    public String getDesc() {
        return desc;
    }
}
