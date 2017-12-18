package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 班级能力信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 15:01
 **/
@Data
public class ClassSubjectAbilityDto implements Serializable{
    //private String classCode;
    private String className;
    /**
     * 能力信息
     */
    private List<AbilityDto> abilityDtos;
}
