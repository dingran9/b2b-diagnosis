package com.eeduspace.report.model;

import lombok.Data;

/**
 * 做题排行榜实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 10:43
 **/
@Data
public class WrongQuestionRankModel {
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
}
