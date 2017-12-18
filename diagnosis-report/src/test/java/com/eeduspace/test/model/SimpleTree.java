package com.eeduspace.test.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单树节点 对象
 *
 * @author Administrator
 */
public class SimpleTree {

    private String id;
    private String name;
    private Integer isRight = 1; //0错1对
    private List<SimpleTreeVO> sons = new ArrayList<SimpleTreeVO>(0);

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SimpleTreeVO> getSons() {
        return sons;
    }

    public void setSons(List<SimpleTreeVO> sons) {
        this.sons = sons;
    }

}
