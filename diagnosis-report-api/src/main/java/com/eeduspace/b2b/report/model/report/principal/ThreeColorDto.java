package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;

/**
 * 分数提升空间
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-26 11:21
 **/
@Data
public class ThreeColorDto implements Serializable{
    /**
     * 蓝色部分
     */
    private Double buleScore;
    /**
     * 灰色部分
     */
    private Double grayScore;
    /**
     * 橙色部分
     */
    private Double orangeScore;
    /**
     * 模块名称
     */
    private String moduleName;



}
