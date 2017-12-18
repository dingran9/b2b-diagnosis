package com.eedu.diagnosis.common.enumration;

/**
 * Created by dqy on 2017/3/20.
 */
public enum DiagnosisTypeEnum {
    SINGLE("单科诊断",0),
    COMPLEX("全科诊断",1);
    private String desc;
    private Integer value;
    DiagnosisTypeEnum(String desc,Integer value){
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
