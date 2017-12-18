package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class ClassHappenModel implements Serializable{

    //学校ID
    private Integer SchoolId;
    //学校名称
    private String schoolName;

    public Integer getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(Integer schoolId) {
        SchoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public List<HappenModel> getHappenModels() {
        return happenModels;
    }

    public void setHappenModels(List<HappenModel> happenModels) {
        this.happenModels = happenModels;
    }

    private List<HappenModel> happenModels = new ArrayList<>();


}
