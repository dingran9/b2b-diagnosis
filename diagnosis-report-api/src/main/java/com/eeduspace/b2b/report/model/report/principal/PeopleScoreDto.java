package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 分数分布与人员数量
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-25 16:58
 **/
@Data
public class PeopleScoreDto implements Serializable{
    /**
     * 人数
     */
    private Long studentCount;
    /**
     * 分数区间
     */
    private String scoreInterval;
    /**
     * 比例
     */
    private String proportion;
}
