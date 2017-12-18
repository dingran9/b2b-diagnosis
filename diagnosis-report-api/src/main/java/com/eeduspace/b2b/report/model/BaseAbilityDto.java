package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 能力信息
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-10 9:40
 **/
@Data
public class BaseAbilityDto implements Serializable{
    /**
     * 能力名称
     */
    private String abilityName;
    /**
     * 能力code
     */
    private String abilityCode;
    /**
     * 能力得分
     */
    private Double score;
    /**
     * 所统计维度名称
     */
    private String ownerName;
    /**
     * 所统计维度code
     */
    private String ownerCode;
}
