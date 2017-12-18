package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/4/27.
 *   单班级，单学科 贡献率  封装返回对象
 */
@Data
public class SubjectachievementModel implements Serializable {

    //学科code
    private String subject_code;

    //贡献率比值
    private String ratio;
}
