package com.eedu.diagnosis.protal.model.report;

import com.eeduspace.b2b.report.model.report.*;

import java.sql.Timestamp;
import java.util.List;

/**
 * 学生报告返回数据
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-27 19:59
 **/
public class StudentReportVO {
    /***/
    //private Integer code;

    /**做卷记录code*/
    private String makePaperRecordCode;

    /**创建时间*/
    private Timestamp createTime;


    /**更新时间*/
    private Timestamp updateTime;
    /**
     * 诊断试卷code
     */
    private String diagnosisPaperCode;


    /**0 为删除  1已删除 */
    private Integer isDel;


    /**试卷code*/
    private String paperCode;


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
    private List<Columnar> knowledgeGrasp;


    /**错误知识点排行榜图 json 字符串存储*/
    private List<WrongKnowledgeRankModel> wrongKnowledgeRank;


    /**各模块分数上升区间及有效增长点图 json字符串存储*/
    private List<Colorcolumnar> moduleScoreIncrease;


    /**学科能力发展及目标图  json 字符串存储*/
    private List<Radar> subjectAbility;


    /**历史得分波动曲线图 josn 字符串存储*/
    private List<HistoryScoreModel> historicalScore;


    /**考试信息表code*/
    private Integer examCode;

    /**
     * 错题题号
     */
    private List<String> wrongQuestionSn;
    /**
     *  试卷总分
     */
    private String paperScore;

    public String getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
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

    public List<Columnar> getKnowledgeGrasp() {
        return knowledgeGrasp;
    }

    public void setKnowledgeGrasp(List<Columnar> knowledgeGrasp) {
        this.knowledgeGrasp = knowledgeGrasp;
    }

    public List<WrongKnowledgeRankModel> getWrongKnowledgeRank() {
        return wrongKnowledgeRank;
    }

    public void setWrongKnowledgeRank(List<WrongKnowledgeRankModel> wrongKnowledgeRank) {
        this.wrongKnowledgeRank = wrongKnowledgeRank;
    }

    public List<Colorcolumnar> getModuleScoreIncrease() {
        return moduleScoreIncrease;
    }

    public void setModuleScoreIncrease(List<Colorcolumnar> moduleScoreIncrease) {
        this.moduleScoreIncrease = moduleScoreIncrease;
    }

    public List<Radar> getSubjectAbility() {
        return subjectAbility;
    }

    public void setSubjectAbility(List<Radar> subjectAbility) {
        this.subjectAbility = subjectAbility;
    }

    public List<HistoryScoreModel> getHistoricalScore() {
        return historicalScore;
    }

    public void setHistoricalScore(List<HistoryScoreModel> historicalScore) {
        this.historicalScore = historicalScore;
    }

    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }

    public List<String> getWrongQuestionSn() {
        return wrongQuestionSn;
    }

    public void setWrongQuestionSn(List<String> wrongQuestionSn) {
        this.wrongQuestionSn = wrongQuestionSn;
    }
}
