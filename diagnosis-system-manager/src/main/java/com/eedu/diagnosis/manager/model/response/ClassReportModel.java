package com.eedu.diagnosis.manager.model.response;

import lombok.Data;

/**
 * Created by dqy on 2017/3/25.
 */
@Data
public class ClassReportModel {

    private String classCode;

    private String className;

    private String diagnosisTeacherRecordCode;

    private Double classAverageValue;//班级平均分


}
