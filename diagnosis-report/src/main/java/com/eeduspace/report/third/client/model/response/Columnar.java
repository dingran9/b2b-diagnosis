/**
  * Copyright 2017 bejson.com 
  */
package com.eeduspace.report.third.client.model.response;

import lombok.Data;

/**
 * <p>描述 知识点模块掌握情况图</p>
 * @Author zhuchaowei
 * e-mail:zhuchaowei@e-eduspace.com
 * Date: 2017/3/16 13:08
 * @param    
 * @return   
**/
@Data
public class Columnar {

    private String id;
    private String papersId;
    private String bookType;
    private String parentCtbCode;
    private String parentCtbCodeName;
    /**
     * 知识点模块code
     */
    private String childrenCtbCode;
    /**
     * 知识点模块名称
     */
    private String childrenCtbCodeName;
    private boolean isdel;
    private String createName;
    private long createDate;
    private String updateName;
    private long updateDate;
    private int status;
    /**
     * 知识点模块得分
     */
    private int score;


}