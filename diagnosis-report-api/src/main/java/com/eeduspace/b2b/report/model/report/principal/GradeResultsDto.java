package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 年级成绩分布信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-25 14:49
 **/
@Data
public class GradeResultsDto implements Serializable{
    /**
     *  人数
     */
    private Long peoples;
    /**
     * 最高分
     */
    private Double maxScore;
    /**
     * 最低分
     */
    private Double minScore;
    /**
     * 平均分
     */
    private Double avgScore;
    /**
     * 标准差
     */
    private Double standardDeviation;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 及格率
     */
    private Double qualified;
    /**
     * 优秀率
     */
    private Double excellent;
    /**
     * 人员得分区间
     */
    List<PeopleScoreDto> peopleScoreDtos;
}
