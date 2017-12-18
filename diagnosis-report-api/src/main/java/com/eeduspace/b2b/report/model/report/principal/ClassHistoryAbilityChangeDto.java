package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 学科能力水平历史变化实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 9:21
 **/
@Data
public class ClassHistoryAbilityChangeDto implements Serializable{
    /**
     * 班级名称
     */
    private String className;
    /**
     * 班级code
     */
    private String classCode;
    /**
     * 能力历史变化信息
     */
    private List<HistoryAbilityChangeDto> historyAbilityChangeDto;
}
