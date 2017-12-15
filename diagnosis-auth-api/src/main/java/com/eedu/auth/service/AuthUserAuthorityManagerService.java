package com.eedu.auth.service;

import com.eedu.auth.beans.AuthUserAuthorityManagerBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by liuhongfei on 2017/9/28.
 */
public interface AuthUserAuthorityManagerService {


    /**
     * 添加教育管理者
     * @param record
     * @return
     */
    public int addUserAuthorityManager(AuthUserAuthorityManagerBean record) throws Exception;


    /**
     * 删除管理者
     * @param userId
     * @return
     */
    public int delUserAuthorityManager(Integer userId) throws Exception;


    /**
     * 修改管理者信息
     * @param record
     * @return
     */
    public int updateUserAuthorityManager(AuthUserAuthorityManagerBean record) throws Exception;


    /**
     *  查询管理者分页列表
     * @return
     */
    public PageInfo<AuthUserAuthorityManagerBean> getUserAuthorityManagerPage(AuthUserAuthorityManagerBean record) throws Exception;

    /**
     *  查询管理者列表
     * @return
     */
    public List<AuthUserAuthorityManagerBean> getUserAuthorityManagerList(AuthUserAuthorityManagerBean conditionBean) throws Exception ;

    /**
     * 账号密码查询管理者
     * @param record
     * @return
     */
    public List<AuthUserAuthorityManagerBean> getUserByAccountAndPwd(AuthUserAuthorityManagerBean record) throws Exception;


    /**
     * ID查询管理者
     * @param userId
     * @return
     */
    public  AuthUserAuthorityManagerBean getUserByUserId(Integer userId) throws Exception;

}
