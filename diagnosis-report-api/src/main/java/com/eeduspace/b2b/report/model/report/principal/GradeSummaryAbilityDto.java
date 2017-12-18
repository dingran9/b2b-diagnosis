package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 年级能力信息汇总
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 17:14
 **/
@Data
public class GradeSummaryAbilityDto implements Serializable{
    /**
     * 班级能力集合
     */
    List<ClassSubjectAbilityDto> classSubjectAbilityDtos;
    /**
     * 年级平均能力
     */
    List<AbilityDto> gradeAveAbilityDtos;
}
