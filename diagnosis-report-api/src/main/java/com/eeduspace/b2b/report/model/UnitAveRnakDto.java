package com.eeduspace.b2b.report.model;

import com.eeduspace.b2b.report.model.report.UnitModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 单元排名实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-10-11 11:21
 **/
@Data
public class UnitAveRnakDto implements Serializable{
    private UnitModel unitModel;
    private OrgDto orgDto;
    /**
     * 单元得分
     */
    private Double score;

}
