package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 年级教师学科平均分统计
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 10:28
 **/
@Data
public class GradeTeacherSubjectDto implements Serializable{
    /**
     * 年级学科平均分
     */
    private Double gradeSubjectAveScore;
    /**
     * 试卷总分
     */
    private Double paperScore;
    /**
     * 教师学科平均分信息
     */
    List<TeacherSubjectAveScoreDto> teacherSubjectAveScoreDtos;
}
