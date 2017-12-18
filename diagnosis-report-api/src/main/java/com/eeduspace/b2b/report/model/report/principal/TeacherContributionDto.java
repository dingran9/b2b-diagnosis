package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 教师贡献率
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 11:09
 **/
@Data
public class TeacherContributionDto implements Serializable{
    private String teacherCode;
    private String teacherName;
    /**
     * 学科贡献指数
     */
    private Double contributionIndex;
    /**
     * 学科贡献信息
     */
    List<ClassSubjectContributionDto> classSubjectContributionDtos;
}
