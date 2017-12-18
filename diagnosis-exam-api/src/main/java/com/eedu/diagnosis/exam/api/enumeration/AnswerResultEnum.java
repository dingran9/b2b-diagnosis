package com.eedu.diagnosis.exam.api.enumeration;

/**
 * Created by dqy on 2017/3/17.
 */
public enum AnswerResultEnum {
    WRONG("错误",0),
    RIGHT("正确",1);
    private String desc;
    private Integer value;
    AnswerResultEnum(String desc,Integer value){
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
