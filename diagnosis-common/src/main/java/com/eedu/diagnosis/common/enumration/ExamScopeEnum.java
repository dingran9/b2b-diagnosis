package com.eedu.diagnosis.common.enumration;

/**
 * Created by dqy on 2017/10/9.
 */
public enum ExamScopeEnum {

    CLASS(1,"班级级别"),
    SCHOOL(2,"学校级别"),
    SOME_SCHOOL(3,"多校级别"),
    ALL_AREA(4,"全区级别");

    private Integer code;
    private String des;
    ExamScopeEnum(Integer code,String des){
        this.code = code;
        this.des = des;
    }

    public Integer getValue() {
        return code;
    }
}
