package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 阶段考试标准分实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-13 9:30
 **/
@Data
public class StageStandardScoreDto implements Serializable{
    private String schoolCode;
    private String schoolName;
    private String classCode;
    private String className;
    private String teacherName;
    private String teacherCode;
    private Double standardScore;
    private String releaseCode;
}
