package com.eeduspace.report.model;

import lombok.Data;

import java.util.Collection;

/**
 * 图形所需数据
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-18 16:44
 **/
@Data
public class GraphicsDataModel {
    private String name;
    private Collection<Double> data;

}
