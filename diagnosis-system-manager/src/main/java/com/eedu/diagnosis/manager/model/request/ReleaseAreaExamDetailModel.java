package com.eedu.diagnosis.manager.model.request;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 该次考试所包含的单科考试详细信息
 * Created by dqy on 2017/9/30.
 */
public class ReleaseAreaExamDetailModel{
    @NotNull(message = "subjectCode is null.")
    private Integer subjectCode;
    @NotNull(message = "artType is null.")
    private Integer artType;
    @NotBlank(message = "diagnosisPaperCode is null.")
    private String diagnosisPaperCode;
    @NotBlank(message = "diagnosisPaperName is null.")
    private String diagnosisPaperName;
    @NotNull(message = "startTime is null.")
    @Future(message = "startTime is invalid.")
    private Date startTime;
    @NotNull(message = "endTime is null.")
    @Future(message = "endTime is invalid.")
    private Date endTime;
    @NotNull(message = "paperScore is null.")
    private Double paperScore;

    public Double getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(Double paperScore) {
        this.paperScore = paperScore;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
    }

    public String getDiagnosisPaperName() {
        return diagnosisPaperName;
    }

    public void setDiagnosisPaperName(String diagnosisPaperName) {
        this.diagnosisPaperName = diagnosisPaperName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = new Date(startTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = new Date(endTime);
    }
}
