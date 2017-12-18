package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.Date;

/** 
 * 单科诊断卷
 * 
 **/
public class DiagnosisPaperModel extends BaseModel{

	
	/** 主键code **/
    private String code;
    /** 诊断试卷名称 **/
    private String diagnosisPaperName;
    /** 资源来源 **/
    private Integer resourceType;
    /** 资源Code **/
    private String resourcePaperCode;
    /** 资源名称 **/
    private String resourcePaperName;
    /** 封面 **/
    private String coverUrl;
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
    /** 学科 **/
    private Integer subjectCode;
    /** 诊断卷难度 **/
    private Integer difficultStar;
    /** 单科诊断来源 **/
    private String platform;
    /** 是否删除 **/
    private Integer isDel;
    /** 知识点code **/
    private String unitCode;
    
    private String unitName;

    private Integer sort;//单元排序
    
    private String schoolCode;
    /** 学校名称**/
    private String schoolName;

    private  String  bookVersion;

    private  String  bookVersionCode;

    private Integer artsType;
    
    private String paperType;//试卷类型

    private String paperTypeName;//试卷类型

    private Double totalScore;

    /**
     * 学生诊断记录code
     */
    private String studentDiagnosisRecordCode;

    public String getPaperTypeName() {
        return paperTypeName;
    }

    public void setPaperTypeName(String paperTypeName) {
        this.paperTypeName = paperTypeName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStudentDiagnosisRecordCode() {
        return studentDiagnosisRecordCode;
    }

    public void setStudentDiagnosisRecordCode(String studentDiagnosisRecordCode) {
        this.studentDiagnosisRecordCode = studentDiagnosisRecordCode;
    }

    public String getBookVersion() {
        return bookVersion;
    }

    public void setBookVersion(String bookVersion) {
        this.bookVersion = bookVersion;
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

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourcePaperCode() {
        return resourcePaperCode;
    }

    public void setResourcePaperCode(String resourcePaperCode) {
        this.resourcePaperCode = resourcePaperCode;
    }

    public String getResourcePaperName() {
        return resourcePaperName;
    }

    public void setResourcePaperName(String resourcePaperName) {
        this.resourcePaperName = resourcePaperName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

   
    public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType;
    }

	public String getBookVersionCode() {
		return bookVersionCode;
	}

	public void setBookVersionCode(String bookVersionCode) {
		this.bookVersionCode = bookVersionCode;
	}

	public Integer getArtsType() {
		return artsType;
	}

	public void setArtsType(Integer artsType) {
		this.artsType = artsType;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}
    
}