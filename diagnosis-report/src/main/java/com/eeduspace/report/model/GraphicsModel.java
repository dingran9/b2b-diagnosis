package com.eeduspace.report.model;

import lombok.Data;

import java.util.List;

/**
 * 图形实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-18 16:43
 **/
@Data
public class GraphicsModel {
    /**
     * X轴
     */
    List<String> xAxi;
    List<GraphicsDataModel> series;
}
