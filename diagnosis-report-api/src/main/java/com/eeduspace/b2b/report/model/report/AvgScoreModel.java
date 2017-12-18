package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/4/24.
 *  单班级 单学科 平均分 排名  封装返回对象
 */

@Data
public class AvgScoreModel implements Serializable {

    //平均分
    private String avgscore;

    //排名
    private String ranking;

    //学科code
    private String subjectcode;


}
