package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 教学成绩
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-11 20:20
 **/
@Data
public class AchievementDto implements Serializable {
    private String classCode;
    private String className;
    private String schoolCode;
    private String schoolName;
    private String teacherCode;
    private String teacherName;
    //private UnitModel unitModel;
    /**
     * 平均分
     */
    private Double aveScore;
    /**
     * 标准差
     */
    private Double stand;
    /**
     * 最高分
     */
    private Double maxScore;
    /**
     * 最低分
     */
    private Double minScore;
    /**
     * 标准分
     */
    private Double standScore;

}
