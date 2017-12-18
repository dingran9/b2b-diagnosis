package com.eeduspace.b2b.report.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 发布考试记录实体
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-04-24 17:06
 **/
@Data
public class ReleaseExamDto implements Serializable{
    /**
     * 学期   格式  如 2017上学期   2017-1   2017下学期    2017-2
     */
    @NotBlank(message = "semester is  null")
    private String semester;
    /**
     * 考试类型
     * UNIT(0,"单元测试"),
     MID_EXAM(1,"期中考试"),
     FINAL_EXAM(2,"期末考试"),
     SIMULATION_TEST(3,"模拟考试"),
     WILL_EXAM(4,"会考");
     */
    @NotBlank(message = "examType is  null")
    private String examType;
    /**
     * 发布考试记录code
     */
    @NotBlank(message = "releaseCode is  null")
    private String releaseCode;
    /**
     * 发布考试记录名称
     */
    @NotBlank(message = "releaseName is  null")
    private String releaseName;
    /**
     * 所有试卷总分之和
     */
    @NotNull(message = "totalScore is null")
    private Double totalScore;

}
