package com.eedu.diagnosis.manager.model.request;

import com.eedu.diagnosis.manager.model.BaseModel;

import java.util.List;

public class ResourceBaseModel extends BaseModel {

	private String stem;
	/** 学段  **/
	private String stageCode;
	/**学年  **/
	private String gradeCode;
	/** 文理类型 **/
	private String departmentType;
	/**学科  **/
	private String subjectCode;
	/**教材版本  **/
	private String bookVersionCode;
	/**教材版本  **/
	private String bookVersionName;
	/** 教材版本(知识树用) **/
	private String booktypeCode;
	/** 教材版本(搜索视频用,资源库) **/
	private String bookTypeCode;
	/** 地区code集合 **/
	private List<String> areas;
	/** 学生目标类型(0:一本上30分,1:一本,2:二本) **/
	private String aimType;
	/** 试卷用途(全科诊断,短板诊断) **/
	private String knowledgeType;
	/** 产生式 **/
	private List<String> products;
	/** 单元知识点code **/
	private String volume;
	/** 试卷类型(全科诊断,短板诊断,单元试卷、期中试卷、期末试卷) **/
	private String type;
	/** 价格 **/
	private Double price;
	/** 起始页 **/
	private String cp;
	/** 试卷code **/
	private String paperCode;

	private List<String> paperCodes;
	/** 知识点 **/
	private String knowledgeCode;
	/** 试卷总分 **/
	private Double totalScore;

	private List<String> knowledges;

	private String ctbCode;
	/**  **/
	private String unitCode;
	/**试题code**/
	private String questionCode;
	/**教材版本**/
	private String booktype;
	/**目标类型(1:二本,2:一本,3:一本30分)**/
	private String difficultStar;

	private String SubjectFlag;

	private String code;

	private String  productsIds;

	private String examYear;//学期

	private Integer districtId;//区县id

	public String getExamYear() {
		return examYear;
	}

	public void setExamYear(String examYear) {
		this.examYear = examYear;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public String getProductsIds() {
		return productsIds;
	}

	public void setProductsIds(String productsIds) {
		this.productsIds = productsIds;
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

	public String getCtbCode() {
		return ctbCode;
	}

	public void setCtbCode(String ctbCode) {
		this.ctbCode = ctbCode;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(String questionCode) {
		this.questionCode = questionCode;
	}

	public String getSubjectFlag() {
		return SubjectFlag;
	}

	public void setSubjectFlag(String subjectFlag) {
		SubjectFlag = subjectFlag;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}
}
