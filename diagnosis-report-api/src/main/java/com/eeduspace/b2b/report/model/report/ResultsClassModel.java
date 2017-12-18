package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/10.
 */
public class ResultsClassModel implements Serializable{


    public Integer getClassCode() {
        return classCode;
    }

    public void setClassCode(Integer classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<DistributedModel> getDistributedModels() {
        return distributedModels;
    }

    public void setDistributedModels(List<DistributedModel> distributedModels) {
        this.distributedModels = distributedModels;
    }

    private Integer classCode;

    private String className;

    private List<DistributedModel> distributedModels = new ArrayList<>();

}
