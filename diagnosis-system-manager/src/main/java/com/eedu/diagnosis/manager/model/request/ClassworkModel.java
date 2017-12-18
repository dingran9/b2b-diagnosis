package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.common.model.paperEntity.SmallQuestionSystem;

import java.util.List;

/**
 * 课堂作业实体
 * Created by dqy on 2017/3/21.
 */
public class ClassworkModel {
    /**
     * 资源库试卷code
     */
    private String code;
    /**
     * 资源库试卷名称
     */
    private String paperName;

    private Integer subjectCode;

    private Integer SchoolCode;

    private Integer gradeCode;

    private Integer stageCode;

    private String operator;

    private List<SmallQuestionSystem> smallQuestionSystems;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getSchoolCode() {
        return SchoolCode;
    }

    public void setSchoolCode(Integer schoolCode) {
        SchoolCode = schoolCode;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public List<SmallQuestionSystem> getSmallQuestionSystems() {
        return smallQuestionSystems;
    }

    public void setSmallQuestionSystems(List<SmallQuestionSystem> smallQuestionSystems) {
        this.smallQuestionSystems = smallQuestionSystems;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
