package com.eedu.diagnosis.common.enumration;

/**
 * 测试类型 0单元测试 1期中2期末3模拟考4会考
 * Created by dqy on 2017/4/11.
 */
public enum ExamTypeEnum {
    UNIT(0,"单元测试"),//二期将单元测试重定义为作业（同步辅导）
    MID_EXAM(1,"期中考试"),
    FINAL_EXAM(2,"期末考试"),
    SIMULATION_TEST(3,"模拟考试"),
    WILL_EXAM(4,"全区考");
    private Integer code;
    private String desc;
    ExamTypeEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return  desc;
    }
    public Integer getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
