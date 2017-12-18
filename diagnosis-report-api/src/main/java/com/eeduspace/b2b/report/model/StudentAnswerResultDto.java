package com.eeduspace.b2b.report.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>描述 学生答题结果信息</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 16:16
**/
public class StudentAnswerResultDto extends BaseCommonModel implements Serializable{
    /**
     * 试卷达标分数
     */
    @NotNull(message = "paperStandardScore is  null")
    Double paperStandardScore;
    @NotBlank(message = "paperCode is  null")
    String paperCode;
    /**
     * 学期  格式  如 2017上学期   20171   2017下学期    20172
     */
    String semester;
    /**
     * 试卷总分之和
     */
    Double totalScore;
    /**
     * 文理类型  0 无 1 文 2理
     */
    @NotNull(message = "artType is  null")
    Integer artType;

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    /**
     * 考试类型
     *   UNIT(0,"单元测试"),
         MID_EXAM(1,"期中考试"),
         FINAL_EXAM(2,"期末考试"),
         SIMULATION_TEST(3,"模拟考试"),
         WILL_EXAM(4,"会考");
     */
    String examType;

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    /**
     * 诊断试卷code
     */
    @NotBlank(message = "diagnosisPaperCode is  null")
    String diagnosisPaperCode;
    /**
     * 试卷总分
     */
    @NotBlank(message = "paperScore is  null")
    String paperScore;
    /**
     * 试卷名
     */
    @NotBlank(message = "paperName is  null")
    String paperName;
    /**
     * 用户code
     */
    @NotBlank(message = "userCode is  null")
    String userCode;
    /**
     * 用户名称
     */
    @NotBlank(message = "userName is  null")
    String userName;
    /**
     * 发布考试记录code   (指一次考试的考试记录)
     */
    @NotBlank(message = "releaseCode is  null")
    String releaseCode;
    /**
     * 发布考试记录名称   (指一次考试的考试记录)
     */
    String releaseName;

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    /**
     * 答卷记录code
     */
    @NotBlank(message = "markPaperRecordCode is  null")
    String markPaperRecordCode;

    /**答卷用时   单位 秒*/
    @NotNull(message = "userTime is null")
    private Integer useTime;

    /**答卷得分 */
    @NotNull(message = "score is null")
    private Double score;

    /**答对题数*/
    @NotNull(message = "rightCount is null")
    private Integer rightCount;

    /**答错题数*/
    @NotNull(message = "wrongCount is null")
    private Integer wrongCount;
    /**答卷时间 记录开始考试时间*/
    private Timestamp makePaperTime;
    /**
     * 单个试题单题结果信息
     */
    @NotEmpty(message = "studentQuestionAnswerResultDtos is null")
    List<StudentQuestionAnswerResultDto> studentQuestionAnswerResultDtos;

    /**
     * 历史分数波动分数数据
     */
    @NotBlank(message = "historicalScore is null")
    private String historicalScore;
    public String getHistoricalScore() {
        return historicalScore;
    }

    public void setHistoricalScore(String historicalScore) {
        this.historicalScore = historicalScore;
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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReleaseCode() {
        return releaseCode;
    }

    public void setReleaseCode(String releaseCode) {
        this.releaseCode = releaseCode;
    }

    public String getMarkPaperRecordCode() {
        return markPaperRecordCode;
    }

    public void setMarkPaperRecordCode(String markPaperRecordCode) {
        this.markPaperRecordCode = markPaperRecordCode;
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
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

    public Double getPaperStandardScore() {
        return paperStandardScore;
    }

    public void setPaperStandardScore(Double paperStandardScore) {
        this.paperStandardScore = paperStandardScore;
    }

    public void setWrongCount(Integer wrongCount) {
        this.wrongCount = wrongCount;
    }

    public Timestamp getMakePaperTime() {
        return makePaperTime;
    }

    public void setMakePaperTime(Timestamp makePaperTime) {
        this.makePaperTime = makePaperTime;
    }

    public List<StudentQuestionAnswerResultDto> getStudentQuestionAnswerResultDtos() {
        return studentQuestionAnswerResultDtos;
    }

    public void setStudentQuestionAnswerResultDtos(List<StudentQuestionAnswerResultDto> studentQuestionAnswerResultDtos) {
        this.studentQuestionAnswerResultDtos = studentQuestionAnswerResultDtos;
    }

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
}
