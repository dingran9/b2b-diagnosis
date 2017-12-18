package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分数变动实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-27 10:22
 **/
@Data
public class ArtTypeDto implements Serializable{
    /**
     * 文理类型 0无 1文  2理
     */
    private Integer artType;
    /**
     * 班级成绩变动集合
     */
    private List<ClassSubjectScoreChangeDto> classSubjectScoreChangeDtos;
}
