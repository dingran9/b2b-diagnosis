/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.report.third.client.model.response;

import lombok.Data;

/**
 * <p>描述 雷达图</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 13:06
 * @param    
 * @return   
**/
@Data
public class Radar {

    private String id;
    private String papersId;
    private String bookType;
    private String parentCtbCode;
    private String parentCtbCodeName;
    /**
     * 能力code
     */
    private String capacityCode;
    /**
     * 能力名称
     */
    private String capacityName;
    private boolean isdel;
    private String createName;
    private long createDate;
    private String updateName;
    private long updateDate;
    private int status;
    /**
     * 能力得分
     */
    private int score;


}