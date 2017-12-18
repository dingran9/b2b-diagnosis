package com.eedu.diagnosis.manager.model.response;

import java.io.Serializable;

public class BaseAreaVo implements Serializable{
    /**
     * 地区编码主键
     */
    private String id;
    /**
     * 地区名称
     */
    private String areaname;

    /**
     * 所属上级 组织id
     */
    private String parentid;


    private String shortname;

    /**
     * 所属级别  1 省 2市  3 县区  4 乡/镇  5 街道/村
     */
    private Boolean level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public Boolean getLevel() {
        return level;
    }

    public void setLevel(Boolean level) {
        this.level = level;
    }
}