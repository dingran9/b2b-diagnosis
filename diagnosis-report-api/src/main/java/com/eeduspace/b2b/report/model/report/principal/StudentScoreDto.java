package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 学生考试得分实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-04 13:00
 **/
@Data
public class StudentScoreDto implements Serializable{
    /**
     * 学生名称
     */
    private String studentName;
    /**
     * 学生得分
     */
    private Double score;
    /**
     * 学生code
     */
    private String studentCode;
}
