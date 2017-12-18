package com.eedu.diagnosis.protal.model.request;

import com.eedu.diagnosis.exam.api.dto.AnswerSheetQuestionDto;
import com.eedu.diagnosis.protal.model.BaseModel;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author Qingyan_dong
 * @Date 2016年9月1日 上午9:22:24
 * @description 答题卡model
 */
public class AnswerSheetModel extends BaseModel {
    /**
     * 学生诊断记录/作业记录code
     */
    @NotBlank(message = "code")
    private String code;

    /**
     * 答题卡来源 0诊断1作业
     */
    @NotNull(message = "resourceType")
    private Integer resourceType;
    /**
     * 考试类型
     * UNIT(0,"单元测试"),
     * MID_EXAM(1,"期中考试"),
     * FINAL_EXAM(2,"期末考试"),
     * SIMULATION_TEST(3,"模拟考试"),
     * WILL_EXAM(4,"会考");
     */
    private Integer examType;
    /**
     * 文理类型  2 无 0 文 1理
     */
    @NotNull(message = "artType")
    private Integer artType;
    /**
     * 考试用时
     */
    @NotBlank(message = "useTime")
    private String useTime;

    /**
     * 学科code
     */
    private Integer subjectCode;

    /**
     * 学生的班级code
     */
    @NotNull(message = "classCode")
    private Integer classCode;

    /**
     * 教材版本code
     */
    private String bookVersionCode;

    /**
     * 学生code
     */
    @NotNull(message = "studentCode")
    private Integer studentCode;

    /**
     * 学生名称
     */
    @NotBlank(message = "studentName")
    private String studentName;

    @NotNull(message = "needMark")
    private Integer needMark;//是否需要判卷 0不需要 1需要

    private String totalScore;//试卷总分
    /**达标分*/
    private Double istopic;
    /**
     * 答题卡详情内容
     */
    @NotEmpty(message = "answerSheetQuestionDtos")
    private List<AnswerSheetQuestionDto> answerSheetQuestionDtos;
    /**
     * 资源库试卷code
     */
    @NotBlank(message = "paperCode")
    private String paperCode;
    /**
     * 诊断用卷code
     */
    @NotBlank(message = "diagnosisPaperCode")
    private String diagnosisPaperCode;
    /**
     * 综合诊断卷code
     */
    @NotBlank(message = "diagnosisPaperName")
    private String diagnosisPaperName;

    /**
     * 对应的教师发布诊断记录code
     */
    @NotBlank(message = "diagnosisTeacherRecordCode")
    private String diagnosisTeacherRecordCode;

    /**
     * 学校code
     */
    @NotNull(message = "schoolCode")
    private Integer schoolCode;

    @NotBlank(message = "schoolName")
    private String schoolName;

    @NotBlank(message = "className")
    private String className;

    /**
     * 学年code
     */
    @NotNull(message = "gradeCode")
    private Integer gradeCode;

    /**
     * 学段  小初高 123
     */
    @NotNull(message = "stageCode")
    private Integer stageCode;


    public Double getIstopic() {
        return istopic;
    }

    public void setIstopic(Double istopic) {
        this.istopic = istopic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getResourceType() {
        return resourceType;
    }

    public void setResourceType(Integer resourceType) {
        this.resourceType = resourceType;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public Integer getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(Integer subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getBookVersionCode() {
        return bookVersionCode;
    }

    public void setBookVersionCode(String bookVersionCode) {
        this.bookVersionCode = bookVersionCode;
    }

    public Integer getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(Integer studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getNeedMark() {
        return needMark;
    }

    public void setNeedMark(Integer needMark) {
        this.needMark = needMark;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public List<AnswerSheetQuestionDto> getAnswerSheetQuestionDtos() {
        return answerSheetQuestionDtos;
    }

    public void setAnswerSheetQuestionDtos(List<AnswerSheetQuestionDto> answerSheetQuestionDtos) {
        this.answerSheetQuestionDtos = answerSheetQuestionDtos;
    }

    public Integer getArtType() {
        return artType;
    }

    public void setArtType(Integer artType) {
        this.artType = artType;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public String getDiagnosisPaperCode() {
        return diagnosisPaperCode;
    }

    public void setDiagnosisPaperCode(String diagnosisPaperCode) {
        this.diagnosisPaperCode = diagnosisPaperCode;
    }

    public String getDiagnosisPaperName() {
        return diagnosisPaperName;
    }

    public void setDiagnosisPaperName(String diagnosisPaperName) {
        this.diagnosisPaperName = diagnosisPaperName;
    }

    public String getDiagnosisTeacherRecordCode() {
        return diagnosisTeacherRecordCode;
    }

    public void setDiagnosisTeacherRecordCode(String diagnosisTeacherRecordCode) {
        this.diagnosisTeacherRecordCode = diagnosisTeacherRecordCode;
    }

    public Integer getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(Integer schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(Integer gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getStageCode() {
        return stageCode;
    }

    public void setStageCode(Integer stageCode) {
        this.stageCode = stageCode;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }
}
