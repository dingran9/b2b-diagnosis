package com.eedu.auth.service;

import com.eedu.auth.beans.AuthUserManagerGroupRecordBean;

/**
 * Created by zyb on 2017/5/27.
 */
public interface AuthUserManagerGroupRecordService {

    /**
     * 添加教师组织记录信息
     * @param authUserGroupRecordBean
     * @return
     * @throws Exception
     */
    public int addUserManagerGroupRecordByUserGroup(AuthUserManagerGroupRecordBean authUserGroupRecordBean) throws Exception;
}
