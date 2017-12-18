package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 学科能力教学掌握情况
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 14:51
 **/
@Data
public class TeachingAbilityDto implements Serializable{
    private String abilityName;
    private String abilityCode;
    /**
     * 教师学科能力教学情况信息
     */
    private List<TeacherAbilityDto> teacherAbilityDtos;
}
