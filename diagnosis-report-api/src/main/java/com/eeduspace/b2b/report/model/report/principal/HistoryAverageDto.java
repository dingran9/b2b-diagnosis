package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 学科历史均分信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 9:53
 **/
@Data
public class HistoryAverageDto implements Serializable{
    /**
     * 平均分
     */
    private Double averageScore;
    /**
     * 发布考试记录code
     */
    private Integer releaseExamCode;
}
