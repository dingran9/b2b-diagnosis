package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.Date;
import java.util.List;


/** 
 * 全科诊断卷
 * 
 **/
public class DiagnosisComplexPaperModel extends BaseModel{

	/** 主键code **/
    private String code;
    /** 全科诊断卷名称**/
    private String diagnosisPaperName;
    /** 封面 **/
    private String coverUrl;
    /** 所属学校 **/
    private String schoolCode;
    /** 学校名称 **/
    private String schoolName;
    /**  **/
    private Date createTime;
    /**  **/
    private Date updateTime;
    /** 创建人 **/
    private String operator;
    /** 学段 **/
    private Integer stageCode;
    /** 学年 **/
    private Integer gradeCode;
    /**上下册标识 0上册  1下册*/
    private Integer volumeType;
    /** 全科诊断卷难度 **/
    private Integer difficultStar;
    /** 全科诊断来源 **/
    private String platform;
    /**试卷类型  0单元测试 1期中 2期末 3模拟考 4会考**/
    private String paperType;

    private Integer artsType;
    /** 是否删除 **/
    private Integer isDel;

    private Integer flag;//区分是否要关联查询


    private List<DiagnosisComplexPaperRelationModel> diagnosisComplexPaperRelationModelList;

    private Double totalScore;
    /** 单科卷信息集合 **/
    private List<DiagnosisPaperModel> diagnosisPaperModelsList;

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getArtsType() {
        return artsType;
    }

    public void setArtsType(Integer artsType) {
        this.artsType = artsType;
    }

    public Integer getVolumeType() {
        return volumeType;
    }

    public void setVolumeType(Integer volumeType) {
        this.volumeType = volumeType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDiagnosisPaperName() {
        return diagnosisPaperName;
    }

    public void setDiagnosisPaperName(String diagnosisPaperName) {
        this.diagnosisPaperName = diagnosisPaperName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getDifficultStar() {
        return difficultStar;
    }

    public void setDifficultStar(Integer difficultStar) {
        this.difficultStar = difficultStar;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

	public List<DiagnosisPaperModel> getDiagnosisPaperModelsList() {
		return diagnosisPaperModelsList;
	}

	public void setDiagnosisPaperModelsList(
			List<DiagnosisPaperModel> diagnosisPaperModelsList) {
		this.diagnosisPaperModelsList = diagnosisPaperModelsList;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

	public List<DiagnosisComplexPaperRelationModel> getDiagnosisComplexPaperRelationModelList() {
		return diagnosisComplexPaperRelationModelList;
	}

	public void setDiagnosisComplexPaperRelationModelList(
			List<DiagnosisComplexPaperRelationModel> diagnosisComplexPaperRelationModelList) {
		this.diagnosisComplexPaperRelationModelList = diagnosisComplexPaperRelationModelList;
	}
    
	
}