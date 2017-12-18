package com.eedu.diagnosis.protal.model.request;

import java.util.List;



public class ResourceBaseModel {
    private String stageCode;//学段
    private String gradeCode;//学年
    private String departmentType;//文理类型
    private String subjectCode;//学科
    private String bookVersionCode;//教材版本
    private String bookVersionName;//教材版本
    private String booktypeCode;//教材版本(知识树用)
    private String bookTypeCode;//教材版本(搜索视频用,资源库)
    private List<String> areas;//地区code集合
    private String aimType;//学生目标类型(0:一本上30分,1:一本,2:二本)
    //private String paperType;//试卷用途(全科诊断,短板诊断)
    private String knowledgeType;//知识树类型(0:不完整;1:完整)
    private List<String> products;//产生式
    
    
    private String resourcePaperCode;//资源库的试卷code
    private String diagnosisPaperCode;
    private String knowledgeCode;//知识点
	private String knowledgeName;//知识点
    private String unitCode;//单元知识点code
    private String volume;//单元知识点code
    private String type;//试卷类型(全科诊断,短板诊断,单元试卷、期中试卷、期末试卷)
    private Double price;//价格
    private String cp;//起始页
    private String paperCode;//试卷code
    private List<String> paperCodes;
   
    private String pageSize;
    private String pageNum;
    //知识点集合
    private Double totalScore;//试卷总分
    private List<String> knowledges;
    
  	//教材版本
  	private String booktype;
  	//目标类型(1:二本,2:一本,3:一本30分)
  	private String difficultStar;

  	private String videoId;

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getAimType() {
		return aimType;
	}

	public void setAimType(String aimType) {
		this.aimType = aimType;
	}

	public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

	public List<String> getAreas() {
		return areas;
	}

	public void setAreas(List<String> areas) {
		this.areas = areas;
	}

	public String getKnowledgeCode() {
		return knowledgeCode;
	}

	public void setKnowledgeCode(String knowledgeCode) {
		this.knowledgeCode = knowledgeCode;
	}

	public String getDepartmentType() {
		return departmentType;
	}

	public void setDepartmentType(String departmentType) {
		this.departmentType = departmentType;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getPaperCode() {
		return paperCode;
	}

	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getBookVersionCode() {
		return bookVersionCode;
	}

	public void setBookVersionCode(String bookVersionCode) {
		this.bookVersionCode = bookVersionCode;
	}

	public List<String> getPaperCodes() {
		return paperCodes;
	}

	public void setPaperCodes(List<String> paperCodes) {
		this.paperCodes = paperCodes;
	}

	public String getBooktypeCode() {
		return booktypeCode;
	}

	public void setBooktypeCode(String booktypeCode) {
		this.booktypeCode = booktypeCode;
	}

    public String getBookVersionName() {
        return bookVersionName;
    }

    public void setBookVersionName(String bookVersionName) {
        this.bookVersionName = bookVersionName;
    }

    public String getKnowledgeType() {
		return knowledgeType;
	}

	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}

	public List<String> getProducts() {
		return products;
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public List<String> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<String> knowledges) {
		this.knowledges = knowledges;
	}

    public String getStageCode() {
        return stageCode;
    }

    public void setStageCode(String stageCode) {
        this.stageCode = stageCode;
    }

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getBookTypeCode() {
		return bookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		this.bookTypeCode = bookTypeCode;
	}

	public String getBooktype() {
		return booktype;
	}

	public void setBooktype(String booktype) {
		this.booktype = booktype;
	}

	public String getDifficultStar() {
		return difficultStar;
	}

	public void setDifficultStar(String difficultStar) {
		this.difficultStar = difficultStar;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getResourcePaperCode() {
		return resourcePaperCode;
	}

	public void setResourcePaperCode(String resourcePaperCode) {
		this.resourcePaperCode = resourcePaperCode;
	}

	public String getDiagnosisPaperCode() {
		return diagnosisPaperCode;
	}

	public void setDiagnosisPaperCode(String diagnosisPaperCode) {
		this.diagnosisPaperCode = diagnosisPaperCode;
	}

    public String getKnowledgeName() {
        return knowledgeName;
    }

    public void setKnowledgeName(String knowledgeName) {
        this.knowledgeName = knowledgeName;
    }
}
