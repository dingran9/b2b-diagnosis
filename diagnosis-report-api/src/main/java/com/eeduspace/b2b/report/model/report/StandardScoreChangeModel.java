package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 标准分变化实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-08-31 11:16
 **/
@Data
public class StandardScoreChangeModel implements Serializable{
    /**
     * 班级集合
     */
    List<ClassModel> classModels;
    /**
     * 考试次数集合
     */
    List<ExamModel> examModels;
    /**
     *班级平均标分
     */
    List<ClassStandardModel> classStandardModels;

}
