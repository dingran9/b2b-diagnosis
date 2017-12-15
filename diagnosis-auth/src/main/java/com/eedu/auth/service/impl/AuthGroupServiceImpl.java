package com.eedu.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.dao.*;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.common.utils.MD5Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/16
 * Time: 20:41
 * Describe:
 */
@Service("groupServiceImpl")
@Slf4j
@com.alibaba.dubbo.config.annotation.Service
public class AuthGroupServiceImpl implements AuthGroupService {

    @Autowired
    private AuthGroupDao authGroupDao;
    @Autowired
    private AuthUserGroupBindDao userGroupBindDao;
    @Autowired
    private AuthUserDao userDao;
    @Autowired
    private AuthDataDictionaryDao dataDictionaryDao;
    @Autowired
    private AuthUserManagerDao userManagerDao;
    @Autowired
    private AuthUserManagerService authUserManagerService;

    /**
     * 添加组织信息
     *
     * @param groupBean
     * @return
     */
    @Override
    public int addGroupInfo(AuthGroupBean groupBean) {

        return authGroupDao.addGroupInfo(groupBean);
    }

    /**
     * 根据Id删除组织信息
     *
     * @param groupId
     * @return
     */
    @Override
    public int delGroupInfoById(String groupId) {
        return authGroupDao.delGroupInfoById(groupId);
    }

    /**
     * 修改组织信息
     *
     * @param groupBean
     * @return
     */
    @Override
    public int updateGroupInfoById(AuthGroupBean groupBean) {
        return authGroupDao.updateGroupInfoById(groupBean);
    }

    /**
     * 根据条件查询组织信息
     *
     * @param groupBean
     * @return
     */
    @Override
    public List<AuthGroupBean> getGroupByCondition(AuthGroupBean groupBean) {

        List<AuthGroupBean> groupBeanList = authGroupDao.getGroupByCondition(groupBean);
        List<AuthGroupBean> groupList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(groupBeanList)) {
            if (!CollectionUtils.isEmpty(groupBean.getGroupPeriod())) {
                for (String period : groupBean.getGroupPeriod()) {

                    AuthDataDictionaryBean bean = new AuthDataDictionaryBean();
                    bean.setDataIden(period);
                    bean.setDataType(ConstantEnum.DATA_DICTIONARY_PERIOD_TYPE.getType());
                    List<AuthDataDictionaryBean> list = dataDictionaryDao.getDataDictionaryByCondition(bean);
                    if (!CollectionUtils.isEmpty(list)) {
                        //根据学段查询学校
                        AuthGroupDictionaryBindBean groupDictionary = new AuthGroupDictionaryBindBean();
                        groupDictionary.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
                        groupDictionary.setDataId(list.get(0).getDataId());
                        groupDictionary.setDataType(ConstantEnum.DATA_DICTIONARY_PERIOD_TYPE.getType());
                        groupDictionary.setAuthGroupBeans(groupBeanList);
                        List<AuthGroupDictionaryBindBean> groupDictionaryList =
                                dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionary);
                        if (!CollectionUtils.isEmpty(groupDictionaryList)) {
                            for (AuthGroupDictionaryBindBean schoolBind : groupDictionaryList) {
                                AuthGroupBean schoolBean = authGroupDao.getGroupInfoById(schoolBind.getGroupId());
                                if (null != schoolBean) {
                                    groupList.add(schoolBean);
                                }
                            }

                        }
                    }
                }
                return groupList;
            }
        }
        return groupBeanList;
    }

    /**
     * 根据ID查询组织信息
     *
     * @param groupId
     * @return
     */
    @Override
    public AuthGroupBean getGroupInfoById(Integer groupId) {
        return authGroupDao.getGroupInfoById(groupId);
    }

    /**
     * 根据班级idlist，查询班级下面的学生
     *
     * @param idLists
     * @return
     */
    @Override
    public List<AuthUserBean> getStudentByClassLists(List<Integer> idLists) {
        if (CollectionUtils.isEmpty(idLists)) {
            return null;
        }
        Map<String, Object> maps = new HashMap<>();
        maps.put("userType", ConstantEnum.USER_TYPE_STUDENT.getType());//查询的用户类型为学生
        maps.put("groupType", ConstantEnum.GROUP_TYPE_CLASS.getType());//查询的组织类型为班级
        maps.put("groupIdList", idLists);
        List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getStudentByGroupIdLists(maps);
        if (CollectionUtils.isEmpty(userGroupBindBeanList)) {
            return null;
        }
        List<AuthUserBean> userBeanList = new ArrayList<>();
        for (AuthUserGroupBindBean userGroupBindBean : userGroupBindBeanList) {
            AuthUserBean userBean = userDao.getUserById(userGroupBindBean.getUserId());
            if (null != userBean) {
                userBeanList.add(userBean);
            }
        }

        return userBeanList;
    }

    /**
     * 查询学校下的机构
     *
     * @param groupBean
     * @return
     */
    @Override
    public List<AuthGroupBean> getGroupByParent(AuthGroupBean groupBean) {
        List<AuthGroupBean> groupBeanList = authGroupDao.getGroupByCondition(groupBean);
        if (CollectionUtils.isEmpty(groupBeanList)) {
            return null;
        }
        for (AuthGroupBean group : groupBeanList) {
            //是学校，查询学校下的年级
            if (group.getGroupType() == ConstantEnum.GROUP_TYPE_SCHOOL.getType()) {
                AuthGroupBean gradeBean = new AuthGroupBean();
                gradeBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                gradeBean.setGroupParentId(group.getGroupId());
                //查询年级
                List<AuthGroupBean> gradeBeanList = authGroupDao.getGroupByCondition(gradeBean);
                group.setChildGroupBeanList(gradeBeanList);
                //查询年级下的班级
                for (AuthGroupBean grade : gradeBeanList) {
                    AuthGroupBean classBean = new AuthGroupBean();
                    classBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                    classBean.setGroupParentId(group.getGroupId());
                    grade.setChildGroupBeanList(authGroupDao.getGroupByCondition(classBean));
                }
            }
            //是年级，查询年级下的班级
            if (group.getGroupType() == ConstantEnum.GROUP_TYPE_GRADE.getType()) {
                AuthGroupBean classBean = new AuthGroupBean();
                classBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                classBean.setGroupParentId(group.getGroupId());
                group.setChildGroupBeanList(authGroupDao.getGroupByCondition(classBean));
            }
        }
        return groupBeanList;
    }

    /**
     * 根据学校和年级查询年级绑定的学科
     *
     * @param schoolId
     * @return
     */
    @Override
    public AuthGroupBean getSubjectBySchoolGrade(Integer schoolId, Integer gradeIden) {
        AuthGroupBean condition = new AuthGroupBean();
        condition.setGroupIden(gradeIden);
        condition.setGroupParentId(schoolId);
        condition.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
        List<AuthGroupBean> groupBeanList = authGroupDao.getGroupByCondition(condition);
        if (!CollectionUtils.isEmpty(groupBeanList)) {
            return groupBeanList.get(0);
        }
        return null;
    }

    /**
     * 根据年级查询绑定的学科
     *
     * @return
     */
    @Override
    public List<AuthSubjectBean> getSubjectByGradeId(AuthGroupBean gradeCondition) {

        AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
        groupDictionaryBindBean.setGroupId(gradeCondition.getGroupId());
        groupDictionaryBindBean.setGroupType(gradeCondition.getGroupType());
        groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
        List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList =
                dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
        if (!CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {
            List<AuthSubjectBean> subjectBeenList = new ArrayList<>();
            for (AuthGroupDictionaryBindBean dataDictionaryBindBean : groupDictionaryBindBeanList) {
                AuthDataDictionaryBean dataDictionaryCondition = new AuthDataDictionaryBean();
                dataDictionaryCondition.setDataId(dataDictionaryBindBean.getDataId());
                AuthDataDictionaryBean dataDictionaryBean =
                        dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryCondition).get(0);

                AuthSubjectBean subjectBean = new AuthSubjectBean();
                subjectBean.setSubjectId(dataDictionaryBean.getDataId());
                subjectBean.setSubjectIden(Integer.parseInt(dataDictionaryBean.getDataIden()));
                subjectBean.setSubjectName(dataDictionaryBean.getDataName());

                subjectBeenList.add(subjectBean);
            }
            return subjectBeenList;
        }
        return null;
    }

    /**
     * 添加学校
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean addSchoolInfo(AuthGroupBean condition) {
        condition.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
        condition.setGroupProductId(ConstantEnum.PRODUCT_TYPE_ID.getType());
        try {
            int count = authGroupDao.addGroupInfo(condition);
            if (count > 0) {
                //添加的学校ID
                int schoolId = condition.getGroupId();
                //学校添加成功后生成学校管理员信息
                AuthUserManagerBean schoolManager = new AuthUserManagerBean();
                schoolManager.setUserType(ConstantEnum.USER_TYPE_SCHOOL_ADMIN.getType());
                schoolManager.setUserName("admin_" + schoolId);
                schoolManager.setUserAccount("admin_" + schoolId);
                schoolManager.setUserPassword(MD5Utils.getMD5("123456"));
                schoolManager.setUserProductId(1);
                schoolManager.setUserSchoolId(schoolId);
                int userManagerId = userManagerDao.addUserManager(schoolManager);
                if (userManagerId == 0) {
                    throw new RuntimeException("添加学校生成校长时异常，schoolId：" + schoolId);
                }
                //添加学校管理员和学校的绑定关系
                AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
//				userGroupBindBean.setUserId(userManagerId);
                userGroupBindBean.setUserId(schoolManager.getUserId());
                userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_SCHOOL_ADMIN.getType());
                userGroupBindBean.setGroupId(schoolId);
                userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
                if (userGroupBindDao.addUserGroupBind(userGroupBindBean) == 0) {
                    throw new RuntimeException("绑定校长和学校关系时异常，userManagerId：" + userManagerId + "，schoolId：" + schoolId);
                }
                //查询学段
                List<String> period = condition.getGroupPeriod();
                if (!CollectionUtils.isEmpty(period)) {
                    for (String periodIden : period) {
                        AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                        dataDictionaryBean.setDataIden(periodIden);
                        dataDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_PERIOD_TYPE.getType());
                        List<AuthDataDictionaryBean> dataDictionaryList = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean);
                        if (!CollectionUtils.isEmpty(dataDictionaryList)) {
                            //添加组织和基础数据的绑定关系
                            AuthDataDictionaryBean dataDictionary = dataDictionaryList.get(0);
                            //绑定学段
                            AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
                            groupDictionaryBindBean.setGroupId(schoolId);
                            groupDictionaryBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
                            groupDictionaryBindBean.setDataId(dataDictionary.getDataId());
                            groupDictionaryBindBean.setDataType(dataDictionary.getDataType());
                            dataDictionaryDao.addGroupDictionaryBind(groupDictionaryBindBean);

                        } else {
                            throw new RuntimeException("查询不到学段标识，periodIden：" + periodIden);
                        }
                    }
                    //添加绑定年级
                    List<AuthGroupBean> groupBeanList = condition.getChildGroupBeanList();
                    for (AuthGroupBean group : groupBeanList) {
                        //查询基础数据年级标识
                        AuthDataDictionaryBean gradeDictionaryBean = new AuthDataDictionaryBean();
                        gradeDictionaryBean.setDataIden(String.valueOf(group.getGroupIden()));
                        gradeDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_GRADE_TYPE.getType());
                        List<AuthDataDictionaryBean> gradeDictionaryList = dataDictionaryDao.getDataDictionaryByCondition(gradeDictionaryBean);
                        if (!CollectionUtils.isEmpty(gradeDictionaryList)) {
                            AuthDataDictionaryBean gradeBean = gradeDictionaryList.get(0);
                            group.setGroupName(gradeBean.getDataName());
                            group.setGroupIden(Integer.valueOf(gradeBean.getDataIden()));
                            group.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                            group.setGroupParentId(schoolId);
                            int gradeId = authGroupDao.addGroupInfo(group);
                            if (gradeId > 0) {
                                //根据学科标识查询学科
                                JSONArray subjectArray = JSON.parseArray(group.getGroupMaterial());
                                Iterator<Object> iter = subjectArray.iterator();
                                while (iter.hasNext()) {
                                    JSONObject sub = (JSONObject) iter.next();
                                    String subId = sub.getString("subjectIden");   //学科Id
                                    String subName = sub.getString("subjectName");   //学科名称
                                    String materialVersion = sub.getString("materialVersion");   //教材版本名称
                                    String versionCode = sub.getString("versionCode");   //教材版本code

                                    AuthDataDictionaryBean subDataDictionaryBean = new AuthDataDictionaryBean();
                                    subDataDictionaryBean.setDataIden(subId);
                                    subDataDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                                    List<AuthDataDictionaryBean> subDataDictionaryList = dataDictionaryDao.getDataDictionaryByCondition(subDataDictionaryBean);
                                    if (!CollectionUtils.isEmpty(subDataDictionaryList)) {
                                        //绑定年级和学科
                                        AuthDataDictionaryBean subDataDictionary = subDataDictionaryList.get(0);

                                        AuthGroupDictionaryBindBean gradeDictionaryBind = new AuthGroupDictionaryBindBean();
                                        gradeDictionaryBind.setGroupId(group.getGroupId());
                                        gradeDictionaryBind.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                                        gradeDictionaryBind.setDataId(subDataDictionary.getDataId());
                                        gradeDictionaryBind.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                                        dataDictionaryDao.addGroupDictionaryBind(gradeDictionaryBind);
                                    } else {
                                        throw new RuntimeException("查询不到学科标识，subId：" + subId);
                                    }
                                }
                            }
                        } else {
                            throw new RuntimeException("查询不到年级标识，groupIden：" + group.getGroupIden());
                        }
                    }
                } else {
                    throw new RuntimeException("无学段属性");
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("GroupDataServiceImpl.addGroupInfo", e);
            throw new RuntimeException();
        }
        return false;
    }

    /**
     * 产品后台管理  学校管理列表
     *
     * @param groupBean
     * @return
     */
    @Override
    public PageInfo<AuthGroupBean> schoolManagerList(AuthGroupBean groupBean) {

        if (groupBean.getPageNum() != null && groupBean.getPageSize() != null) {
            //开启分页
            PageHelper.startPage(groupBean.getPageNum(), groupBean.getPageSize());
        }

        List<AuthGroupBean> groupBeanList = authGroupDao.getGroupByCondition(groupBean);
        if (!CollectionUtils.isEmpty(groupBeanList)) {
            for (AuthGroupBean schoolBean : groupBeanList) {
                //设置学校校长信息
                AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
                userGroupBindBean.setGroupId(schoolBean.getGroupId());
                userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
//				userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType());
                userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_SCHOOL_ADMIN.getType());
                List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
                if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
                    AuthUserManagerBean schoolMaster = userManagerDao.getUserByUserId(userGroupBindBeanList.get(0).getUserId());
                    schoolBean.setSchoolmaster(schoolMaster);
                }
                //设置学段
                AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
                groupDictionaryBindBean.setGroupId(schoolBean.getGroupId());
                groupDictionaryBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
                groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_PERIOD_TYPE.getType());
                List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList =
                        dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
                if (!CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {
                    for (AuthGroupDictionaryBindBean groupPeriod : groupDictionaryBindBeanList) {
                        AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                        dataDictionaryBean.setDataId(groupPeriod.getDataId());
                        schoolBean.getGroupPeriod().add(dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean).get(0).getDataName());
                    }
                }
            }
            PageInfo<AuthGroupBean> page = new PageInfo<>(groupBeanList);
            return page;
        }
        return null;
    }

    /**
     * 产品后台管理  学校管理  管理教材版本
     *
     * @param maps
     * @return
     */
    @Override
    public List<AuthSubjectBean> managerMaterial(Map<String, Object> maps) {
        try {
            //查询学校下年级
            AuthGroupBean schoolCondition = new AuthGroupBean();
            schoolCondition.setGroupParentId(Integer.parseInt(maps.get("schoolId").toString()));
            schoolCondition.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
            if (maps.containsKey("gradeIden")) {
                schoolCondition.setGroupIden(Integer.parseInt(maps.get("gradeIden").toString()));
            }
            List<AuthGroupBean> gradeBeanList = authGroupDao.getGroupByCondition(schoolCondition);
            if (!CollectionUtils.isEmpty(gradeBeanList)) {
                List<AuthSubjectBean> subjectBeanList = new ArrayList<>();
                for (AuthGroupBean gradeBean : gradeBeanList) {
                    //获取年级学科的绑定关系
                    AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
                    groupDictionaryBindBean.setGroupId(gradeBean.getGroupId());
                    groupDictionaryBindBean.setGroupType(gradeBean.getGroupType());
                    groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                    List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList = dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
                    if (!CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {
                        for (AuthGroupDictionaryBindBean bindBean : groupDictionaryBindBeanList) {
                            //查询基础数据中绑定的数据
                            AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                            dataDictionaryBean.setDataId(bindBean.getDataId());
                            AuthDataDictionaryBean subjectBean = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean).get(0);

                            if (StringUtils.isNotBlank(gradeBean.getGroupMaterial())) {
                                JSONArray subjectBeen = JSONObject.parseArray(gradeBean.getGroupMaterial());
                                Iterator<Object> iter = subjectBeen.iterator();
                                while (iter.hasNext()) {
                                    JSONObject object = (JSONObject) iter.next();
                                    if (subjectBean.getDataIden().equals(object.getString("subjectIden"))) {
                                        AuthSubjectBean subject = new AuthSubjectBean();
                                        subject.setSubjectId(subjectBean.getDataId());
                                        subject.setSubjectIden(object.getInteger("subjectIden"));
                                        subject.setSubjectName(object.getString("subjectName"));
                                        subject.setGradeId(gradeBean.getGroupId());
                                        subject.setGradeName(gradeBean.getGroupName());
                                        subject.setGradeIden(gradeBean.getGroupIden());
                                        subject.setMaterialVersion(object.getString("materialVersion"));
                                        subject.setVersionCode(object.getString("versionCode"));
                                        if (maps.containsKey("subjectIden") && subjectBean.getDataIden().equals(maps.get("subjectIden").toString())) {
                                            subjectBeanList.add(subject);
                                            return subjectBeanList;
                                        } else {
                                            subjectBeanList.add(subject);
                                        }
                                    }

                                }

//							}else{
//								throw new RuntimeException("年级学科的绑定关系和组织表中年级学科教材版本绑定字段不一致");
                            }
                        }
                    }

                }
                return subjectBeanList;

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthGroupServiceImpl.managerMaterial--", e);
        }

        return null;
    }

    /**
     * 产品后台管理  学校管理  修改教材版本
     *
     * @param maps
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateManagerMaterial(Map<String, Object> maps) {
        try {
            Integer schoolId = Integer.parseInt(maps.get("schoolId").toString());
            Integer gradeIden = Integer.parseInt(maps.get("gradeIden").toString());
            Integer subjectId = Integer.parseInt(maps.get("subjectId").toString());
            Integer subjectIden = Integer.parseInt(maps.get("subjectIden").toString());
            String updateToSubjectIden = maps.get("updateToSubjectIden").toString();
            String updateToMaterialVersion = maps.get("updateToMaterialVersion").toString();

            //年级是否存在
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(schoolId);
            groupBean.setGroupIden(gradeIden);
            List<AuthGroupBean> gradeBeanList = authGroupDao.getGroupByCondition(groupBean);
            if (!CollectionUtils.isEmpty(gradeBeanList)) {
                AuthGroupBean gradeBean = authGroupDao.getGroupByCondition(groupBean).get(0);
                //查询年级学科的绑定关系
                AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
                groupDictionaryBindBean.setGroupId(gradeBean.getGroupId());
                groupDictionaryBindBean.setGroupType(gradeBean.getGroupType());
                groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                groupDictionaryBindBean.setDataId(subjectId);
                List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList =
                        dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
                if (!CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {
                    if (StringUtils.isBlank(gradeBean.getGroupMaterial())) {
                        throw new RuntimeException("年级学科的绑定关系错误,gradeId:" + gradeBean.getGroupId() + ",subjectId:" + subjectId);
                    }
                    //根据修改的学科标识查询基础数据ID
                    AuthDataDictionaryBean dataDictionaryBind = new AuthDataDictionaryBean();
                    dataDictionaryBind.setDataIden(updateToSubjectIden);
                    dataDictionaryBind.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                    List<AuthDataDictionaryBean> dataDictionaryBeanList =
                            dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBind);
                    if (!CollectionUtils.isEmpty(dataDictionaryBeanList)) {
                        //更新年级绑定的学科
                        AuthGroupDictionaryBindBean updateGradeDictionaryBind = new AuthGroupDictionaryBindBean();
                        updateGradeDictionaryBind.setId(groupDictionaryBindBeanList.get(0).getId());
                        updateGradeDictionaryBind.setDataId(dataDictionaryBeanList.get(0).getDataId());
                        //修改绑定关系后修改年级中教材版本数据groupMaterial
                        if (dataDictionaryDao.updateGroupDictionaryBind(updateGradeDictionaryBind) > 0) {
                            List<AuthSubjectBean> updateToSubject = new ArrayList<>();
                            List<AuthSubjectBean> subjectBeanList = JSONObject.parseArray(gradeBean.getGroupMaterial(), AuthSubjectBean.class);
                            for (AuthSubjectBean subjectBean : subjectBeanList) {
                                if (subjectIden.equals(subjectBean.getSubjectIden())) {
                                    AuthSubjectBean updateToSubjectBean = JSONObject.parseObject(updateToMaterialVersion, AuthSubjectBean.class);
                                    updateToSubject.add(updateToSubjectBean);
                                } else {
                                    updateToSubject.add(subjectBean);
                                }
                            }
                            if (!CollectionUtils.isEmpty(updateToSubject)) {
                                String MaterialVersionJson = JSON.toJSONString(updateToSubject);
                                AuthGroupBean updateToMaterial = new AuthGroupBean();
                                updateToMaterial.setGroupId(gradeBean.getGroupId());
                                updateToMaterial.setGroupMaterial(MaterialVersionJson);
                                return authGroupDao.updateGroupInfoById(updateToMaterial) > 0;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("不存在年级和学科的绑定关系,gradeIden:" + gradeIden + ",subjectId:" + subjectId);
                }

            } else {
                throw new RuntimeException("年级不存在--schoolId:" + schoolId + ",gradeIden:" + gradeIden);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthGroupServiceImpl.updateManageMaterial--", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return false;
    }

    /**
     * 产品后台管理  学校管理  添加教材版本
     *
     * @param maps
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean addManagerMaterial(Map<String, Object> maps) {

        try {
            Integer schoolId = Integer.parseInt(maps.get("schoolId").toString());
            Integer gradeIden = Integer.parseInt(maps.get("gradeIden").toString());
            Integer subjectId = Integer.parseInt(maps.get("subjectId").toString());
            Integer subjectIden = Integer.parseInt(maps.get("subjectIden").toString());
            String addToMaterialVersion = maps.get("addToMaterialVersion").toString();
            Integer gradeId = null;
            //年级是否存在
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(schoolId);
            groupBean.setGroupIden(gradeIden);
            groupBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
            List<AuthGroupBean> gradeBeanList = authGroupDao.getGroupByCondition(groupBean);
            if (CollectionUtils.isEmpty(gradeBeanList)) {
                AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                dataDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_GRADE_TYPE.getType());
                dataDictionaryBean.setDataIden(gradeIden.toString());
                List<AuthDataDictionaryBean> gradeDataBean =
                        dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean);
                if (CollectionUtils.isEmpty(gradeDataBean)) {
                    throw new RuntimeException("查询不到年级的基础数据标识,gradeIden:" + gradeIden);
                }
                groupBean.setGroupName(gradeDataBean.get(0).getDataName());

                gradeId = authGroupDao.addGroupInfo(groupBean);
            } else {
                gradeId = gradeBeanList.get(0).getGroupId();
            }


            AuthGroupBean gradeBean = authGroupDao.getGroupByCondition(groupBean).get(0);
            //查询年级学科的绑定关系
            AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
            groupDictionaryBindBean.setGroupId(gradeId);
            groupDictionaryBindBean.setGroupType(gradeBean.getGroupType());
            groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
            groupDictionaryBindBean.setDataId(subjectId);
            List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList =
                    dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
            if (CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {

                //根据添加的学科标识查询基础数据ID
                AuthDataDictionaryBean dataDictionaryBind = new AuthDataDictionaryBean();
                dataDictionaryBind.setDataIden(subjectIden.toString());
                dataDictionaryBind.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                List<AuthDataDictionaryBean> dataDictionaryBeanList =
                        dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBind);
                if (!CollectionUtils.isEmpty(dataDictionaryBeanList)) {
                    //添加年级绑定的学科
                    AuthGroupDictionaryBindBean addGradeDictionaryBind = new AuthGroupDictionaryBindBean();
                    addGradeDictionaryBind.setGroupId(gradeBean.getGroupId());
                    addGradeDictionaryBind.setGroupType(gradeBean.getGroupType());
                    addGradeDictionaryBind.setDataId(dataDictionaryBeanList.get(0).getDataId());
                    addGradeDictionaryBind.setDataType(dataDictionaryBeanList.get(0).getDataType());
                    //修改绑定关系后修改年级中教材版本数据groupMaterial
                    if (dataDictionaryDao.addGroupDictionaryBind(addGradeDictionaryBind) > 0) {
                        List<AuthSubjectBean> updateToSubject = new ArrayList<>();
                        List<AuthSubjectBean> subjectBeanList = JSONObject.parseArray(gradeBean.getGroupMaterial(), AuthSubjectBean.class);
                        if (!CollectionUtils.isEmpty(subjectBeanList)) {
                            for (AuthSubjectBean subjectBean : subjectBeanList) {
                                if (subjectIden.equals(subjectBean.getSubjectIden())) {
                                    throw new RuntimeException("年级和学科的绑定关系已存在,gradeId:" + gradeId + ",subjectIden:" + subjectIden);
                                } else {
                                    updateToSubject.add(subjectBean);
                                }
                            }
                        }
                        AuthSubjectBean addSubject = JSONObject.parseObject(addToMaterialVersion, AuthSubjectBean.class);
                        updateToSubject.add(addSubject);
                        if (!CollectionUtils.isEmpty(updateToSubject)) {
                            String MaterialVersionJson = JSON.toJSONString(updateToSubject);
                            AuthGroupBean updateToMaterial = new AuthGroupBean();
                            updateToMaterial.setGroupId(gradeBean.getGroupId());
                            updateToMaterial.setGroupMaterial(MaterialVersionJson);
                            return authGroupDao.updateGroupInfoById(updateToMaterial) > 0;
                        }
                    }
                }
            } else {
                throw new RuntimeException("年级和学科的绑定关系已存在,gradeId:" + gradeId + ",subjectId:" + subjectId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthGroupServiceImpl.addManagerMaterial--", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return false;
    }

    /**
     * 产品后台管理  学校管理  删除教材版本
     *
     * @param maps
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean deleteManagerMaterial(Map<String, Object> maps) {

        try {
            Integer schoolId = Integer.parseInt(maps.get("schoolId").toString());
            Integer gradeIden = Integer.parseInt(maps.get("gradeIden").toString());
            Integer subjectId = Integer.parseInt(maps.get("subjectId").toString());
            Integer subjectIden = Integer.parseInt(maps.get("subjectIden").toString());

            //年级是否存在
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(schoolId);
            groupBean.setGroupIden(gradeIden);
            List<AuthGroupBean> gradeBeanList = authGroupDao.getGroupByCondition(groupBean);
            if (!CollectionUtils.isEmpty(gradeBeanList)) {
                AuthGroupBean gradeBean = authGroupDao.getGroupByCondition(groupBean).get(0);
                //查询删除的年级学科的绑定关系
                AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
                groupDictionaryBindBean.setGroupId(gradeBean.getGroupId());
                groupDictionaryBindBean.setGroupType(gradeBean.getGroupType());
                groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                groupDictionaryBindBean.setDataId(subjectId);
                List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList =
                        dataDictionaryDao.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
                if (CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {
                    throw new RuntimeException("年级和学科的绑定关系不存在,gradeId:" + gradeBean.getGroupId() + ",subjectId:" + subjectId);
                } else {
                    //删除绑定关系后修改年级中教材版本数据groupMaterial
                    if (dataDictionaryDao.deleteGroupDictionaryBind(groupDictionaryBindBeanList.get(0).getId()) > 0) {
                        List<AuthSubjectBean> updateToSubject = new ArrayList<>();
                        List<AuthSubjectBean> subjectBeanList = JSONObject.parseArray(gradeBean.getGroupMaterial(), AuthSubjectBean.class);
                        for (AuthSubjectBean subjectBean : subjectBeanList) {
                            if (!subjectIden.equals(subjectBean.getSubjectIden())) {
                                updateToSubject.add(subjectBean);
                            }
                        }
//                        if (!CollectionUtils.isEmpty(updateToSubject)) {
                            String MaterialVersionJson = JSON.toJSONString(updateToSubject);
                            AuthGroupBean updateToMaterial = new AuthGroupBean();
                            updateToMaterial.setGroupId(gradeBean.getGroupId());
                            updateToMaterial.setGroupMaterial(MaterialVersionJson);
                            return authGroupDao.updateGroupInfoById(updateToMaterial) > 0;
//                        }
                    }
                }

            } else {
                throw new RuntimeException("年级不存在--schoolId:" + schoolId + ",gradeIden:" + gradeIden);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthGroupServiceImpl.addManagerMaterial--", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return false;
    }

    /**
     * 根据学校和学校下的年级查询班级
     *
     * @param schoolId  学校Id
     * @param gradeIden 年级标识
     * @return
     */
    @Override
    public List<AuthGroupBean> getClassBySchoolGrade(Integer schoolId, Integer gradeIden) {
        //查询学校
        AuthGroupBean schoolBean = authGroupDao.getGroupInfoById(schoolId);
        if (null != schoolBean) {
            if (ConstantEnum.GROUP_TYPE_SCHOOL.getType().equals(schoolBean.getGroupType())) {
                //查询年级
                AuthGroupBean groupBean = new AuthGroupBean();
                groupBean.setGroupIden(gradeIden);
                groupBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                groupBean.setGroupParentId(schoolBean.getGroupId());
                List<AuthGroupBean> gradeList = authGroupDao.getGroupByCondition(groupBean);
                if (!CollectionUtils.isEmpty(gradeList)) {
                    //根据年级查年级下的班级
                    AuthGroupBean gradeBean = new AuthGroupBean();
                    gradeBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                    gradeBean.setGroupParentId(gradeList.get(0).getGroupId());
                    List<AuthGroupBean> classList = authGroupDao.getGroupByCondition(gradeBean);
                    return classList;
                }
            }
        }


        return null;
    }

    /**
     * 添加班级信息
     *
     * @param map
     * @return
     */
    @Override
    public boolean addClassInfo(Map<String, Object> map) {

        try {
            if (null != map && map.containsKey("schoolId") && map.containsKey("gradeIden")
                    && map.containsKey("className")) {

                Integer schoolId = Integer.parseInt(map.get("schoolId").toString());
                AuthGroupBean schoolBean = authGroupDao.getGroupInfoById(schoolId);
                if (null != schoolBean) {
                    AuthGroupBean gradeCondition = new AuthGroupBean();
                    gradeCondition.setGroupParentId(schoolBean.getGroupId());
                    gradeCondition.setGroupIden(Integer.parseInt(map.get("gradeIden").toString()));
                    List<AuthGroupBean> gradeBeanList = authGroupDao.getGroupByCondition(gradeCondition);
                    if (!CollectionUtils.isEmpty(gradeBeanList)) {
                        AuthGroupBean gradeBean = gradeBeanList.get(0);
                        AuthGroupBean classBean = new AuthGroupBean();
                        classBean.setGroupName(map.get("className").toString());
                        classBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                        classBean.setGroupParentId(gradeBean.getGroupId());
                        if (map.containsKey("groupArt"))
                            classBean.setGroupArt(Integer.parseInt(map.get("groupArt").toString()));
                        return authGroupDao.addGroupInfo(classBean) > 0;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthGroupServiceImpl.addClassInfo,error...", e);
        }

        return false;
    }

    /**
     * 校长后台管理  用户管理----班级管理列表
     *
     * @param maps
     * @return
     */
    @Override
    public PageInfo<AuthGradeBean> classManagerList(Map<String, Object> maps) {
        try {
            if (null != maps && maps.containsKey("schoolId")) {
                AuthGroupBean schoolBean = authGroupDao.getGroupInfoById(Integer.parseInt(maps.get("schoolId").toString()));
                if (null != schoolBean) {
                    List<AuthGradeBean> gradeBeanList = new ArrayList<>();
                    //查询学校下的年级
                    AuthGroupBean gradeCondition = new AuthGroupBean();
                    gradeCondition.setGroupParentId(schoolBean.getGroupId());
                    gradeCondition.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                    if (maps.containsKey("gradeIden")) {
                        gradeCondition.setGroupIden(Integer.parseInt(maps.get("gradeIden").toString()));
                    }
                    if (maps.containsKey("pageNum") && maps.containsKey("pageSize")) {
                        // 开启分页
                        PageHelper.startPage(Integer.parseInt(maps.get("pageNum").toString()),
                                Integer.parseInt(maps.get("pageSize").toString()));
                    }
                    List<AuthGroupBean> gradeList = authGroupDao.getGroupByCondition(gradeCondition);
                    for (AuthGroupBean gradeBean : gradeList) {
                        AuthGradeBean returnGradeBean = new AuthGradeBean();
                        returnGradeBean.setGradeId(gradeBean.getGroupId());
                        returnGradeBean.setGradeName(gradeBean.getGroupName());
                        returnGradeBean.setGradeIden(gradeBean.getGroupIden());
                        returnGradeBean.setSchoolId(schoolBean.getGroupId());
                        returnGradeBean.setSchoolName(schoolBean.getGroupName());
                        //查询年级下的班级
                        AuthGroupBean classCondition = new AuthGroupBean();
                        classCondition.setGroupParentId(gradeBean.getGroupId());
                        classCondition.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                        List<AuthGroupBean> classList = authGroupDao.getGroupByCondition(classCondition);
                        if (!CollectionUtils.isEmpty(classList)) {
                            for (AuthGroupBean classBean : classList) {
                                AuthClassBean returnClassBean = new AuthClassBean();
                                returnClassBean.setClassId(classBean.getGroupId());
                                returnClassBean.setClassName(classBean.getGroupName());
                                if (null != classBean.getGroupArt()) {
                                    returnClassBean.setGroupArt(classBean.getGroupArt());
                                }
                                //查询班级学生人数
                                AuthUserGroupBindBean groupBind = new AuthUserGroupBindBean();
                                groupBind.setGroupId(classBean.getGroupId());
                                groupBind.setGroupType(classBean.getGroupType());
                                List<AuthUserGroupBindBean> groupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(groupBind);
                                int studentCount = 0;
                                int teacherCount = 0;
                                if (!CollectionUtils.isEmpty(groupBindBeanList)) {
                                    for (AuthUserGroupBindBean bindBean : groupBindBeanList) {
                                        if (ConstantEnum.USER_TYPE_STUDENT.getType().equals(bindBean.getUserType())) {
//											AuthUserBean userBean = userDao.getUserById(bindBean.getUserId());
//											if(null != userBean){
                                            studentCount++;
//											}
                                        } else if (ConstantEnum.USER_TYPE_TEACHER.getType().equals(bindBean.getUserType())) {
//											AuthUserManagerBean userManagerBean = userManagerDao.getUserByUserId(bindBean.getUserId());
//											if(null != userManagerBean){
                                            teacherCount++;
//											}
                                        }

                                    }
                                }
                                returnClassBean.setStudentNumber(studentCount);
                                returnClassBean.setTeacherNumber(teacherCount);
                                returnGradeBean.getClassBeanList().add(returnClassBean);

                            }
                        }
                        gradeBeanList.add(returnGradeBean);
                    }
                    PageInfo<AuthGradeBean> page = new PageInfo<>(gradeBeanList);
                    return page;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthGroupServiceImpl.classManagerList,error...", e);
        }

        return null;
    }


    /**
     * 根据学校ID，年级ID，学科ID获取教材版本
     *
     * @param schoolId  学校Id
     * @param gradeId   年级标识
     * @param subjectId 学科标识
     * @return
     */
    @Override
    public AuthSubjectBean getmaterialVersion(Integer schoolId, Integer gradeId, Integer subjectId) {
        //查询学校
        AuthGroupBean bean = new AuthGroupBean();
        bean.setGroupIden(gradeId);
        bean.setGroupParentId(schoolId);
        List<AuthGroupBean> gradeBeans = authGroupDao.getGroupByCondition(bean);
        if (!CollectionUtils.isEmpty(gradeBeans)) {
            AuthGroupBean gradeBean = gradeBeans.get(0);
            if (null != gradeBean.getGroupMaterial() && !"".equals(gradeBean.getGroupMaterial())) {
                List<AuthSubjectBean> subjectBeanList = JSONObject.parseArray(gradeBean.getGroupMaterial(), AuthSubjectBean.class);
                if (!CollectionUtils.isEmpty(subjectBeanList)) {
                    for (AuthSubjectBean subjectBean : subjectBeanList) {
                        if (subjectBean.getSubjectIden().equals(subjectId)) {
                            return subjectBean;
                        }
                    }
                }
            }
        }
        return null;
    }


    /**
     * 验证学校，年级，所有学科下有没有未绑定的老师的班级
     *
     * @param schoolId  学校Id
     * @param gradeId   年级标识
     * @param groupIden 学科标识
     * @param subjectIdns 学科标识
     * @return
     */
    public boolean subjectNoClass(Integer schoolId, Integer gradeId, Integer groupIden, Integer[] subjectIdns) {

        AuthGroupBean groupBean = new AuthGroupBean();
        groupBean.setGroupParentId(gradeId);
        groupBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
        List<AuthGroupBean> groupBeanList = getGroupByCondition(groupBean);

        if (null != groupBeanList && groupBeanList.size() > 0) {
//				Integer groupIden = groupBeanList.get(0).getGroupIden();
            for (Integer subjectIden : subjectIdns) {
                AuthUserManagerConditionBean condition = new AuthUserManagerConditionBean();
                condition.setUserSchoolId(schoolId);
                condition.setUserSubjectIden(String.valueOf(subjectIden));
                condition.setUserGradeIden(String.valueOf(groupIden));
                condition.setPageSize(Integer.MAX_VALUE);
                condition.setPageNum(1);
                PageInfo<AuthUserManagerConditionBean> page = authUserManagerService.getUserManagerListByCondition(condition);
                if (null != page) {
                    List<AuthUserManagerConditionBean> list = page.getList();
                    if (null != list && list.size() > 0) {
                        Set<Integer> set = new HashSet<>();
                        for (AuthUserManagerConditionBean bean : list) {
                            List<AuthClassBean> classbeans = bean.getClassBeanList();
                            if (null != classbeans && classbeans.size() > 0) {
                                for (AuthClassBean classBean : classbeans) {
                                    set.add(classBean.getClassId());
                                }
                            }
                        }
                        for (AuthGroupBean authGroupBean : groupBeanList) {
                            if (set.add(authGroupBean.getGroupId())) return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * 根据班级名称查询班级 用于验重
     * @param groupBean
     * @return
     */
    @Override
    public List<AuthGroupBean> getClassInfoByName(AuthGroupBean groupBean)throws Exception{

        return authGroupDao.getClassByName(groupBean);
    }

    /**
     * 根据id组查询组织信息
     * @param ids
     * @return
     */
    @Override
    public List<AuthGroupBean> getGroupByIds(List<Integer> ids)throws Exception{
        Map<String,List<Integer>> map = new HashMap<>();
        map.put("ids",ids);
        return authGroupDao.getGroupByIds(map);
    }
}
