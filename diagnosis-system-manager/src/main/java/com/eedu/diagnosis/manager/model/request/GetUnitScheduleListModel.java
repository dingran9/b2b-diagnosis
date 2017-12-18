package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.manager.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by dqy on 2017/10/13.
 */
public class GetUnitScheduleListModel extends BaseModel{
    @NotBlank(message = "semester is null.")
    private String semester;
    @NotNull(message = "gradeCode is null.")
    private Integer gradeCode;
    @NotNull(message = "subjectCode is null.")
    private Integer subjectCode;
    @NotBlank(message = "bookVersion is null.")
    private String bookVersion;
    @NotNull(message = "districtId is null.")
    private Integer districtId;
    private Integer operatorCode;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public Integer getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(Integer operatorCode) {
        this.operatorCode = operatorCode;
    }
}
