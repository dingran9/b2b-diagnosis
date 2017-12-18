package com.eeduspace.b2b.report.service;

import com.eeduspace.b2b.report.model.systemlog.OperationLogDto;

/**
 * Created by zhuchaowei on 2017/5/10.
 */
public interface SystemManagerOperationLogOpenService {
    void svaeOperationLog(OperationLogDto operationLogDto) throws Exception;
}
