package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 贡献指数变动
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-03 12:37
 **/
@Data
public class TeacherContributionChangeDto implements Serializable{

    private String teacherName;

    private String teacherCode;
    /**
     * 贡献指数
     */
    private Double contribution;

}
