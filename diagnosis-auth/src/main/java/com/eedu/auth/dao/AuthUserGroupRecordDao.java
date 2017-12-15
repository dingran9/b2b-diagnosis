package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserGroupRecordBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by liuhongfei on 2017/5/27.
 */
@Repository
public interface AuthUserGroupRecordDao {


    /**
     * 查询学生组织记录信息
     * @param authUserGroupRecordBean
     * @return
     */
    List<AuthUserGroupRecordBean> getUserGroupRecordByCondition(AuthUserGroupRecordBean authUserGroupRecordBean)throws Exception;


    /**
     * 添加学生组织记录信息
     * @param list
     * @return
     */
    int addUserGroupRecord(List<AuthUserGroupRecordBean> list)throws Exception;

    /**
     * 根据Id删除学生组织记录信息
     * @param id
     * @return
     */
    int delUserGroupRecord(int id)throws Exception;

    /**
     * 根据ID查询学生组织记录信息
     * @param id
     * @return
     */
    AuthUserGroupRecordBean getGroupRecordInfoById(int id)throws Exception;

    /**
     * 根据学生ID，类型查询学生组织记录信息
     * @param authUserBean
     * @return
     */
    List<AuthUserGroupRecordBean> getGroupRecordInfoById(AuthUserBean authUserBean)throws Exception;

    /**
     * 添加学生组织记录信息
     * @param authUserGroupRecordBean
     * @return
     * @throws Exception
     */
    int addUserGroupRecordByUserGroup(AuthUserGroupRecordBean authUserGroupRecordBean)throws Exception;

    /**
     * 添加学生组织记录信息
     * @param maps
     * @return
     * @throws Exception
     */
    int addUserGroupRecordByUserGroupBatch(Map<String,Object> maps);

    /**
     * 批量添加学生组织记录信息
     * @param authUserGroupRecordBeans
     * @return
     * @throws Exception
     */
    int batchSaveUserGroupRecord(List<AuthUserGroupRecordBean> authUserGroupRecordBeans);
}
