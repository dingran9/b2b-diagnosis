package com.eedu.diagnosis.paper.persist.model.knowledge;
import java.util.List;




public class ParamQuestionEntity {
	
	   private String id;
	   private String stem;
	   
	    private String title;
	    private String quesOption;
	    private String quesAnalyze;
	    
	    private String stemPicture;
	    private  String createDate;
	   // private Date makeTime;
	    //private Date checkTime;
	    private String updateDate;
	   
		private String optionPicture;

	   
	    private String anayzePicture;

	    
	    private String answer;

	   
	    private Integer difficultStar;

	   
	    private String quesComment;

	    private String remark;

	   
	    private String videoPath;

	  
	    private String audioListenPath;

	   
	    private String source;

	    
	    private String iscompleted;

	    
	    private String type;

	    
	    private String isdel;

	  
	    private String stageCode;

	  
	    private String subjectName;

	    
	    private String gradeCode;

	   
	    private String ischecked;

	   
	    private String checkName;

	    private String gradeRules;

	    private Double score;

	    private String costTime;
	   
	    private String estimateTime;
	    private String makeName;
	    private Integer useTimes;
	   
	    private String createBy;
	    private String createName;
	    private String updateBy;

	   
	    private String updateName;

	   
	    private String application;

	   
	    private String audioAnalyzePath;

	    
	    private String productions;

	    
	    private String productionCode;

	   
	    private String enlargeId;

	   
	    private String knowledges;

	   
	    private String fitCode;
	
		private List<QuesionOptionModel> quesOptions;
		/**
		 * 解析信息
		 */
		private List<QuestionAnalysisModel> quesAnalyzes;

	


		public List<QuesionOptionModel> getQuesOptions() {
			return quesOptions;
		}


		public void setQuesOptions(List<QuesionOptionModel> quesOptions) {
			this.quesOptions = quesOptions;
		}


		public List<QuestionAnalysisModel> getQuesAnalyzes() {
			return quesAnalyzes;
		}


		public void setQuesAnalyzes(List<QuestionAnalysisModel> quesAnalyzes) {
			this.quesAnalyzes = quesAnalyzes;
		}


		public String getId() {
			return id;
		}


		public void setId(String id) {
			this.id = id;
		}

		 public String getStem() {
				return stem;
			}


			public void setStem(String stem) {
				this.stem = stem;
			}
		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}
		public String getQuesOption() {
			return quesOption;
		}


		public void setQuesOption(String quesOption) {
			this.quesOption = quesOption;
		}
		public String getStemPicture() {
			return stemPicture;
		}


		public void setStemPicture(String stemPicture) {
			this.stemPicture = stemPicture;
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


		public Integer getDifficultStar() {
			return difficultStar;
		}


		public void setDifficultStar(Integer difficultStar) {
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


		public String getAudioListenPath() {
			return audioListenPath;
		}


		public void setAudioListenPath(String audioListenPath) {
			this.audioListenPath = audioListenPath;
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




		public String getGradeRules() {
			return gradeRules;
		}


		public void setGradeRules(String gradeRules) {
			this.gradeRules = gradeRules;
		}


		public Double getScore() {
			return score;
		}


		public void setScore(Double score) {
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


	

		public Integer getUseTimes() {
			return useTimes;
		}


		public void setUseTimes(Integer useTimes) {
			this.useTimes = useTimes;
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


		public String getCreateDate() {
			return createDate;
		}


		public void setCreateDate(String createDate) {
			this.createDate = createDate;
		}


		public String getUpdateDate() {
			return updateDate;
		}


		public void setUpdateDate(String updateDate) {
			this.updateDate = updateDate;
		}
		
//		  public Date getMakeTime() {
//				return makeTime;
//			}
//
//
//			public void setMakeTime(Date makeTime) {
//				this.makeTime = makeTime;
//			}


//			public Date getCheckTime() {
//				return checkTime;
//			}
//
//
//			public void setCheckTime(Date checkTime) {
//				this.checkTime = checkTime;
//			}

		
	
}
