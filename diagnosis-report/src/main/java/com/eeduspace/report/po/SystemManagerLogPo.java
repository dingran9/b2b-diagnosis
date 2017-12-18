package com.eeduspace.report.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统操作日志
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-10 10:19
 **/
@Entity
@Table(name = "edu_system_manager_log", schema = "b2b_report", catalog = "")
@Data
public class SystemManagerLogPo {
    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;
    /**
     * 日志类型 0操作日志，1异常日志
     */
    @Column(name = "log_type")
    private Integer logType;
    /**
     * 操作人
     */
    @Column(name = "user")
    private String user;
    /**
     * 方法名称
     */
    @Column(name = "method_name")
    private String methodName;
    /**
     * 请求参数
     */
    @Column(name = "request_params",columnDefinition="text")
    private String requestParams;
    /**
     * 方法描述
     */
    @Column(name = "method_description")
    private String methodDescription;
    /**
     * 访问者ip
     */
    @Column(name = "request_ip")
    private String requestIp;
    /**
     * 请求uri
     */
    @Column(name = "request_uri")
    private String requestUri;
    /**
     * 请求path
     */
    @Column(name = "request_path")
    private String requestPath;
    /**
     * 异常编码
     */
    @Column(name = "exception_code")
    private String exceptionCode;
    /**
     * 异常详情
     */
    @Column(name = "exception_detail",columnDefinition="text")
    private String exceptionDetail;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 请求响应状态
     */
    @Column(name = "status")
    private String status;
    /**
     * 请求响应内容
     */
    @Column(name = "content")
    private String content;
    /**
     * 操作日志类型 0 控制层日志  1service层日志
     */
    @Column(name = "operation_type")
    private Integer operationType;


}
