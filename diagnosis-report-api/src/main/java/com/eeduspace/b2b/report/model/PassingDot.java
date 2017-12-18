package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/4/25.
 *  各学科成绩优秀率与及格率 封装返回对象
 */
@Data
public class PassingDot implements Serializable {

    //优秀率
    private String excellent_rate;
    //及格率
    private String passing_rate;
    //学科code
    private String subject_code;
}
