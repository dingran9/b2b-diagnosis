package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/10.
 */
public class ResultsModel implements Serializable{


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

    public List<DistributedModel> getDistributedModels() {
        return distributedModels;
    }

    public void setDistributedModels(List<DistributedModel> distributedModels) {
        this.distributedModels = distributedModels;
    }

    private Integer schoolCode;

    private String schoolName;

    public List<ResultsClassModel> getResultsClassModels() {
        return resultsClassModels;
    }

    public void setResultsClassModels(List<ResultsClassModel> resultsClassModels) {
        this.resultsClassModels = resultsClassModels;
    }

    private List<DistributedModel> distributedModels = new ArrayList<>();

    private List<ResultsClassModel> resultsClassModels = new ArrayList<>();



}
