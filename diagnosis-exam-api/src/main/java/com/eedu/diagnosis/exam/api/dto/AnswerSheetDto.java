package com.eedu.diagnosis.exam.api.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 答题卡
 */
public class AnswerSheetDto implements Serializable{
	/**
	 * 学生诊断记录code
	 */
	private String code;
	/**
	 * 答题卡来源 0诊断1作业
	 */
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
	 * 答题用时
	 */
	private String useTime;
	/**
	 * 学科code
	 */
	private Integer subjectCode;
	/**
	 * 班级code  冗余
	 */
	private Integer classCode;
	private String bookVersionCode;
	/**
	 * 答题卡详情内容
	 */
	private List<AnswerSheetQuestionDto> answerSheetQuestionDtos;
	/**
	 * 资源库试卷code
	 */
	private String paperCode;
	/**
	 * 诊断试卷code
	 */
	private String diagnosisPaperCode;
	/**
	 * 试卷名称
	 */
	private String diagnosisPaperName;
	/**
	 * 是否需要判卷 0不需要 1需要
	 */
	private Integer needMark;
	/**
	 * 试卷总分
	 */
	private Double totalScore;
	/**
	 * 学生得分
	 */
	private Double  getScore;
	/**
	 * 达标分
	 */
	private Double istopic;
	/**
	 * 学生code
	 */
	private Integer studentCode;
	/**
	 * 学生名称
	 */
	private String studentName;
	/**
	 * 对应的教师发布诊断记录code
	 */
	private String diagnosisTeacherRecordCode;
	private Integer schoolCode;
	private String schoolName;
	private String className;
	/**
	 * 学年
	 */
	private Integer gradeCode;
	/**
	 *学段小初高 123
	 */
	private Integer stageCode;
	/**
	 * 诊断状态  0默认已发送 1已提交 2已出报告 3报告异常
	 */
	private Integer diagnosisStatus;

	private Integer rightCout;

	private Integer wrongCout;

	public Integer getExamType() {
		return examType;
	}

	public void setExamType(Integer examType) {
		this.examType = examType;
	}

	public Double getGetScore() {
		return getScore;
	}

	public void setGetScore(Double getScore) {
		this.getScore = getScore;
	}

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

	public List<AnswerSheetQuestionDto> getAnswerSheetQuestionDtos() {
		return answerSheetQuestionDtos;
	}

	public void setAnswerSheetQuestionDtos(List<AnswerSheetQuestionDto> answerSheetQuestionDtos) {
		this.answerSheetQuestionDtos = answerSheetQuestionDtos;
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

	public Integer getNeedMark() {
		return needMark;
	}

	public void setNeedMark(Integer needMark) {
		this.needMark = needMark;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
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

	public Integer getDiagnosisStatus() {
		return diagnosisStatus;
	}

	public void setDiagnosisStatus(Integer diagnosisStatus) {
		this.diagnosisStatus = diagnosisStatus;
	}

	public Integer getRightCout() {
		return rightCout;
	}

	public void setRightCout(Integer rightCout) {
		this.rightCout = rightCout;
	}

	public Integer getWrongCout() {
		return wrongCout;
	}

	public void setWrongCout(Integer wrongCout) {
		this.wrongCout = wrongCout;
	}
}
