package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-08-31 15:09
 **/
@Data
public class ClassStandardModel implements Serializable{
    /**
     * 平均标准分
     */
    private Double avgStandardScore;
    private String className;
    private String classCode;
    private Integer releaseExamCode;
}
