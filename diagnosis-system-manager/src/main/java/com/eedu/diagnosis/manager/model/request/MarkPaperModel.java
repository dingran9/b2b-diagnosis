package com.eedu.diagnosis.manager.model.request;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by dqy on 2017/9/20.
 */
public class MarkPaperModel {
    @NotBlank(message = "diagnosisTeacherRecordCode")
    private String diagnosisTeacherRecordCode;
    @NotBlank(message = "resourcePaperCode")
    private String resourcePaperCode;
    @NotBlank(message = "paperScore")
    private String paperScore;
    @NotNull(message = "bookVersionCode")
    private String bookVersionCode;
    @NotEmpty(message = "markQuestionList")
    private List<MarkQuestionModel> markQuestionList;

    public List<MarkQuestionModel> getMarkQuestionList() {
        return markQuestionList;
    }

    public void setMarkQuestionList(List<MarkQuestionModel> markQuestionList) {
        this.markQuestionList = markQuestionList;
    }

    public String getBookVersionCode() {
        return bookVersionCode;
    }

    public void setBookVersionCode(String bookVersionCode) {
        this.bookVersionCode = bookVersionCode;
    }

    public String getResourcePaperCode() {
        return resourcePaperCode;
    }

    public void setResourcePaperCode(String resourcePaperCode) {
        this.resourcePaperCode = resourcePaperCode;
    }

    public String getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    public String getDiagnosisTeacherRecordCode() {
        return diagnosisTeacherRecordCode;
    }

    public void setDiagnosisTeacherRecordCode(String diagnosisTeacherRecordCode) {
        this.diagnosisTeacherRecordCode = diagnosisTeacherRecordCode;
    }
}
