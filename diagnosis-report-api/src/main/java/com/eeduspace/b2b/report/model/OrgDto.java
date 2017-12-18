package com.eeduspace.b2b.report.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 组织实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-11 11:17
 **/
@Data
public class OrgDto implements Serializable{
    /**
     * 组织名称
     */
    private String name;
    /**
     * 组织code
     */
    private String code;
}
