package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-08-31 13:16
 **/
@Data
public class ExamModel implements Serializable{
    private Integer examCode;
    private Timestamp examTime;
}

