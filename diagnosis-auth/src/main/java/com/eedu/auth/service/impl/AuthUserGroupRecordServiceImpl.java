package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthUserGroupRecordBean;
import com.eedu.auth.dao.AuthUserGroupRecordDao;
import com.eedu.auth.service.AuthUserGroupRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zyb on 2017/5/27.
 */
@Service("userGroupRecordServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserGroupRecordServiceImpl implements AuthUserGroupRecordService {

    @Autowired
    private AuthUserGroupRecordDao authUserGroupRecordDao;
    /**
     * 添加学生组织记录信息
     * @param authUserGroupRecordBean
     * @return
     * @throws Exception
     */
    @Override
    public int addUserGroupRecordByUserGroup(AuthUserGroupRecordBean authUserGroupRecordBean) throws Exception{

       return  authUserGroupRecordDao.addUserGroupRecordByUserGroup(authUserGroupRecordBean);

    }

}
