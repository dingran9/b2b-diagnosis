package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/9/29.
 */
public class DistributedModel implements Serializable{

    //分数段
    private String segment;

    //人数
    private Integer peopleCount;

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Double getPercentage() {
        return percentage;
    }

    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    private Double percentage;

    private Integer sn;
}

