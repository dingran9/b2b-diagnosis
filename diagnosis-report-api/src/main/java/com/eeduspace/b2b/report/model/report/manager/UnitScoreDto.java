package com.eeduspace.b2b.report.model.report.manager;

import lombok.Data;

import java.io.Serializable;

/**
 * 单元信息实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-29 11:01
 **/
@Data
public class UnitScoreDto implements Serializable{
    private Double avgScore;
    private String unitCode;
    private String unitName;
}
