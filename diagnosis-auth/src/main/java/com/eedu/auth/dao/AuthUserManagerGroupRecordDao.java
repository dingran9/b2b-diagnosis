package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserManagerGroupRecordBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuhongfei on 2017/5/27.
 */
@Repository
public interface AuthUserManagerGroupRecordDao {


    /**
     * 查询教师组织记录信息
     * @param authUserGroupRecordBean
     * @return
     */
    List<AuthUserManagerGroupRecordBean> getUserManagerGroupRecordByCondition(AuthUserManagerGroupRecordBean authUserGroupRecordBean)throws Exception;


    /**
     * 添加教师组织记录信息
     * @param list
     * @return
     */
    int addUserManagerGroupRecord(List<AuthUserManagerGroupRecordBean> list)throws Exception;

    /**
     * 根据Id删除教师组织记录信息
     * @param id
     * @return
     */
    int delUserManagerGroupRecord(int id)throws Exception;

    /**
     * 根据ID查询教师组织记录信息
     * @param id
     * @return
     */
    AuthUserManagerGroupRecordBean getUserManagerGroupRecordInfoById(int id)throws Exception;

    /**
     * 根据教师ID，类型查询教师组织记录信息
     * @param authUserBean
     * @return
     */
    List<AuthUserManagerGroupRecordBean> getUserManagerGroupRecordInfoById(AuthUserBean authUserBean)throws Exception;

    /**
     * 添加教师组织记录信息
     * @param authUserGroupRecordBean
     * @return
     * @throws Exception
     */
    int addUserManagerGroupRecordByUserGroup(AuthUserManagerGroupRecordBean authUserGroupRecordBean)throws Exception;

    /**
     * 批量添加教师组织记录信息
     * @param authUserManagerGroupRecordBeans
     * @return
     * @throws Exception
     */
    int batchSaveUserMangerGroupRecord(List<AuthUserManagerGroupRecordBean> authUserManagerGroupRecordBeans);
}
