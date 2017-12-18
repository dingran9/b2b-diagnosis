package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 教师能力教学情况信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 14:48
 **/
@Data
public class TeacherAbilityDto implements Serializable{
    private String teacherName;
    private String teacherCode;
    /**
     * 能力得分
     */
    private Double abilityScore;

}
