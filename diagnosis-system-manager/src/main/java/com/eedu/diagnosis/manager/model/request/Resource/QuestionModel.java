package com.eedu.diagnosis.manager.model.request.Resource;

import java.util.List;

/**
* 项目名称：keepMark-teacher-business<br>
* 包名：com.eedu.keepMark.teacher.model.response <br>
* 类名称：QuestionModel  <br>
* 类描述：返回的试题的实体类<br>
* 作者：zhangjian  <br>
* 创建日期：2016年8月27日 <br>
* 公司：北京易教空间教育科技股份有限公司<br>
 */
public class QuestionModel {

	//注解id
	private String id;
	//试题标题
	private String title;
	//题干
	private String stem;
	//认知水平code
	private String stemPicture;
	//试题选项
	private String quesOption;
	//使用者code
	private String optionPicture;
	//
	private String quesAnalyze;
	//教学场景code
	private String anayzePicture;
	//答案（单选、多选字符）
	private String answer;
	//试题难度等级（难中易）
	private String difficultStar;
	//试题评论
	private String quesComment;
	//试题备注
	private String remark;
	//视频文件路径
	private String videoPath;
	//OMMENT音频解析文件路径
	private String audiolistenPath;
	//试题出处（web、book、selfcreate）
	private String source;
	//试题完成度
	private String iscompleted;
	//试题类型1:单选题 2:多选题 3:判断题4:填空题5:主观题6:复合题
	private String type;
	//逻辑删除
	private String isdel;
	//学段code
	private String stageCode;
	//学科名称
	private String subjectName;
	//学年code
	private String gradeCode;
	//是否已审核
	private String ischecked;
	//审核人名称
	private String checkName;
	//审核时间
	private String checkTime; 
	//来源code
	private String gradeRules; 
	//分数
	private String score; 
	//做题时间（分钟）
	private String costTime; 
	//预估做题时间（分钟）
	private String estimateTime;
	//出题人名称
	private String makeName;
	//出题时间
	private String makeTime; 
	//被使用的次数
	private String useTimes; 
	//录入时间
	private String createDate; 
	//登录人名称
	private String createBy; 
	//录入人名称
	private String createName;
	//更新日期
	private String updateDate; 
	//更新登录人名称
	private String updateBy; 
	//更新人名称
	private String updateName; 
	//试题用途（中考模拟、高考模拟）
	private String application; 
	//COMMENT英语听力文件路径
	private String audioAnalyzePath; 
	//产生式名称（以@拼接试题所关联产生式名称）
	private String productions; 
	//MENT 试题关联所有产生式code(@关联)
	private String productionCode; 
	private String enlargeId;
	private String knowledges;
	//试题用途code（1:前测类、2:小结类、3:作业类、4:单元测（测一测））
	private String fitCode; 
	//基础树
	private List<SimpleBaseTree> basetree;
	//教材树
	private List<SimpleTree> tree;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStem() {
		return stem;
	}
	public void setStem(String stem) {
		this.stem = stem;
	}
	public String getStemPicture() {
		return stemPicture;
	}
	public void setStemPicture(String stemPicture) {
		this.stemPicture = stemPicture;
	}
	public String getQuesOption() {
		return quesOption;
	}
	public void setQuesOption(String quesOption) {
		this.quesOption = quesOption;
	}
	public String getOptionPicture() {
		return optionPicture;
	}
	public void setOptionPicture(String optionPicture) {
		this.optionPicture = optionPicture;
	}
	public String getQuesAnalyze() {
		return quesAnalyze;
	}
	public void setQuesAnalyze(String quesAnalyze) {
		this.quesAnalyze = quesAnalyze;
	}
	public String getAnayzePicture() {
		return anayzePicture;
	}
	public void setAnayzePicture(String anayzePicture) {
		this.anayzePicture = anayzePicture;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getDifficultStar() {
		return difficultStar;
	}
	public void setDifficultStar(String difficultStar) {
		this.difficultStar = difficultStar;
	}
	public String getQuesComment() {
		return quesComment;
	}
	public void setQuesComment(String quesComment) {
		this.quesComment = quesComment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getAudiolistenPath() {
		return audiolistenPath;
	}
	public void setAudiolistenPath(String audiolistenPath) {
		this.audiolistenPath = audiolistenPath;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getIscompleted() {
		return iscompleted;
	}
	public void setIscompleted(String iscompleted) {
		this.iscompleted = iscompleted;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsdel() {
		return isdel;
	}
	public void setIsdel(String isdel) {
		this.isdel = isdel;
	}
	public String getStageCode() {
		return stageCode;
	}
	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getIschecked() {
		return ischecked;
	}
	public void setIschecked(String ischecked) {
		this.ischecked = ischecked;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getGradeRules() {
		return gradeRules;
	}
	public void setGradeRules(String gradeRules) {
		this.gradeRules = gradeRules;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	public String getEstimateTime() {
		return estimateTime;
	}
	public void setEstimateTime(String estimateTime) {
		this.estimateTime = estimateTime;
	}
	public String getMakeName() {
		return makeName;
	}
	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}
	public String getMakeTime() {
		return makeTime;
	}
	public void setMakeTime(String makeTime) {
		this.makeTime = makeTime;
	}
	public String getUseTimes() {
		return useTimes;
	}
	public void setUseTimes(String useTimes) {
		this.useTimes = useTimes;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getAudioAnalyzePath() {
		return audioAnalyzePath;
	}
	public void setAudioAnalyzePath(String audioAnalyzePath) {
		this.audioAnalyzePath = audioAnalyzePath;
	}
	public String getProductions() {
		return productions;
	}
	public void setProductions(String productions) {
		this.productions = productions;
	}
	public String getProductionCode() {
		return productionCode;
	}
	public void setProductionCode(String productionCode) {
		this.productionCode = productionCode;
	}
	public String getEnlargeId() {
		return enlargeId;
	}
	public void setEnlargeId(String enlargeId) {
		this.enlargeId = enlargeId;
	}
	public String getKnowledges() {
		return knowledges;
	}
	public void setKnowledges(String knowledges) {
		this.knowledges = knowledges;
	}
	public String getFitCode() {
		return fitCode;
	}
	public void setFitCode(String fitCode) {
		this.fitCode = fitCode;
	}
	public List<SimpleBaseTree> getBasetree() {
		return basetree;
	}
	public void setBasetree(List<SimpleBaseTree> basetree) {
		this.basetree = basetree;
	}
	public List<SimpleTree> getTree() {
		return tree;
	}
	public void setTree(List<SimpleTree> tree) {
		this.tree = tree;
	}
	
}
