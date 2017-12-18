/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.report.third.client.model.response;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * <p>描述 报告内容</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 13:00
 * @param    
 * @return   
**/
@Data
public class ReportModel {
    /**
     *思维能力图
     */
    private List<Typecolumnar> Typecolumnar;
    /**
     * 雷达图
     */
    private List<Radar> Radar;
    /**
     * 三色图
     */
    private List<Colorcolumnar> Colorcolumnar = new ArrayList<>();
    /**
     * 模块知识点掌握情况图
     */
    private List<Columnar> Columnar;


}