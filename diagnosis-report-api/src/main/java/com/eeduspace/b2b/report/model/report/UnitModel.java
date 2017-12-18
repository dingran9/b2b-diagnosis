package com.eeduspace.b2b.report.model.report;

import java.io.Serializable;

/**
 * Created by liuhongfei on 2017/10/9.
 */
public class UnitModel implements Serializable{



    //单元CODE
    private String unitCode;

    //单元名称
    private String unitName;

    //排序
    private Integer sort;

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
