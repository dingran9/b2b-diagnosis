package com.eedu.diagnosis.manager.model.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by dqy on 2017/3/31.
 */
@Data
public class DiagnosisRecordStudentModel {

    private String code;

    private String diagnosisName;

    private Integer studentCode;

    private String diagnosisTeacherRecordCode;

    private String studentName;

    private Date createTime;

    private Date updateTime;

    private Date startTime;

    private Date endTime;

    private String diagnosisPaperCode;

    private String diagnosisPaperName;

    private Integer stageCode;

    private Integer gradeCode;

    private Integer subjectCode;

    private List<Integer> subjectCodes;

    private BigDecimal totalScore;

    private Integer diagnosisStatus;

    private Integer classCode;
    private String schoolName;
    private Integer schoolCode;
    private String className;

    private String unitCode;

    private String unitName;

    private BigDecimal objectiveScore;//客观题得分
    private BigDecimal subjectiveScore;//主观题得分
    private Integer markStatus;
    /**
     * 是否过期 0未过期 1已过期
     */
    private int isExpired = 0;
}
