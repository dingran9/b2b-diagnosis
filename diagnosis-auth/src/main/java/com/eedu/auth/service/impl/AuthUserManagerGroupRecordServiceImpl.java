package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthUserManagerGroupRecordBean;
import com.eedu.auth.dao.AuthUserManagerGroupRecordDao;
import com.eedu.auth.service.AuthUserManagerGroupRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liuhongfei on 2017/5/27.
 */
@Service("userManagerGroupRecordServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserManagerGroupRecordServiceImpl implements AuthUserManagerGroupRecordService{


    @Autowired
    private AuthUserManagerGroupRecordDao authUserManagerGroupRecordDao;

    /**
     * 添加教师组织记录信息
     * @param authUserGroupRecordBean
     * @return
     * @throws Exception
     */
    @Override
    public int addUserManagerGroupRecordByUserGroup(AuthUserManagerGroupRecordBean authUserGroupRecordBean) throws Exception{

        return  authUserManagerGroupRecordDao.addUserManagerGroupRecordByUserGroup(authUserGroupRecordBean);

    }

}
