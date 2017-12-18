package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 教师历史贡献指数信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-03 12:01
 **/
@Data
public class TeacherHistoryContributionDto implements Serializable{
    /**
     * 发布考试时间
     */
    private Timestamp releaseExamCreateTime;
    /**
     * 发布考试记录code
     */
    private Integer releaseExamCode;
    private List<TeacherContributionChangeDto> teacherContributionChangeDtos;
    /**
     * 学科平均贡献指数
     */
    private Double subjectAveContribution;
}
