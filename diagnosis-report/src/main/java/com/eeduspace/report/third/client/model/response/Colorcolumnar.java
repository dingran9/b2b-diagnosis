/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.report.third.client.model.response;

import lombok.Data;

/**
 * <p>描述 三色图实体</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 13:03
 * @param    
 * @return   
**/
@Data
public class Colorcolumnar {
    /**
     * 蓝色  已掌握部分
     */
    private double blue;
    /**
     * 黄色 较容易提升部分
     */
    private double yellow;
    /**
     * 灰色 较难提升部分
     */
    private double gray;
    /**
     * 模块code
     */
    private String ctb_code;
    /**
     * 模块名称
     */
    private String ctb_Name;


}