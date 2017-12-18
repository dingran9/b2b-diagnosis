package com.eedu.diagnosis.exam.api.enumeration;

/**
 * Created by dqy on 2017/3/21.
 */
public enum PaperSourceEnum {
    DIAGNOSIS("诊断试卷",0),
    HOMEWORK("作业试卷",1);
    private String desc;
    private Integer value;
    PaperSourceEnum(String desc,Integer value){
        this.value = value;
        this.desc = desc;
    }



    @Override
    public String toString() {
        return  desc;
    }

    public String getDesc() {
        return desc;
    }
    public Integer getValue() {
        return value;
    }
}
