package com.eedu.diagnosis.protal.model.response.question;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentQuestion implements Serializable {
	  //用户答题答案
    private String userAnswerResul;
    //学生本题得分
    private Double studentGetScore;
    //本题卷面得分
    private Double surfaceScore;
	private String stem;
	private String quesOption;
	private String quesAnalyze;
	private Integer questionNo;
    private String questionAudio;
    private  List<BaseProductionModel> baseProductionJson;
    /**
     * 教材树产生式  用来前端显示知识点
     */
    private List<ProductionModel> knowledgeJson;
	private List<SimpleTree> tree=new ArrayList<SimpleTree>(0);
	 private List<BaseProductionModel> basetree=new ArrayList<BaseProductionModel>(0);

    public List<ProductionModel> getKnowledgeJson() {
        return knowledgeJson;
    }

    public void setKnowledgeJson(List<ProductionModel> knowledgeJson) {
        this.knowledgeJson = knowledgeJson;
    }

    public List<BaseProductionModel> getBaseProductionJson() {
        return baseProductionJson;
    }

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

    public void setBaseProductionJson(List<BaseProductionModel> baseProductionJson) {
        this.baseProductionJson = baseProductionJson;
    }

    public String getQuestionAudio() {
        return questionAudio;
    }

    public void setQuestionAudio(String questionAudio) {
        this.questionAudio = questionAudio;
    }

    public Integer getQuestionNo() {
		return questionNo;
	}

	public void setQuestionNo(Integer questionNo) {
		this.questionNo = questionNo;
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

	public String getQuesAnalyze() {
		return quesAnalyze;
	}

	public void setQuesAnalyze(String quesAnalyze) {
		this.quesAnalyze = quesAnalyze;
	}

	public List<SimpleTree> getTree() {
		return tree;
	}

	public void setTree(List<SimpleTree> tree) {
		this.tree = tree;
	}

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.id
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.answer
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String answer;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.type
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.score
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private Double score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.sort
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private Integer sort;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.ques_id
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String quesId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.product
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String product;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.product_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String productCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.knowledge
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String knowledge;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.knowledge_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String knowledgeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.subject_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String subjectCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column component_question.voice
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private String voice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table component_question
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.id
     *
     * @return the value of component_question.id
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.id
     *
     * @param id the value for component_question.id
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.answer
     *
     * @return the value of component_question.answer
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.answer
     *
     * @param answer the value for component_question.answer
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.type
     *
     * @return the value of component_question.type
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.type
     *
     * @param type the value for component_question.type
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.score
     *
     * @return the value of component_question.score
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public Double getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.score
     *
     * @param score the value for component_question.score
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setScore(Double score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.sort
     *
     * @return the value of component_question.sort
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.sort
     *
     * @param sort the value for component_question.sort
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.ques_id
     *
     * @return the value of component_question.ques_id
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getQuesId() {
        return quesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.ques_id
     *
     * @param quesId the value for component_question.ques_id
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.product
     *
     * @return the value of component_question.product
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getProduct() {
        return product;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.product
     *
     * @param product the value for component_question.product
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.product_code
     *
     * @return the value of component_question.product_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.product_code
     *
     * @param productCode the value for component_question.product_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.knowledge
     *
     * @return the value of component_question.knowledge
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getKnowledge() {
        return knowledge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.knowledge
     *
     * @param knowledge the value for component_question.knowledge
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.knowledge_code
     *
     * @return the value of component_question.knowledge_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getKnowledgeCode() {
        return knowledgeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.knowledge_code
     *
     * @param knowledgeCode the value for component_question.knowledge_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setKnowledgeCode(String knowledgeCode) {
        this.knowledgeCode = knowledgeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.subject_code
     *
     * @return the value of component_question.subject_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.subject_code
     *
     * @param subjectCode the value for component_question.subject_code
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column component_question.voice
     *
     * @return the value of component_question.voice
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public String getVoice() {
        return voice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column component_question.voice
     *
     * @param voice the value for component_question.voice
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    public void setVoice(String voice) {
        this.voice = voice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table component_question
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ComponentQuestion other = (ComponentQuestion) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getQuesId() == null ? other.getQuesId() == null : this.getQuesId().equals(other.getQuesId()))
            && (this.getProduct() == null ? other.getProduct() == null : this.getProduct().equals(other.getProduct()))
            && (this.getProductCode() == null ? other.getProductCode() == null : this.getProductCode().equals(other.getProductCode()))
            && (this.getKnowledge() == null ? other.getKnowledge() == null : this.getKnowledge().equals(other.getKnowledge()))
            && (this.getKnowledgeCode() == null ? other.getKnowledgeCode() == null : this.getKnowledgeCode().equals(other.getKnowledgeCode()))
            && (this.getSubjectCode() == null ? other.getSubjectCode() == null : this.getSubjectCode().equals(other.getSubjectCode()))
            && (this.getVoice() == null ? other.getVoice() == null : this.getVoice().equals(other.getVoice()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table component_question
     *
     * @mbggenerated Sat Aug 27 16:42:57 CST 2016
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getQuesId() == null) ? 0 : getQuesId().hashCode());
        result = prime * result + ((getProduct() == null) ? 0 : getProduct().hashCode());
        result = prime * result + ((getProductCode() == null) ? 0 : getProductCode().hashCode());
        result = prime * result + ((getKnowledge() == null) ? 0 : getKnowledge().hashCode());
        result = prime * result + ((getKnowledgeCode() == null) ? 0 : getKnowledgeCode().hashCode());
        result = prime * result + ((getSubjectCode() == null) ? 0 : getSubjectCode().hashCode());
        result = prime * result + ((getVoice() == null) ? 0 : getVoice().hashCode());
        return result;
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
	
}