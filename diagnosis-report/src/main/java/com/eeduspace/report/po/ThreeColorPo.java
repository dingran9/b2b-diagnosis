package com.eeduspace.report.po;

import javax.persistence.*;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_three_color", schema = "b2b_report", catalog = "")
public class ThreeColorPo {
    private int code;
    private Double buleScore;
    private Double orangeScore;
    private Double grayScore;
    private String moduleName;
    private String moduleCode;
    private Integer examCode;

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Basic
    @Column(name = "bule_score")
    public Double getBuleScore() {
        return buleScore;
    }

    public void setBuleScore(Double buleScore) {
        this.buleScore = buleScore;
    }

    @Basic
    @Column(name = "orange_score")
    public Double getOrangeScore() {
        return orangeScore;
    }

    public void setOrangeScore(Double orangeScore) {
        this.orangeScore = orangeScore;
    }

    @Basic
    @Column(name = "gray_score")
    public Double getGrayScore() {
        return grayScore;
    }

    public void setGrayScore(Double grayScore) {
        this.grayScore = grayScore;
    }

    @Basic
    @Column(name = "module_name")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Basic
    @Column(name = "module_code")
    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Basic
    @Column(name = "exam_code")
    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThreeColorPo that = (ThreeColorPo) o;

        if (code != that.code) return false;
        if (buleScore != null ? !buleScore.equals(that.buleScore) : that.buleScore != null) return false;
        if (orangeScore != null ? !orangeScore.equals(that.orangeScore) : that.orangeScore != null) return false;
        if (grayScore != null ? !grayScore.equals(that.grayScore) : that.grayScore != null) return false;
        if (moduleName != null ? !moduleName.equals(that.moduleName) : that.moduleName != null) return false;
        if (moduleCode != null ? !moduleCode.equals(that.moduleCode) : that.moduleCode != null) return false;
        if (examCode != null ? !examCode.equals(that.examCode) : that.examCode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (buleScore != null ? buleScore.hashCode() : 0);
        result = 31 * result + (orangeScore != null ? orangeScore.hashCode() : 0);
        result = 31 * result + (grayScore != null ? grayScore.hashCode() : 0);
        result = 31 * result + (moduleName != null ? moduleName.hashCode() : 0);
        result = 31 * result + (moduleCode != null ? moduleCode.hashCode() : 0);
        result = 31 * result + (examCode != null ? examCode.hashCode() : 0);
        return result;
    }
}
