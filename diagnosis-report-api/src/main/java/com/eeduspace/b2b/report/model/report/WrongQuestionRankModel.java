package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * 做题排行榜实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 10:43
 **/
public class WrongQuestionRankModel implements Serializable{
    /**
     * 班级名称
     */
    private String className;
    /**
     * 错误次数
     */
    private Integer wrongCount;
    /**
     * 题号
     */
    private String questionSn;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public String getQuestionSn() {
        return questionSn;
    }

    public void setQuestionSn(String questionSn) {
        this.questionSn = questionSn;
    }
}
