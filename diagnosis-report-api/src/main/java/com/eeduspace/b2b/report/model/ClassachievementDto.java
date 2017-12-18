package com.eeduspace.b2b.report.model;

import com.eeduspace.b2b.report.model.report.SubjectachievementModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuhongfei on 2017/4/27.
 *   单班级 所有学科 贡献率  封装返回对象
 */
@Data
public class ClassachievementDto implements Serializable {

    //班级name
    private String class_name;

    //班级下学科，贡献比率集合
    private List<SubjectachievementModel> subjectachievementModels;

}
