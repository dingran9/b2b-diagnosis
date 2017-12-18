package com.eedu.diagnosis.manager.service.taskService;

/**
 * 每次诊断结束规定时间后 通知报告服务生成该次诊断的各种总报告
 * Created by dqy on 2017/3/20.
 */
public interface MakeReportService {
    boolean makeReprot();
}
