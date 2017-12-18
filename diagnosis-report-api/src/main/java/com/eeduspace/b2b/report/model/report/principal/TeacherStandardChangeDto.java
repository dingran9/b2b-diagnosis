package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-03 12:37
 **/
@Data
public class TeacherStandardChangeDto implements Serializable{

    private String teacherName;

    private String teacherCode;
    /**
     * 标准分
     */
    private Double standardScore;

}
