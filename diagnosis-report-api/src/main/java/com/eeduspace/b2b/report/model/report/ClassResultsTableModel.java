package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.Map;

/**
 * 班级成绩表
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-20 20:25
 **/

public class ClassResultsTableModel implements Serializable{

    private long count;
    private Double sum;
    private Double min;
    private Double max;
    //班级名称
    private String className;

    //学科优秀率统计
    Map subjectCount;
    //班级分数段统计
    Map scoreCount;
    //平均分
    Map rankingAverage;
    /**
     * 班级平均分
     */
    private Double classAverage;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Map getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(Map subjectCount) {
        this.subjectCount = subjectCount;
    }

    public Map getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Map scoreCount) {
        this.scoreCount = scoreCount;
    }

    public Map getRankingAverage() {
        return rankingAverage;
    }

    public void setRankingAverage(Map rankingAverage) {
        this.rankingAverage = rankingAverage;
    }

    public Double getClassAverage() {
        return classAverage;
    }

    public void setClassAverage(Double classAverage) {
        this.classAverage = classAverage;
    }
}
