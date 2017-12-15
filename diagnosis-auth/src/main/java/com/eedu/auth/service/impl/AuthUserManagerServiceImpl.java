package com.eedu.auth.service.impl;

import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.beans.enums.EnumBean;
import com.eedu.auth.dao.*;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.common.enumration.ArtTypeEnum;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.common.utils.ProcessUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 11:51
 * Describe:
 */
@Service("userManagerServiceImpl")
@Slf4j
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserManagerServiceImpl implements AuthUserManagerService {
    @Autowired
    private AuthUserManagerDao userManagerDao;
    @Autowired
    private AuthUserGroupBindDao userGroupBindDao;
    @Autowired
    private AuthGroupDao groupDao;
    @Autowired
    private AuthUserDao userDao;
    @Autowired
    private AuthDataDictionaryDao dataDictionaryDao;
    @Autowired
    private AuthUserManagerGroupRecordDao userManagerGroupRecordDao;
    @Autowired
    private AuthRoleDao authRoleDao;

    /**
     * 添加管理者信息
     *
     * @param userManagerBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUserManager(AuthUserManagerBean userManagerBean) {
        return userManagerDao.addUserManager(userManagerBean);
    }

    /**
     * 批量添加教师
     *
     * @param list
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<AuthUserManagerBean> batchSaveUserManager(List<AuthUserManagerBean> list) {
        userManagerDao.batchSaveUserManager(list);
        return list;
    }

    /**
     * 删除管理者信息
     *
     * @param userId
     * @return
     */
    @Override
    public int delUserManager(Integer userId) {
        return userManagerDao.delUserManager(userId);
    }

    /**
     * 修改管理者信息
     *
     * @param managerBean
     * @return
     */
    @Override
    public int updateUserManager(AuthUserManagerBean managerBean) {
        return userManagerDao.updateUserManager(managerBean);
    }

    /**
     * 根据条件查询管理者列表
     *
     * @param managerBean
     * @return
     */
    @Override
    public List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean managerBean) {
        return userManagerDao.getUserManagerList(managerBean);
    }

    /**
     * 根据帐号和密码查找用户
     *
     * @param managerBean
     * @return
     */
    @Override
    public AuthUserManagerBean getUserByAccountAndPwd(AuthUserManagerBean managerBean) {

        AuthUserManagerBean userManagerBean = null;


        List<AuthUserManagerBean> userManagerBeans = userManagerDao.getUserByAccountAndPwd(managerBean);

        if (null != userManagerBeans && userManagerBeans.size() > 0) {

            userManagerBean = userManagerBeans.get(0);
            //查询到用户后需要将用户的绑定关系带上
            if (null != userManagerBeans && userManagerBeans.size() > 0) {
                if (userManagerBean.getUserType() == ConstantEnum.USER_TYPE_SCHOOL_ADMIN.getType()) {
                    userManagerBean = userManagerBeans.get(0);
                    //查询到用户后需要将用户的绑定关系带上
                    if (null != userManagerBean && userManagerBean.getUserId() > 0) {
                        AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
                        userGroupBindBean.setUserId(userManagerBean.getUserId());
                        userGroupBindBean.setUserType(userManagerBean.getUserType());
                        userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
                        List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
                        if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
                            AuthGroupBean groupBean = new AuthGroupBean();
                            groupBean.setGroupId(userGroupBindBeanList.get(0).getGroupId());
                            List<AuthGroupBean> beans = groupDao.getGroupByCondition(groupBean);
                            if (!CollectionUtils.isEmpty(beans)) {
                                userManagerBean.setSchoolName(beans.get(0).getGroupName());
                            }

//                    if (!userGroupBindBeanList.get(0).getGroupId().equals(userManagerBean.getUserSchoolId())) {
//                        AuthUserManagerBean user = new AuthUserManagerBean();
//                        user.setUserId(userManagerBean.getUserId());
//                        user.setUserSchoolId(userGroupBindBeanList.get(0).getGroupId());
//                        userManagerDao.updateUserManager(user);
//                        userManagerBean.setUserSchoolId(userGroupBindBeanList.get(0).getGroupId());
//                    }
                        }
                    }
                }
            }
        }
        return userManagerBean;
    }

    /**
     * 根据手机号查询用户
     *
     * @param userPhone
     * @return
     */
    @Override
    public AuthUserManagerBean getUserByUserPhone(String userPhone) {
        return userManagerDao.getUserByUserPhone(userPhone);
    }

    @Override
    public AuthUserManagerBean getUserByAccount(String account) {
        return userManagerDao.getUserByAccount(account);
    }

    /**
     * 根据老师信息查询老师下面的班级信息
     *
     * @param teacherId
     * @return
     */
    @Override
    public List<AuthGroupBean> getClassByTeacherId(Integer teacherId) {
        AuthUserManagerBean managerBean = new AuthUserManagerBean();
        managerBean.setUserId(teacherId);
        List<AuthUserManagerBean> userManagerBeenList = userManagerDao.getUserManagerList(managerBean);
        if (!CollectionUtils.isEmpty(userManagerBeenList)) {
            AuthUserManagerBean userManagerBean = userManagerBeenList.get(0);
            //组装信息查询老师绑定的班级信息
            AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
            userGroupBindBean.setUserId(userManagerBean.getUserId());
            userGroupBindBean.setUserType(userManagerBean.getUserType());
            userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
            List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
            List<AuthGroupBean> groupBeanList = new ArrayList<>();
            for (AuthUserGroupBindBean groupBindBean : userGroupBindBeanList) {
                AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
                if (groupBean != null) {
                    AuthUserGroupBindBean groupBind = new AuthUserGroupBindBean();
                    groupBind.setGroupId(groupBean.getGroupId());
                    groupBind.setGroupType(groupBean.getGroupType());
                    groupBind.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
                    List<AuthUserGroupBindBean> groupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(groupBind);
                    int count = 0;
                    if (!CollectionUtils.isEmpty(groupBindBeanList)) {
                        for (AuthUserGroupBindBean bindBean : groupBindBeanList) {
                            AuthUserBean userBean = userDao.getUserById(bindBean.getUserId());
                            if (null != userBean) {
                                count++;
                            }
                        }
                    }
                    groupBean.setPeopleNumber(count);
                    groupBeanList.add(groupBean);
                }
            }
            return groupBeanList;
        }
        return null;
    }

    /**
     * 根据班级获取学生信息
     *
     * @param classId
     * @return
     */
    @Override
    public List<AuthUserBean> getMyStudentByClassId(Integer classId) {
        //根据classId查询组织详情
        AuthGroupBean groupBean = groupDao.getGroupInfoById(classId);
        if (null != groupBean) {
            //组装数据查询班级学生的绑定信息
            AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
            userGroupBindBean.setGroupId(groupBean.getGroupId());
            userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
            List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);

            List<AuthUserBean> userBeanList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
                for (AuthUserGroupBindBean groupBindBean : userGroupBindBeanList) {
                    AuthUserBean userBean = userDao.getUserById(groupBindBean.getUserId());
                    if (null != userBean) {
                        userBeanList.add(userBean);
                    }
                }
                return userBeanList;
            }
        }
        return null;
    }

    /**
     * 根据userId集合查询
     *
     * @param maps key=userIdList
     * @return
     */
    @Override
    public List<AuthUserManagerBean> getUserByListId(Map<String, Object> maps) {
        return userManagerDao.getUserByListId(maps);
    }

    /**
     * 根据用户Id查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public AuthUserManagerBean getUserByUserId(Integer userId) {
        return userManagerDao.getUserByUserId(userId);
    }

    /**
     * 根据班级和学科查询老师
     *
     * @param classId   班级id
     * @param subjectId 学科id
     * @return TODO 需要优化，先查询班级下的老师
     */
    @Override
    public AuthUserManagerBean getUserByGradeIdBySubjectId(Integer classId, Integer subjectId) {
        AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
        userGroupBindBean.setGroupId(classId);
        userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
        userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
        List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
        if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
            for (AuthUserGroupBindBean groupBindBean : userGroupBindBeanList) {
                AuthUserManagerBean userManagerBean = userManagerDao.getUserByUserId(groupBindBean.getUserId());

                AuthUserDictionaryBindBean userDictionaryBindBean = new AuthUserDictionaryBindBean();
                userDictionaryBindBean.setUserId(userManagerBean.getUserId());
                userDictionaryBindBean.setUserType(userManagerBean.getUserType());
                userDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                List<AuthUserDictionaryBindBean> userDictionaryBindBeanList = dataDictionaryDao.getUserDictionaryBindByCondition(userDictionaryBindBean);
                for (AuthUserDictionaryBindBean userDictionary : userDictionaryBindBeanList) {
                    AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                    dataDictionaryBean.setDataId(userDictionary.getDataId());
                    dataDictionaryBean.setDataIden(subjectId.toString());
                    dataDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                    List<AuthDataDictionaryBean> dataDictionaryBeanList = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean);
                    if (!CollectionUtils.isEmpty(dataDictionaryBeanList)) {
                        return userManagerBean;
                    }
                }

            }
        }
        return null;
    }

    /**
     * 查询帐号和手机号是否存在
     *
     * @param userManagerBean
     * @return
     */
    @Override
    public AuthUserManagerBean getUserIsExist(AuthUserManagerBean userManagerBean) {
        return userManagerDao.getUserIsExist(userManagerBean);
    }

    @Override
    public AuthUserManagerBean getTeacherInfo(AuthUserManagerBean condition) {
        AuthUserManagerBean userManagerBean = userManagerDao.getUserByUserId(condition.getUserId());
        if (null != userManagerBean) {
            //查询老师和组织的绑定信息
            AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
            userGroupBindBean.setUserId(userManagerBean.getUserId());
            userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
            List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
            for (AuthUserGroupBindBean groupBindBean : userGroupBindBeanList) {
                //查询学校
                if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_SCHOOL.getType()) {
                    AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
                    userManagerBean.setUserSchoolId(groupBean.getGroupId());
                    userManagerBean.setSchoolName(groupBean.getGroupName());
                    userManagerBean.setGroupAreaDistrictId(groupBean.getGroupAreaDistrictId());
                    userManagerBean.setGroupAreaDistrictName(groupBean.getGroupAreaDistrictName());
                }
                //查询年级
                if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_GRADE.getType()) {
                    AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
                    userManagerBean.setUserGradeId(groupBean.getGroupId());
                    userManagerBean.setUserGradeName(groupBean.getGroupName());
                    userManagerBean.setUserGradeIden(groupBean.getGroupIden());
                }
                //查询班级
                if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_CLASS.getType()) {
                    AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
                    AuthClassBean classBean = new AuthClassBean();
                    classBean.setClassId(groupBean.getGroupId());
                    classBean.setClassName(groupBean.getGroupName());
                    classBean.setGroupArt(groupBean.getGroupArt());
                    userManagerBean.getClassBeanList().add(classBean);
                }
            }
            //查询老师绑定的学科
            AuthUserDictionaryBindBean userDictionaryBindBean = new AuthUserDictionaryBindBean();
            userDictionaryBindBean.setUserId(userManagerBean.getUserId());
            userDictionaryBindBean.setUserType(userManagerBean.getUserType());
            userDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
            List<AuthUserDictionaryBindBean> userDictionaryBindBeanList =
                    dataDictionaryDao.getUserDictionaryBindByCondition(userDictionaryBindBean);
            if (!CollectionUtils.isEmpty(userDictionaryBindBeanList)) {
                String[] subjectCodes = new String[userDictionaryBindBeanList.size()];
                for (int i = 0; i <= userDictionaryBindBeanList.size() - 1; i++) {
                    AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                    dataDictionaryBean.setDataId(userDictionaryBindBeanList.get(i).getDataId());
                    AuthDataDictionaryBean dictionaryBean = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean).get(0);
                    subjectCodes[i] = dictionaryBean.getDataIden();
                }
                userManagerBean.setUserSubjects(subjectCodes);
            }

            return userManagerBean;
        }
        return null;
    }

    /**
     * 添加老师
     *
     * @param userManagerBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTeacherManager(AuthUserManagerBean userManagerBean) {
//        userManagerBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
        userManagerBean.setUserProductId(ConstantEnum.PRODUCT_TYPE_ID.getType());
        boolean bool = false;
        try {
            int addUserCount = userManagerDao.addUserManager(userManagerBean);

            if (addUserCount > 0) {
                userManagerBean.setUserAccount(ProcessUtils.parseLong(userManagerBean.getUserId()));
                List<AuthUserManagerBean> list = new ArrayList<>();
                list.add(userManagerBean);
                batchUpdateManagerAccount(list);
            }
            if (addUserCount > 0) {
                int userId = userManagerBean.getUserId();
                //添加用户和学校的绑定关系
                AuthGroupBean groupBean = groupDao.getGroupInfoById(userManagerBean.getUserSchoolId());
                if (null != groupBean) {
                    AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
                    userGroupBindBean.setUserId(userId);
                    userGroupBindBean.setUserType(userManagerBean.getUserType());
                    userGroupBindBean.setGroupId(groupBean.getGroupId());
                    userGroupBindBean.setGroupType(groupBean.getGroupType());
                    int count = userGroupBindDao.addUserGroupBind(userGroupBindBean);

                    if (count > 0) bool = true;

                    if (!userManagerBean.getUserType().equals(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType())) {
                        if (count > 0) {
                            //添加用户和年级的绑定关系
                            AuthGroupBean gradeBean = new AuthGroupBean();
                            gradeBean.setGroupParentId(groupBean.getGroupId());
                            gradeBean.setGroupIden(userManagerBean.getUserGradeIden());
                            gradeBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                            gradeBean.setGroupId(userManagerBean.getUserGradeId());
                            List<AuthGroupBean> gradeGroupBeanList = groupDao.getGroupByCondition(gradeBean);
                            if (!CollectionUtils.isEmpty(gradeGroupBeanList)) {
                                AuthGroupBean gradeGroupBean = gradeGroupBeanList.get(0);
                                if (null != gradeGroupBean) {
                                    AuthUserGroupBindBean userGradeBindBean = new AuthUserGroupBindBean();
                                    userGradeBindBean.setUserId(userId);
                                    userGradeBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                                    userGradeBindBean.setGroupId(gradeGroupBean.getGroupId());
                                    userGradeBindBean.setGroupType(gradeGroupBean.getGroupType());
                                    int gradeCount = userGroupBindDao.addUserGroupBind(userGradeBindBean);
                                    if (gradeCount > 0) {
                                        //添加用户和班级的绑定关系
                                        List<AuthClassBean> classBeanList = userManagerBean.getClassBeanList();
                                        for (AuthClassBean classBean : classBeanList) {
                                            AuthGroupBean classCondition = new AuthGroupBean();
                                            classCondition.setGroupId(classBean.getClassId());
                                            classCondition.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                                            classCondition.setGroupParentId(userGradeBindBean.getGroupId());
                                            List<AuthGroupBean> classGroupBeanList = groupDao.getGroupByCondition(classCondition);
                                            if (!CollectionUtils.isEmpty(classGroupBeanList)) {
                                                AuthGroupBean classGroupBean = classGroupBeanList.get(0);
                                                if (null != classGroupBean) {
                                                    AuthUserGroupBindBean userClassBindBean = new AuthUserGroupBindBean();
                                                    userClassBindBean.setUserId(userId);
                                                    userClassBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                                                    userClassBindBean.setGroupId(classGroupBean.getGroupId());
                                                    userClassBindBean.setGroupType(classGroupBean.getGroupType());
                                                    userGroupBindDao.addUserGroupBind(userClassBindBean);
                                                } else {
                                                    throw new RuntimeException("查询不到班级数据，classId" + classBean.getClassId());
                                                }
                                            } else {
                                                throw new RuntimeException("该年级下无此班级数据，classId" + classBean.getClassId());
                                            }
                                        }
                                    }
                                } else {
                                    throw new RuntimeException("查询不到年级数据，GradeId" + userManagerBean.getUserGradeId());
                                }
                            } else {
                                throw new RuntimeException("该学校下无此年级数据，GradeId" + userManagerBean.getUserGradeId());
                            }
                        }
                    }
                } else {
                    throw new RuntimeException("查询不到学校数据，SchoolId" + userManagerBean.getUserSchoolId());
                }
                //TODO 添加老师和学科基础数据的绑定关系
                if (!userManagerBean.getUserType().equals(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType())) {
                    AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                    dataDictionaryBean.setDataIden(String.valueOf(userManagerBean.getUserSubject()));
                    dataDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                    List<AuthDataDictionaryBean> dataDictionaryList = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean);
                    if (!CollectionUtils.isEmpty(dataDictionaryList)) {
                        AuthUserDictionaryBindBean userDictionaryBindBean = new AuthUserDictionaryBindBean();
                        userDictionaryBindBean.setUserId(userId);
                        userDictionaryBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                        userDictionaryBindBean.setDataId(dataDictionaryList.get(0).getDataId());
                        userDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                        dataDictionaryDao.addUserDictionaryBindByCondition(userDictionaryBindBean);
                        bool = true;
                    } else {
                        throw new RuntimeException("无此学科，SubjectId" + userManagerBean.getUserSubject());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthTeachServiceImpl.addTeacherManager-", e);
            throw new RuntimeException();
        }
        return bool;
    }


    /**
     * 修改老师绑定的年级，学科，班级信息
     *
     * @param userManagerBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeacherManagerGroup(AuthUserManagerBean userManagerBean) {
//        userManagerBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
        userManagerBean.setUserProductId(ConstantEnum.PRODUCT_TYPE_ID.getType());
        boolean bool = false;
        try {

            AuthUserManagerBean userbean = new AuthUserManagerBean();

            userbean.setUserId(userManagerBean.getUserId());
            userbean.setUserSchoolId(userManagerBean.getUserSchoolId());
            userbean.setUserSubject(userManagerBean.getUserSubject());
            userbean.setUserGradeId(userManagerBean.getUserGradeId());
            //更新用户表中的年级，学校，学科 信息
            int updateUsercount = userManagerDao.updateUserManager(userbean);

            if (updateUsercount > 0) {

                AuthUserGroupBindBean querybind = new AuthUserGroupBindBean();
                querybind.setUserId(userManagerBean.getUserId());
                querybind.setUserType(userManagerBean.getUserType());
                List<AuthUserGroupBindBean> result = userGroupBindDao.getUserGroupBindForRecord(querybind);
                if (CollectionUtils.isEmpty(result)) {
                    throw new RuntimeException("组织记录信息添加失败，userId:" + userManagerBean.getUserId() + "，userType:" + userManagerBean.getUserType());
                }
                List<AuthUserManagerGroupRecordBean> list = new ArrayList<>();
                list = PageHelperUtil.converterList(result, AuthUserManagerGroupRecordBean.class);
                for (AuthUserManagerGroupRecordBean bean : list) {
                    bean.setUserOperating(ConstantEnum.GROUP_OPERATING_UPDATE.getType());
                }
                userManagerGroupRecordDao.batchSaveUserMangerGroupRecord(list);

//                AuthUserManagerGroupRecordBean bean = new AuthUserManagerGroupRecordBean();
//                bean.setUserId(userManagerBean.getUserId());
//                bean.setUserType(userManagerBean.getUserType());
//                bean.setUserOperating(ConstantEnum.GROUP_OPERATING_UPDATE.getType());
//                int result = userManagerGroupRecordDao.addUserManagerGroupRecordByUserGroup(bean);
//                if(result <= 0){
//                    throw new RuntimeException("组织记录信息添加失败，userId:" + userManagerBean.getUserId()+"，userType:"+userManagerBean.getUserType());
//                }
                //删除用户绑定的年级，学校，班级信息
                int deleteUserCount = userGroupBindDao.delUserGroupBindByUserID(userManagerBean);

                if (deleteUserCount >= 0) {
                    //删除用户绑定的学科信息
                    int deleteDictCount = dataDictionaryDao.deleteUserDictionaryBindByUserId(userManagerBean);

                    if (deleteDictCount >= 0) {

                        int userId = userManagerBean.getUserId();
                        //添加用户和学校的绑定关系
                        AuthGroupBean groupBean = groupDao.getGroupInfoById(userManagerBean.getUserSchoolId());
                        if (null != groupBean) {
                            AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
                            userGroupBindBean.setUserId(userId);
                            userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                            userGroupBindBean.setGroupId(groupBean.getGroupId());
                            userGroupBindBean.setGroupType(groupBean.getGroupType());
                            int count = userGroupBindDao.addUserGroupBind(userGroupBindBean);

                            if (count > 0) bool = true;

                            if (!userManagerBean.getUserType().equals(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType())) {
                                if (count > 0) {
                                    //添加用户和年级的绑定关系
                                    AuthGroupBean gradeBean = new AuthGroupBean();
                                    gradeBean.setGroupParentId(groupBean.getGroupId());
                                    gradeBean.setGroupIden(userManagerBean.getUserGradeIden());
                                    gradeBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
                                    gradeBean.setGroupId(userManagerBean.getUserGradeId());
                                    List<AuthGroupBean> gradeGroupBeanList = groupDao.getGroupByCondition(gradeBean);
                                    if (!CollectionUtils.isEmpty(gradeGroupBeanList)) {
                                        AuthGroupBean gradeGroupBean = gradeGroupBeanList.get(0);
                                        if (null != gradeGroupBean) {
                                            AuthUserGroupBindBean userGradeBindBean = new AuthUserGroupBindBean();
                                            userGradeBindBean.setUserId(userId);
                                            userGradeBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                                            userGradeBindBean.setGroupId(gradeGroupBean.getGroupId());
                                            userGradeBindBean.setGroupType(gradeGroupBean.getGroupType());
                                            int gradeCount = userGroupBindDao.addUserGroupBind(userGradeBindBean);
                                            if (gradeCount > 0) {
                                                //添加用户和班级的绑定关系
                                                List<AuthClassBean> classBeanList = userManagerBean.getClassBeanList();
                                                for (AuthClassBean classBean : classBeanList) {
                                                    System.out.println(classBean.getClassId() + " --- " + ConstantEnum.GROUP_TYPE_CLASS.getType() + " --- " + userGradeBindBean.getGroupId());
                                                    AuthGroupBean classCondition = new AuthGroupBean();
                                                    classCondition.setGroupId(classBean.getClassId());
                                                    classCondition.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
                                                    classCondition.setGroupParentId(userGradeBindBean.getGroupId());
                                                    List<AuthGroupBean> classGroupBeanList = groupDao.getGroupByCondition(classCondition);
                                                    if (!CollectionUtils.isEmpty(classGroupBeanList)) {
                                                        AuthGroupBean classGroupBean = classGroupBeanList.get(0);
                                                        if (null != classGroupBean) {
                                                            AuthUserGroupBindBean userClassBindBean = new AuthUserGroupBindBean();
                                                            userClassBindBean.setUserId(userId);
                                                            userClassBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                                                            userClassBindBean.setGroupId(classGroupBean.getGroupId());
                                                            userClassBindBean.setGroupType(classGroupBean.getGroupType());
                                                            userGroupBindDao.addUserGroupBind(userClassBindBean);
                                                        } else {
                                                            throw new RuntimeException("查询不到班级数据，classId" + classBean.getClassId());
                                                        }
                                                    } else {
                                                        throw new RuntimeException("该年级下无此班级数据，classId" + classBean.getClassId());
                                                    }
                                                }
                                            }
                                        } else {
                                            throw new RuntimeException("查询不到年级数据，GradeId" + userManagerBean.getUserGradeId());
                                        }
                                    } else {
                                        throw new RuntimeException("该学校下无此年级数据，GradeId" + userManagerBean.getUserGradeId());
                                    }
                                }
                            }
                        } else {
                            throw new RuntimeException("查询不到学校数据，SchoolId" + userManagerBean.getUserSchoolId());
                        }
                        //TODO 添加老师和学科基础数据的绑定关系
                        if (!userManagerBean.getUserType().equals(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType())) {
                            AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                            dataDictionaryBean.setDataIden(String.valueOf(userManagerBean.getUserSubject()));
                            dataDictionaryBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                            List<AuthDataDictionaryBean> dataDictionaryList = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean);
                            if (!CollectionUtils.isEmpty(dataDictionaryList)) {
                                AuthUserDictionaryBindBean userDictionaryBindBean = new AuthUserDictionaryBindBean();
                                userDictionaryBindBean.setUserId(userId);
                                userDictionaryBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
                                userDictionaryBindBean.setDataId(dataDictionaryList.get(0).getDataId());
                                userDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
                                dataDictionaryDao.addUserDictionaryBindByCondition(userDictionaryBindBean);
                                bool = true;
                            } else {
                                throw new RuntimeException("无此学科，SubjectId" + userManagerBean.getUserSubject());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("AuthTeachServiceImpl.addTeacherManager-", e);
            throw new RuntimeException();
        }
        return bool;
    }


    /**
     * 后端管理  教师列表
     *
     * @param conditionBean
     * @return
     */
    @Override
    public PageInfo<AuthUserManagerConditionBean> getUserManagerListByCondition(AuthUserManagerConditionBean conditionBean) {
        if (conditionBean.getPageNum() != null && conditionBean.getPageSize() != null) {
            //开启分页
            PageHelper.startPage(conditionBean.getPageNum(), conditionBean.getPageSize());
        }
        List<AuthUserManagerConditionBean> conditionBeanList = userManagerDao.getUserManagerListByCondition(conditionBean);

        if (!CollectionUtils.isEmpty(conditionBeanList)) {

//            for (AuthUserManagerConditionBean conditionbean : conditionBeanList) {
//                // 查询该教师下的班级列表
//                List<AuthGroupBean> classBeanList = getClassByTeacherId(conditionbean.getUserId());
//                for (AuthGroupBean groupBean : classBeanList) {
//                    AuthClassBean classBean = new AuthClassBean();
//                    classBean.setClassId(groupBean.getGroupId());
//                    classBean.setClassName(groupBean.getGroupName());
//                    conditionbean.getClassBeanList().add(classBean);
//                }
//            }
            List<Map<String, Object>> list = groupDao.getManagerGroupByList(conditionBeanList);

            if (!CollectionUtils.isEmpty(list)) {
                for (AuthUserManagerConditionBean conditionbean : conditionBeanList) {
//                    List<AuthGroupBean> classBeanList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        if (null != map.get("user_id") && !map.get("user_id").equals(0) && map.get("user_id").equals(conditionbean.getUserId())) {
                            AuthClassBean classBean = new AuthClassBean();
                            if (null != map.get("group_id") && !map.get("group_id").equals(0))
                                classBean.setClassId(Integer.parseInt(map.get("group_id") + ""));
                            if (null != map.get("group_name") && !"".equals(map.get("group_name")))
                                classBean.setClassName(map.get("group_name") + "");
                            if (null != map.get("group_art") && !"".equals(map.get("group_art"))){
                                classBean.setGroupArt(Integer.parseInt(map.get("group_art") + ""));}
                            else{classBean.setGroupArt(ArtTypeEnum.NOTYPE.getValue());}
                            conditionbean.getClassBeanList().add(classBean);
                        }

                    }

                }
            }
            PageInfo<AuthUserManagerConditionBean> page = new PageInfo<>(conditionBeanList);
            return page;
        }

        return null;
    }

    /**
     * 权限管理-管理用户-用户禁用，启用
     *
     * @param
     * @return
     */
    public int updateUserManagerStatus(AuthUserManagerBean authUserManagerBean) {

        return userManagerDao.updateUserManagerStatus(authUserManagerBean);

    }

    /**
     * 一期  权限管理  用户登录获取左侧菜单列表(资源列表  一期只获取第一级)
     *
     * @param authUserManagerBean
     * @return
     */
    @Override
    public List<EnumBean> getUserManagerResourceList(AuthUserManagerBean authUserManagerBean) {
        List<Map<String, Object>> list = new ArrayList<>();

        if (null != authUserManagerBean.getUserType() && authUserManagerBean.getUserType() == ConstantEnum.USER_TYPE_SYSTEM_TEACHER.getType())
            list = userManagerDao.getUserManagerResourceList(authUserManagerBean);
        else if (null != authUserManagerBean.getUserType() && authUserManagerBean.getUserType() == ConstantEnum.USER_TYPE_SYSTEM_ADMIN.getType())
            list = userManagerDao.getUserManagerResourceListByAdmin(authUserManagerBean);

        List<EnumBean> enumbeans = new ArrayList<>();

        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                EnumBean enumBean = new EnumBean();
                enumBean.setResource_id(Integer.parseInt(map.get("resource_id") + ""));
                enumBean.setName(map.get("resource_name") + "");
                enumBean.setUrl(map.get("resource_url") + "");
                enumBean.setHasChild(0);
                enumBean.setType(Integer.parseInt(map.get("resource_type") + ""));
                enumbeans.add(enumBean);
            }
        }
        enumbeans = removeDuplicate(enumbeans);
        return enumbeans;
    }

    //排重
    private List<EnumBean> removeDuplicate(List<EnumBean> list) {
        List<EnumBean> result = new ArrayList<EnumBean>();
        Set<Integer> menuIds = new HashSet<Integer>();
        for (int i = 0; i < list.size(); i++) {
            EnumBean m = list.get(i);
            if (m != null && menuIds.add(m.getResource_id())) {
                result.add(m);
            }
        }
        return result;
    }


    /**
     * 二期  权限管理  用户登录获取左侧菜单列表(资源列表  一期只获取第一级)
     *
     * @param authUserManagerBean
     * @return
     */
    @Override
    public List<EnumBean> getUserManagerResourceByUserManager(AuthUserManagerBean authUserManagerBean) {
        //需加入超级管理员的资源查询，直接查询所有权限
        List<EnumBean> enumbeans = new ArrayList<>();

        if (null != authUserManagerBean.getUserType() && authUserManagerBean.getUserType() == ConstantEnum.USER_TYPE_SYSTEM_TEACHER.getType())
            enumbeans = userManagerDao.getUserManagerResourceByUserManager(authUserManagerBean);
        else if (null != authUserManagerBean.getUserType() && authUserManagerBean.getUserType() == ConstantEnum.USER_TYPE_SYSTEM_ADMIN.getType())
            enumbeans = userManagerDao.getUserManagerResourceByAdmin(authUserManagerBean);
        enumbeans = removeDuplicate(enumbeans);

        // 查看结果
//        for (EnumBean menu : enumbeans) {
//            System.out.println(menu);
//        }
        // 最后的结果
        List<EnumBean> menuList = new ArrayList<EnumBean>();
        if (!CollectionUtils.isEmpty(enumbeans)) {
            // 先找到所有的一级资源
            for (int i = 0; i < enumbeans.size(); i++) {
                // 一级资源parent_id 为 0
                if (enumbeans.get(i).getParent_id() == 0) {
                    menuList.add(enumbeans.get(i));
                }
            }
            // 为一级资源设置子资源，getChild是递归调用的
            for (EnumBean menu : menuList) {
//                System.out.println("一级目录 ： " + menu.getResource_id());
                menu.setChildren(getChild(menu.getResource_id(), enumbeans));
                if (null != menu.getChildren() && menu.getChildren().size() > 0)
                    menu.setHasChild(menu.getChildren().size());
                else menu.setHasChild(0);
            }
        }
        return menuList;
    }


    private List<EnumBean> getChild(Integer id, List<EnumBean> rootMenu) {
        // 子菜单
        List<EnumBean> childList = new ArrayList<EnumBean>();
        for (EnumBean menu : rootMenu) {
            // 遍历所有节点，将父资源id与传过来的id比较
            if (menu.getParent_id() == id) {
//                System.out.println("父节点 ： " + id + "  子节点 : " + menu.getResource_id());
                childList.add(menu);
            }
        }
        // 把子资源的子资源再循环一遍
        for (EnumBean menu : childList) {
//            if (menu.getType() != 2) {// 不是按钮的资源还有子资源
            // 递归
            menu.setChildren(getChild(menu.getResource_id(), rootMenu));
            if (null != menu.getChildren() && menu.getChildren().size() > 0)
                menu.setHasChild(menu.getChildren().size());
            else menu.setHasChild(0);
        }
//        } // 递归退出条件
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }

    /**
     * 批量更新用户account(账号)信息
     *
     * @param authUserManagerBeans
     * @return
     */
    @Override
    public boolean batchUpdateManagerAccount(List<AuthUserManagerBean> authUserManagerBeans) {

        return userManagerDao.batchUpdateManagerAccount(authUserManagerBeans) > 0;

    }

    /**
     * 删除教师信息并删除教师的组织绑定关系
     *
     * @param authUserManagerBean
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delTeacherManager(AuthUserManagerBean authUserManagerBean) {

//       int ucount = userManagerDao.delTeacherManager(authUserManagerBean);

//       if(ucount > 0) {
        try {
            AuthUserGroupBindBean querybind = new AuthUserGroupBindBean();
            querybind.setUserId(authUserManagerBean.getUserId());
            querybind.setUserType(authUserManagerBean.getUserType());
            List<AuthUserGroupBindBean> result = userGroupBindDao.getUserGroupBindForRecord(querybind);
            if (CollectionUtils.isEmpty(result)) {
                throw new RuntimeException("组织记录信息添加失败，userId:" + authUserManagerBean.getUserId() + "，userType:" + authUserManagerBean.getUserType());
            }
            List<AuthUserManagerGroupRecordBean> list = new ArrayList<>();
            list = PageHelperUtil.converterList(result, AuthUserManagerGroupRecordBean.class);
            for (AuthUserManagerGroupRecordBean bean : list) {
                bean.setUserOperating(ConstantEnum.GROUP_OPERATING_DELETE.getType());
            }
            userManagerGroupRecordDao.batchSaveUserMangerGroupRecord(list);

//            AuthUserManagerGroupRecordBean bean = new AuthUserManagerGroupRecordBean();
//            bean.setUserId(authUserManagerBean.getUserId());
//            bean.setUserType(authUserManagerBean.getUserType());
//            bean.setUserOperating(ConstantEnum.GROUP_OPERATING_DELETE.getType());
//            int result = userManagerGroupRecordDao.addUserManagerGroupRecordByUserGroup(bean);
//            if (result <= 0) {
//                throw new RuntimeException("组织记录信息添加失败，userId:" + authUserManagerBean.getUserId() + "，userType:" + authUserManagerBean.getUserType());
//            }
            AuthUserGroupBindBean gradebind = new AuthUserGroupBindBean();
            gradebind.setUserId(authUserManagerBean.getUserId());
            gradebind.setUserType(authUserManagerBean.getUserType());
            gradebind.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
            int deleteGradeCount = userGroupBindDao.delUserGroupBindByUserIDAndGroupType(gradebind);
            AuthUserGroupBindBean classbind = new AuthUserGroupBindBean();
            classbind.setUserId(authUserManagerBean.getUserId());
            classbind.setUserType(authUserManagerBean.getUserType());
            classbind.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
            int deleteclassCount = userGroupBindDao.delUserGroupBindByUserIDAndGroupType(classbind);
            //删除用户绑定的学科信息
//            int deleteDictCount = dataDictionaryDao.deleteUserDictionaryBindByUserId(authUserManagerBean);

            int updatecount = userManagerDao.updateUserManagerGroupNull(authUserManagerBean);
//       }
            return updatecount;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 校长端，查询校长学校，学段信息
     *
     * @param condition
     * @return
     */
    @Override
    public AuthUserManagerBean getPrincipalInfo(AuthUserManagerBean condition) {
        AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
        condition.setUserType(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType());
        List<AuthUserManagerBean> userManagerBeans = userManagerDao.getUserManagerList(condition);
        if (!CollectionUtils.isEmpty(userManagerBeans)) {
            userManagerBean = userManagerBeans.get(0);
            //查询老师和组织的绑定信息
            AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
            userGroupBindBean.setUserId(condition.getUserId());
            userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType());
            userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
            List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
            if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
                AuthUserGroupBindBean groupBindBean = userGroupBindBeanList.get(0);
                //查询学校
                if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_SCHOOL.getType()) {
                    AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
                    userManagerBean.setUserSchoolId(groupBean.getGroupId());
                    userManagerBean.setSchoolName(groupBean.getGroupName());

                    //查询学校绑定的学段
                    AuthGroupDictionaryBindBean bindBean = new AuthGroupDictionaryBindBean();
                    bindBean.setGroupId(groupBindBean.getGroupId());
                    bindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
                    bindBean.setDataType(ConstantEnum.DATA_DICTIONARY_PERIOD_TYPE.getType());
                    List<AuthGroupDictionaryBindBean> authGroupDictionaryBindBeans =
                            dataDictionaryDao.getGroupDictionaryBindByCondition(bindBean);
                    if (!CollectionUtils.isEmpty(authGroupDictionaryBindBeans)) {
                        String[] schoolsections = new String[authGroupDictionaryBindBeans.size()];
                        for (int i = 0; i <= authGroupDictionaryBindBeans.size() - 1; i++) {
                            AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
                            dataDictionaryBean.setDataId(authGroupDictionaryBindBeans.get(i).getDataId());
                            AuthDataDictionaryBean dictionaryBean = dataDictionaryDao.getDataDictionaryByCondition(dataDictionaryBean).get(0);
                            schoolsections[i] = dictionaryBean.getDataIden();
                        }
                        userManagerBean.setSchoolsections(schoolsections);
                    }
                }
            }
            return userManagerBean;
        }
        return null;
    }


    /**
     * 根据条件分页查询管理者列表
     *
     * @param managerBean
     * @return
     */
    @Override
    public PageInfo<AuthUserManagerBean> getUserManagerPage(AuthUserManagerBean managerBean) {
        if (managerBean.getPageNum() != null && managerBean.getPageSize() != null) {
            //开启分页
            PageHelper.startPage(managerBean.getPageNum(), managerBean.getPageSize());
        }
        List<AuthUserManagerBean> list = userManagerDao.getUserManagerList(managerBean);
        if (!CollectionUtils.isEmpty(list)) {
            List<AuthUserRoleBean> userRoleBeans = authRoleDao.getUserRoleBindInfoList(list);
            Map<Integer, List<AuthUserRoleBean>> map = userRoleBeans.stream().collect(Collectors.groupingBy(AuthUserRoleBean::getUserId));
            for (AuthUserManagerBean userbean : list) {
                if (!CollectionUtils.isEmpty(map.get(userbean.getUserId()))) {
                    List<AuthUserRoleBean> authUserRoleBeans = map.get(userbean.getUserId());
                    List<AuthRoleBean> authRoleBeans = new ArrayList<>();
                    for (AuthUserRoleBean authUserRoleBean : authUserRoleBeans) {
                        AuthRoleBean authRoleBean = new AuthRoleBean();
                        authRoleBean.setRoleId(authUserRoleBean.getRoleId());
                        authRoleBean.setRoleName(authUserRoleBean.getRoleName());
                        authRoleBeans.add(authRoleBean);
                    }
                    userbean.setRoleBeanList(authRoleBeans);
                }
            }
        }
        PageInfo<AuthUserManagerBean> page = new PageInfo<>(list);
        return page;
    }


}
