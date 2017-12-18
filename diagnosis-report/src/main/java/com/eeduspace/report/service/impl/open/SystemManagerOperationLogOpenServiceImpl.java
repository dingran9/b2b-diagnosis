package com.eeduspace.report.service.impl.open;

import com.eeduspace.b2b.report.model.systemlog.OperationLogDto;
import com.eeduspace.b2b.report.service.SystemManagerOperationLogOpenService;
import com.eeduspace.report.dao.SystemManagerLogDao;
import com.eeduspace.report.po.SystemManagerLogPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统操作日志实现类
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-05-10 10:29
 **/
@Slf4j
@Service("systemManagerOperationLogOpenService")
@com.alibaba.dubbo.config.annotation.Service
public class SystemManagerOperationLogOpenServiceImpl implements SystemManagerOperationLogOpenService{
    @Autowired
    private SystemManagerLogDao systemManagerLogDao;

    @Override
    public void svaeOperationLog(OperationLogDto operationLogDto) throws Exception {
        SystemManagerLogPo systemManagerLogPo=new SystemManagerLogPo();
        BeanUtils.copyProperties(operationLogDto,systemManagerLogPo);
        systemManagerLogDao.save(systemManagerLogPo);
    }
}
