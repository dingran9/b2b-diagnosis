package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * 合格率实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 9:21
 **/
public class PassRateModel implements Serializable{
    /**
     * 及格数量
     */
    private Integer jige;
    /**
     * 不及格数量
     */
    private Integer bujige;
    /**
     * 优秀数量
     */
    private Integer youxiu;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 总人数
     */
    private Integer totalCount;

    public Integer getJige() {
        return jige;
    }

    public void setJige(Integer jige) {
        this.jige = jige;
    }

    public Integer getBujige() {
        return bujige;
    }

    public void setBujige(Integer bujige) {
        this.bujige = bujige;
    }

    public Integer getYouxiu() {
        return youxiu;
    }

    public void setYouxiu(Integer youxiu) {
        this.youxiu = youxiu;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
