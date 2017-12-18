/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.report.third.client.model.response;

import lombok.Data;

/**
 * <p>描述 思维能力图</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 13:03
 * @param    
 * @return   
**/
@Data
public class Typecolumnar {
    /**
     * 思维能力名称
     */
    private String typeName;
    /**
     * 思维能力得分
     */
    private double score;
    /**
     * 思维能力 code
     */
    private String typeCode;


}