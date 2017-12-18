package com.eedu.diagnosis.protal.model.request;


import com.eedu.diagnosis.protal.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dqy on 2017/3/21.
 */
public class StudentModel extends BaseModel {
    private Integer userId;
    private Integer subjectCode;
    private Integer gradeCode;
    private Integer stageCode;
    private Integer classCode;
    private Integer schoolCode;
    private List<Integer> subjectCodes = new ArrayList<>(0);

    public List<Integer> getSubjectCodes() {
        return subjectCodes;
    }

    public void setSubjectCodes(List<Integer> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
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

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public Integer getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(Integer schoolCode) {
        this.schoolCode = schoolCode;
    }
}
