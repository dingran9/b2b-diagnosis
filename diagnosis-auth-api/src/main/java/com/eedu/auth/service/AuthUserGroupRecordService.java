package com.eedu.auth.service;

import com.eedu.auth.beans.AuthUserGroupRecordBean;

/**
 * Created by zyb on 2017/5/27.
 */
public interface AuthUserGroupRecordService {


    /**
     * 添加学生组织记录信息
     * @param authUserGroupRecordBean
     * @return
     * @throws Exception
     */
    public int addUserGroupRecordByUserGroup(AuthUserGroupRecordBean authUserGroupRecordBean) throws Exception;
}
