package com.eedu.auth.service.impl;

import com.eedu.auth.beans.AuthUserAuthorityManagerBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import com.eedu.auth.dao.AuthUserAuthorityManagerDao;
import com.eedu.auth.service.AuthUserAuthorityManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhongfei on 2017/9/28.
 */
@Service("authUserAuthorityManagerServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserAuthorityManagerServiceImpl implements AuthUserAuthorityManagerService{


    @Autowired
    private AuthUserAuthorityManagerDao authUserAuthorityManagerDao;


    @Override
    public int addUserAuthorityManager(AuthUserAuthorityManagerBean record) throws Exception {
        return authUserAuthorityManagerDao.addUserAuthorityManager(record);
    }

    @Override
    public int delUserAuthorityManager(Integer userId) throws Exception {
        return authUserAuthorityManagerDao.delUserAuthorityManager(userId);
    }

    @Override
    public int updateUserAuthorityManager(AuthUserAuthorityManagerBean record) throws Exception {
        return authUserAuthorityManagerDao.updateUserAuthorityManager(record);
    }

    @Override
    public PageInfo<AuthUserAuthorityManagerBean> getUserAuthorityManagerPage(AuthUserAuthorityManagerBean conditionBean) throws Exception {
        if (conditionBean.getPageNum() != null && conditionBean.getPageSize() != null) {
            PageHelper.startPage(conditionBean.getPageNum(), conditionBean.getPageSize());
        }
        List<AuthUserAuthorityManagerBean>  result = authUserAuthorityManagerDao.getUserAuthorityManagerList(conditionBean);
        if(CollectionUtils.isEmpty(result)){result = new ArrayList<>();}
        PageInfo<AuthUserAuthorityManagerBean> page = new PageInfo<>(result);
        return page;
    }

    @Override
    public List<AuthUserAuthorityManagerBean> getUserAuthorityManagerList(AuthUserAuthorityManagerBean conditionBean) throws Exception {
        List<AuthUserAuthorityManagerBean>  result = authUserAuthorityManagerDao.getUserAuthorityManagerList(conditionBean);
        if(CollectionUtils.isEmpty(result)){return  new ArrayList<>();}
        return result;
    }


    @Override
    public List<AuthUserAuthorityManagerBean> getUserByAccountAndPwd(AuthUserAuthorityManagerBean record) throws Exception {
        List<AuthUserAuthorityManagerBean>  result = authUserAuthorityManagerDao.getUserByAccountAndPwd(record);
        if(CollectionUtils.isEmpty(result))return new ArrayList<>();
        return result;
    }

    @Override
    public AuthUserAuthorityManagerBean getUserByUserId(Integer userId) throws Exception {
        return authUserAuthorityManagerDao.getUserByUserId(userId);
    }
}
