package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 能力实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 15:05
 **/
@Data
public class AbilityDto implements Serializable{
    /**
     * 能力名称
     */
    private String name;
    /**
     * 能力code
     */
    private String code;
    /**
     * 能力得分
     */
    private Double score;
}
