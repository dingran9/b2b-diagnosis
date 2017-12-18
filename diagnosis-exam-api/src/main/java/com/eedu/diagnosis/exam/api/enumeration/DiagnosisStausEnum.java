package com.eedu.diagnosis.exam.api.enumeration;

/**
 *
 *  0默认已发送 1已提交 2已出报告 3报告异常
 * Created by dqy on 2017/3/17.
 */
public enum DiagnosisStausEnum {
    RELEASE("默认已发送",0),
    SUBMIT("已提交",1),
    REPROT("已出报告",2),
    REPORT_ERROR("报告异常",3);
    private String desc;
    private Integer value;
    DiagnosisStausEnum(String desc,Integer value){
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
