package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 班级学科得分变动实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-27 10:16
 **/
@Data
public class GradeSubjectScoreChangeDto implements Serializable{
    /**
     * 发布考试记录code
     */
    private Integer releaseExamCode;
    /**
     * 文理分类集合
     */
    List<ArtTypeDto> artTypeDtos;
}
