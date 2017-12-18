package com.eedu.diagnosis.common.model.paperEntity;

import java.util.ArrayList;
import java.util.List;

public class SmallQuestionSystem {
	//学生本题得分
	private Double studentGetScore;
	//本题卷面得分
	private Double surfaceScore;
    private String id;
    private String title;
    private String stem;
	private String quesOption;
	private String answer;
	private String type;
	private String quesAnalyze;
	private Integer difficultStar;
	private String videoPath;
	private String audioListenPath;
	private String stageCode;
	private String subjectName;
	private String gradeCode;
	private String audioAnalyzePath;
	private String productions;
	private String productionCode;
	private Double score;
    private String enlargeId;
    private String knowledges;
    private String costTime;
    private Integer sort;
    private Integer questionNo;
    //用户答题答案
    private String userAnswerResul;
	private String questionAudio;
	private int isFavorite = 0;//是否收藏 0未1已
	private Integer isImg;
	private Integer isComplex;//是否为复合题
	private String answerRecordCode;
	private String componentQuestionCode;
	/**
	 * 0 错 1对
	 */
	private Integer markQuestionResult;

	public Integer getIsComplex() {
		return isComplex;
	}

	public void setIsComplex(Integer isComplex) {
		this.isComplex = isComplex;
	}

	public String getAnswerRecordCode() {
		return answerRecordCode;
	}

	public void setAnswerRecordCode(String answerRecordCode) {
		this.answerRecordCode = answerRecordCode;
	}

	public Integer getIsImg() {
		return isImg;
	}

	public void setIsImg(Integer isImg) {
		this.isImg = isImg;
	}

	public Integer getMarkQuestionResult() {
		return markQuestionResult;
	}

	public void setMarkQuestionResult(Integer markQuestionResult) {
		this.markQuestionResult = markQuestionResult;
	}

	/**
	 * 教材树产生式  用来前端显示知识点
	 */
	private List<ProductionModel> knowledgeJson;
	public String getQuestionAudio() {
		return questionAudio;
	}

	public void setQuestionAudio(String questionAudio) {
		this.questionAudio = questionAudio;
	}

	public List<ProductionModel> getKnowledgeJson() {
		return knowledgeJson;
	}

	public void setKnowledgeJson(List<ProductionModel> knowledgeJson) {
		this.knowledgeJson = knowledgeJson;
	}

	private List<SimpleTree> tree=new ArrayList<SimpleTree>(0);
    private List<BaseProductionModel> basetree=new ArrayList<BaseProductionModel>(0);
    private List<ComponentQuestion> componentQuestions=new ArrayList<ComponentQuestion>(0);
	private List<BaseProductionModel> baseProductionJson;

	public String getComponentQuestionCode() {
		return componentQuestionCode;
	}

	public void setComponentQuestionCode(String componentQuestionCode) {
		this.componentQuestionCode = componentQuestionCode;
	}

	public int getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}

	public List<BaseProductionModel> getBaseProductionJson() {
		return baseProductionJson;
	}

	public void setBaseProductionJson(List<BaseProductionModel> baseProductionJson) {
		this.baseProductionJson = baseProductionJson;
	}

	//具体题型
  	private String logicType;

	public Double getStudentGetScore() {
		return studentGetScore;
	}

	public void setStudentGetScore(Double studentGetScore) {
		this.studentGetScore = studentGetScore;
	}

	public Double getSurfaceScore() {
		return surfaceScore;
	}

	public void setSurfaceScore(Double surfaceScore) {
		this.surfaceScore = surfaceScore;
	}

	public Integer getQuestionNo() {
		return questionNo;
	}
	public void setQuestionNo(Integer questionNo) {
		this.questionNo = questionNo;
	}
	public List<ComponentQuestion> getComponentQuestions() {
		return componentQuestions;
	}
	public void setComponentQuestions(List<ComponentQuestion> componentQuestions) {
		this.componentQuestions = componentQuestions;
	}
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
	public String getQuesOption() {
		return quesOption;
	}
	public void setQuesOption(String quesOption) {
		this.quesOption = quesOption;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQuesAnalyze() {
		return quesAnalyze;
	}
	public void setQuesAnalyze(String quesAnalyze) {
		this.quesAnalyze = quesAnalyze;
	}
	public Integer getDifficultStar() {
		return difficultStar;
	}
	public void setDifficultStar(Integer difficultStar) {
		this.difficultStar = difficultStar;
	}
	public String getVideoPath() {
		return videoPath;
	}
	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}
	public String getAudioListenPath() {
		return audioListenPath;
	}
	public void setAudioListenPath(String audioListenPath) {
		this.audioListenPath = audioListenPath;
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
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
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
	public String getCostTime() {
		return costTime;
	}
	public void setCostTime(String costTime) {
		this.costTime = costTime;
	}
	public List<SimpleTree> getTree() {
		return tree;
	}
	public void setTree(List<SimpleTree> tree) {
		this.tree = tree;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getUserAnswerResul() {
		return userAnswerResul;
	}
	public void setUserAnswerResul(String userAnswerResul) {
		this.userAnswerResul = userAnswerResul;
	}
	public List<BaseProductionModel> getBasetree() {
		return basetree;
	}
	public void setBasetree(List<BaseProductionModel> basetree) {
		this.basetree = basetree;
	}
	public String getLogicType() {
		return logicType;
	}
	public void setLogicType(String logicType) {
		this.logicType = logicType;
	}
	
}