package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 班级提分空间
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 13:09
 **/
@Data
public class ClassMentionScoreDto implements Serializable{
    /**
     * 班级code
     */
    private String classCode;
    /**
     * 班级名
     */
    private String className;
    /**
     * 学年code
     */
    private String gradeCode;
    /**
     * 学科code
     */
    private String subjectCode;
    /**
     * 考试发布记录code
     */
    private Integer releaseExamCode;
    /**
     * 提升空间信息
     */
    private List<ThreeColorDto> threeColorDtos;
}
