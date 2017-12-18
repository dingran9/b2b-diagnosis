package com.eeduspace.b2b.report.model.report.principal;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-02 9:22
 **/
@Data
public class HistoryAbilityChangeDto implements Serializable{
    /**
     * 考试发布记录code
     */
    private Integer releaseExamCode;
    /**
     *  考试发布记录时间
     */
    private Timestamp releaseExamCreateTime;
    /**
     * 能力信息
     */
    private List<AbilityDto> abilityDtos;
}
