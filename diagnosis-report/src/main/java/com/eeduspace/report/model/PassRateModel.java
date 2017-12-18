package com.eeduspace.report.model;

import lombok.Data;

/**
 * 合格率实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-03-21 9:21
 **/
@Data
public class PassRateModel {
    /**
     * 及格数量
     */
    private Integer jige;
    /**
     * 不及格数量
     */
    private Integer bujige;
    /**
     * 优秀数量
     */
    private Integer youxiu;
    /**
     * 班级名称
     */
    private String className;
}
