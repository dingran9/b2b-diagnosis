package com.eeduspace.b2b.report.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>描述 学生报告实体</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 15:15
 * @param    
 * @return   
**/
public class StudentReportDto implements Serializable{
    /***/
    //private Integer code;

    /**做卷记录code*/
    private String makePaperRecordCode;

    /**创建时间*/
    private Timestamp createTime;


    /**更新时间*/
    private Timestamp updateTime;


    /**0 为删除  1已删除 */
    private Integer isDel;


    /**试卷code*/
    private String paperCode;
    /**
     * 诊断试卷code
     */
    private String diagnosisPaperCode;


    /**试卷名称*/
    private String paperName;


    /**答卷用时   单位 秒*/
    private Integer useTime;


    /**答卷时间 记录开始考试时间*/
    private Timestamp makePaperTime;


    /**答卷得分 */
    private Double score;


    /**答对题数*/
    private Integer rightCount;


    /**答错题数*/
    private Integer wrongCount;


    /**模块知识掌握情况图   json字符串格式存储*/
    private String knowledgeGrasp;


    /**错误知识点排行榜图 json 字符串存储*/
    private String wrongKnowledgeRank;


    /**各模块分数上升区间及有效增长点图 json字符串存储*/
    private String moduleScoreIncrease;


    /**学科能力发展及目标图  json 字符串存储*/
    private String subjectAbility;


    /**历史得分波动曲线图 josn 字符串存储*/
    private String historicalScore;


    /**考试信息表code*/
    private Integer examCode;

    /**
     * 错题题号
     */
    private String wrongQuestionSn;
    /**
     * 试卷总分
     */
    private String paperScore;

    public String getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    /**
     * 学年code
     */
    private String gradeCode;


    public String getWrongQuestionSn() {
        return wrongQuestionSn;
    }

    public void setWrongQuestionSn(String wrongQuestionSn) {
        this.wrongQuestionSn = wrongQuestionSn;
    }


    public String getMakePaperRecordCode() {
        return makePaperRecordCode;
    }

    public void setMakePaperRecordCode(String makePaperRecordCode) {
        this.makePaperRecordCode = makePaperRecordCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    public Timestamp getMakePaperTime() {
        return makePaperTime;
    }

    public void setMakePaperTime(Timestamp makePaperTime) {
        this.makePaperTime = makePaperTime;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public String getKnowledgeGrasp() {
        return knowledgeGrasp;
    }

    public void setKnowledgeGrasp(String knowledgeGrasp) {
        this.knowledgeGrasp = knowledgeGrasp;
    }

    public String getWrongKnowledgeRank() {
        return wrongKnowledgeRank;
    }

    public void setWrongKnowledgeRank(String wrongKnowledgeRank) {
        this.wrongKnowledgeRank = wrongKnowledgeRank;
    }

    public String getModuleScoreIncrease() {
        return moduleScoreIncrease;
    }

    public void setModuleScoreIncrease(String moduleScoreIncrease) {
        this.moduleScoreIncrease = moduleScoreIncrease;
    }

    public String getSubjectAbility() {
        return subjectAbility;
    }

    public void setSubjectAbility(String subjectAbility) {
        this.subjectAbility = subjectAbility;
    }

    public String getHistoricalScore() {
        return historicalScore;
    }

    public void setHistoricalScore(String historicalScore) {
        this.historicalScore = historicalScore;
    }

    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
    }
}
