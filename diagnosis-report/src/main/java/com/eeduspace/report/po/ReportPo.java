package com.eeduspace.report.po;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhuchaowei on 2017/3/15.
 */
@Entity
@Table(name = "edu_report", schema = "b2b_report", catalog = "")
public class ReportPo {
    private int code;
    private String makePaperRecordCode;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer isDel;
    private String paperCode;
    private String paperName;
    private Integer useTime;
    private Timestamp makePaperTime;
    private Double score;
    private Integer rightCount;
    private Integer wrongCount;
    private String knowledgeGrasp;
    private String wrongKnowledgeRank;
    private String moduleScoreIncrease;
    private String subjectAbility;
    private String historicalScore;
    private Integer examCode;
    private String diagnosisPaperCode;
    private String gradeCode;
    private String paperScore;
    @Basic
    @Column(name = "paper_score",length = 10)
    public String getPaperScore() {
        return paperScore;
    }

    public void setPaperScore(String paperScore) {
        this.paperScore = paperScore;
    }

    @Basic
    @Column(name = "grade_code")
    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    private String wrongQuestionSn;
    @Basic
    @Column(name = "wrong_question_sn")
    public String getWrongQuestionSn() {
        return wrongQuestionSn;
    }

    public void setWrongQuestionSn(String wrongQuestionSn) {
        this.wrongQuestionSn = wrongQuestionSn;
    }

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    @Basic
    @Column(name = "diagnosis_paper_code")
    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
    }

    @Basic
    @Column(name = "make_paper_record_code")
    public String getMakePaperRecordCode() {
        return makePaperRecordCode;
    }

    public void setMakePaperRecordCode(String makePaperRecordCode) {
        this.makePaperRecordCode = makePaperRecordCode;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "is_del")
    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    @Basic
    @Column(name = "paper_code")
    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    @Basic
    @Column(name = "paper_name")
    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    @Basic
    @Column(name = "use_time")
    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }

    @Basic
    @Column(name = "make_paper_time")
    public Timestamp getMakePaperTime() {
        return makePaperTime;
    }

    public void setMakePaperTime(Timestamp makePaperTime) {
        this.makePaperTime = makePaperTime;
    }

    @Basic
    @Column(name = "score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Basic
    @Column(name = "right_count")
    public Integer getRightCount() {
        return rightCount;
    }

    public void setRightCount(Integer rightCount) {
        this.rightCount = rightCount;
    }

    @Basic
    @Column(name = "wrong_count")
    public Integer getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    @Basic
    @Column(name = "knowledge_grasp")
    public String getKnowledgeGrasp() {
        return knowledgeGrasp;
    }

    public void setKnowledgeGrasp(String knowledgeGrasp) {
        this.knowledgeGrasp = knowledgeGrasp;
    }

    @Basic
    @Column(name = "wrong_knowledge_rank")
    public String getWrongKnowledgeRank() {
        return wrongKnowledgeRank;
    }

    public void setWrongKnowledgeRank(String wrongKnowledgeRank) {
        this.wrongKnowledgeRank = wrongKnowledgeRank;
    }

    @Basic
    @Column(name = "module_score_increase")
    public String getModuleScoreIncrease() {
        return moduleScoreIncrease;
    }

    public void setModuleScoreIncrease(String moduleScoreIncrease) {
        this.moduleScoreIncrease = moduleScoreIncrease;
    }

    @Basic
    @Column(name = "subject_ability")
    public String getSubjectAbility() {
        return subjectAbility;
    }

    public void setSubjectAbility(String subjectAbility) {
        this.subjectAbility = subjectAbility;
    }

    @Basic
    @Column(name = "historical_score")
    public String getHistoricalScore() {
        return historicalScore;
    }

    public void setHistoricalScore(String historicalScore) {
        this.historicalScore = historicalScore;
    }

    @Basic
    @Column(name = "exam_code")
    public Integer getExamCode() {
        return examCode;
    }

    public void setExamCode(Integer examCode) {
        this.examCode = examCode;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportPo reportPo = (ReportPo) o;

        if (code != reportPo.code) return false;
        if (makePaperRecordCode != null ? !makePaperRecordCode.equals(reportPo.makePaperRecordCode) : reportPo.makePaperRecordCode != null)
            return false;
        if (createTime != null ? !createTime.equals(reportPo.createTime) : reportPo.createTime != null) return false;
        if (updateTime != null ? !updateTime.equals(reportPo.updateTime) : reportPo.updateTime != null) return false;
        if (isDel != null ? !isDel.equals(reportPo.isDel) : reportPo.isDel != null) return false;
        if (paperCode != null ? !paperCode.equals(reportPo.paperCode) : reportPo.paperCode != null) return false;
        if (paperName != null ? !paperName.equals(reportPo.paperName) : reportPo.paperName != null) return false;
        if (useTime != null ? !useTime.equals(reportPo.useTime) : reportPo.useTime != null) return false;
        if (makePaperTime != null ? !makePaperTime.equals(reportPo.makePaperTime) : reportPo.makePaperTime != null)
            return false;
        if (score != null ? !score.equals(reportPo.score) : reportPo.score != null) return false;
        if (rightCount != null ? !rightCount.equals(reportPo.rightCount) : reportPo.rightCount != null) return false;
        if (wrongCount != null ? !wrongCount.equals(reportPo.wrongCount) : reportPo.wrongCount != null) return false;
        if (knowledgeGrasp != null ? !knowledgeGrasp.equals(reportPo.knowledgeGrasp) : reportPo.knowledgeGrasp != null)
            return false;
        if (wrongKnowledgeRank != null ? !wrongKnowledgeRank.equals(reportPo.wrongKnowledgeRank) : reportPo.wrongKnowledgeRank != null)
            return false;
        if (moduleScoreIncrease != null ? !moduleScoreIncrease.equals(reportPo.moduleScoreIncrease) : reportPo.moduleScoreIncrease != null)
            return false;
        if (subjectAbility != null ? !subjectAbility.equals(reportPo.subjectAbility) : reportPo.subjectAbility != null)
            return false;
        if (historicalScore != null ? !historicalScore.equals(reportPo.historicalScore) : reportPo.historicalScore != null)
            return false;
        if (examCode != null ? !examCode.equals(reportPo.examCode) : reportPo.examCode != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (makePaperRecordCode != null ? makePaperRecordCode.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        result = 31 * result + (paperCode != null ? paperCode.hashCode() : 0);
        result = 31 * result + (paperName != null ? paperName.hashCode() : 0);
        result = 31 * result + (useTime != null ? useTime.hashCode() : 0);
        result = 31 * result + (makePaperTime != null ? makePaperTime.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (rightCount != null ? rightCount.hashCode() : 0);
        result = 31 * result + (wrongCount != null ? wrongCount.hashCode() : 0);
        result = 31 * result + (knowledgeGrasp != null ? knowledgeGrasp.hashCode() : 0);
        result = 31 * result + (wrongKnowledgeRank != null ? wrongKnowledgeRank.hashCode() : 0);
        result = 31 * result + (moduleScoreIncrease != null ? moduleScoreIncrease.hashCode() : 0);
        result = 31 * result + (subjectAbility != null ? subjectAbility.hashCode() : 0);
        result = 31 * result + (historicalScore != null ? historicalScore.hashCode() : 0);
        result = 31 * result + (examCode != null ? examCode.hashCode() : 0);

        return result;
    }
}
