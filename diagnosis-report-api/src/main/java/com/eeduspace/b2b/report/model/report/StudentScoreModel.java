package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * 学生考试得分实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-10 13:19
 **/
@Data
public class StudentScoreModel implements Serializable{
    private String studentName;
    private String studentCode;
    private Double score;
}
