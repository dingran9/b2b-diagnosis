package com.eedu.diagnosis.exam.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-19 16:13
 **/
@Data
public class DiagnosisMarkQuestionRecordDto implements Serializable{
    private String code ;
    /**
     * 诊断记录code
     */
    private String diagnosisRecordCode;
    /**
     * 答题记录code
     */
    private String answerRecordCode;
    /**
     * 判题结果
     */
    private Integer markResult;
    /**
     * 本题得分
     */
    private Double score;
    /**
     * 基础产生式
     */
    private String baseProduction;
    /**
     * 产生式与知识点
     */
    private String productionKnowledge;
    /**
     * 卷面分
     */
    private Double surfaceScore;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 教师code
     */
    private String teacherCode;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
