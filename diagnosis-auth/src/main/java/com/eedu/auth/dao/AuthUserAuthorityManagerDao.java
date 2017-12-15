package com.eedu.auth.dao;

import com.eedu.auth.beans.AuthUserAuthorityManagerBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liuhongfei on 2017/9/28.
 */

@Repository
public interface AuthUserAuthorityManagerDao {

    /**
     * 添加教育管理者
     * @param record
     * @return
     */
    public int addUserAuthorityManager(AuthUserAuthorityManagerBean record);


    /**
     * 删除管理者
     * @param userId
     * @return
     */
    public int delUserAuthorityManager(Integer userId);


    /**
     * 修改管理者信息
     * @param record
     * @return
     */
    public int updateUserAuthorityManager(AuthUserAuthorityManagerBean record);


    /**
     *  查询管理者列表
     * @param record
     * @return
     */
    public List<AuthUserAuthorityManagerBean> getUserAuthorityManagerList(AuthUserAuthorityManagerBean record);


    /**
     * 账号密码查询管理者
     * @param record
     * @return
     */
    public List<AuthUserAuthorityManagerBean> getUserByAccountAndPwd(AuthUserAuthorityManagerBean record);


    /**
     * ID查询管理者
     * @param userId
     * @return
     */
    public  AuthUserAuthorityManagerBean getUserByUserId(Integer userId);
}
