package com.eedu.diagnosis.manager.model.request.ClassTest.machine;

import java.util.List;

public class ExercisesAnswer {
	private String testCode; //卷子code
	private String exercisesCode;//题code
	private String rightAnswer;
	private List<Answer> answers;

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getExercisesCode() {
		return exercisesCode;
	}

	public void setExercisesCode(String exercisesCode) {
		this.exercisesCode = exercisesCode;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	@Override
	public String toString() {
		return "ExercisesAnswer [testCode=" + testCode + ", exercisesCode=" + exercisesCode + ", rightAnswer=" + rightAnswer + ", answers=" + answers + "]";
	}

}
