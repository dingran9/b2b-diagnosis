package com.eedu.diagnosis.exam.api.enumeration;

/**
 * Created by dqy on 2017/9/11.
 */
public enum MarkPaperStatusEnum {

    NOTMARK("未判卷",0),
    MARKED("已判卷",1);
    private String desc;
    private Integer value;
    MarkPaperStatusEnum(String desc,Integer value){
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
