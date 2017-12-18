package com.eeduspace.b2b.report.model.report;

import lombok.Data;

import java.io.Serializable;

/**
 * 历史分数
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-27 20:23
 **/
@Data
public class HistoryScoreModel implements Serializable{
    private String examDate;
    private Double score;
}
