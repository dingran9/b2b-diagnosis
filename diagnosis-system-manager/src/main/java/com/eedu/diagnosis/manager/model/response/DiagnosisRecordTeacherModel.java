package com.eedu.diagnosis.manager.model.response;

import com.eedu.diagnosis.manager.model.BaseModel;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/20.
 */
@Data
public class DiagnosisRecordTeacherModel extends BaseModel{
    private String code;

    private String diagnosisName;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    private Integer schoolCode;

    private String schoolName;

    private Integer stageCode;

    private Integer gradeCode;
    /**班级codes*/
    private List<Integer> classCodes;
    /**是否全年级发布 0否1是*/
    private Integer isAllGrade;
    /** 测试类型 0单元测试 1期中 2期末 3模拟考 4会考 */
    private Integer examType;

    private Integer subjectCode;

    private Integer teacherCode;

    private String teacherName;

    private Integer diagnosisType;

    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    private Integer isSnapshot;
    //已出班级报告的数量
    private Long hasClassReportCount;
    //是否已判卷
    private Integer markStatus;

    private String examYear;//考试年份   eg. 2017-1 2017年上学期 2017-2 2017年下学期

}
