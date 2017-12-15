package com.eedu.auth.beans.enums;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/5/8.
 */
@Data
public class EnumBean implements Serializable {

    private Integer resource_id;

    private String icon;

    private String name;

    private String url;

    private Integer hasChild;

    private Integer type;

    private List<EnumBean> children = new ArrayList<>();

    private Integer parent_id;

}
