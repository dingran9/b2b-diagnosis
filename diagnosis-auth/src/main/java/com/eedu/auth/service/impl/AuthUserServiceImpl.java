package com.eedu.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.dao.AuthGroupDao;
import com.eedu.auth.dao.AuthUserDao;
import com.eedu.auth.dao.AuthUserGroupBindDao;
import com.eedu.auth.dao.AuthUserGroupRecordDao;
import com.eedu.auth.enumration.EventSourceEnum;
import com.eedu.auth.eventListener.AuthEvent;
import com.eedu.auth.eventListener.EventSourceHandler;
import com.eedu.auth.service.AuthDataDictionaryService;
import com.eedu.auth.service.AuthUserGroupBindService;
import com.eedu.auth.service.AuthUserGroupRecordService;
import com.eedu.auth.service.AuthUserService;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/14
 * Time: 17:11
 * Describe:
 */
@Service("userServiceImpl")
@com.alibaba.dubbo.config.annotation.Service
public class AuthUserServiceImpl implements AuthUserService,ApplicationContextAware {
	@Autowired
	private AuthUserDao authUserDao;
	@Autowired
	private AuthUserGroupBindDao userGroupBindDao;
	@Autowired
	private AuthGroupDao groupDao;
	@Autowired
	private AuthDataDictionaryService dataDictionaryService;
	@Autowired
	private AuthUserGroupBindService userGroupBindService;
	@Autowired
	private AuthUserGroupRecordService authUserGroupRecordService;
	@Autowired
	private AuthUserGroupRecordDao authUserGroupRecordDao;

	private ApplicationContext applicationContext;
	/**
	 * 根据条件查询所有用户
	 *
	 * @param userBean
	 * @return
	 */
	@Override
	public List<AuthUserBean> getUserByCondition(AuthUserBean userBean) throws Exception {
		return authUserDao.getUserByCondition(userBean);
	}

	/**
	 * 添加用户-注册
	 *
	 * @param userBean
	 * @return
	 */
	@Override
	public int addUserInfo(AuthUserBean userBean) throws Exception {
		AuthUserBean authUserBean = new AuthUserBean();
		authUserBean.setUserAccount(userBean.getUserAccount());
		authUserBean.setUserPhone(userBean.getUserPhone());
		List<AuthUserBean> userBeenList = this.getUserByCondition(authUserBean);
		if(!CollectionUtils.isEmpty(userBeenList)){
			return 0;
		}
		return authUserDao.addUserInfo(userBean);
	}

	/**
	 * 修改学生个人信息
	 *
	 * @param condition
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateStudentInfo(AuthUserBean condition) throws Exception {
		return authUserDao.updateUserInfo(condition);
	}



	/**
	 * 修改用户信息
	 *
	 * @param condition
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateUserInfo(AuthUserBean condition) throws Exception {
		AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
		userGroupBindBean.setUserId(condition.getUserId());
		userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
		//更新学校
		if(null != condition.getSchoolId() && condition.getSchoolId() > 0){
			userGroupBindBean.setGroupId(condition.getSchoolId());
			userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
			userGroupBindService.validateUserGroupBindInfo(userGroupBindBean);
		}
		//更新年级
		if(null != condition.getGradeId() && condition.getGradeId() > 0){
			AuthGroupBean groupBean = groupDao.getGroupInfoById(condition.getGradeId());
			if(null != groupBean){
				if(groupBean.getGroupIden().equals(32) || groupBean.getGroupIden().equals(33)){
					if(condition.getArtType() == null){
						throw new RuntimeException("高二高三学生没有选择文理类型");
					}
				}else {
					condition.setArtType(2);//非高二 高三生 纹理类型定义为无
				}
			}
			userGroupBindBean.setGroupId(condition.getGradeId());
			userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
			userGroupBindService.validateUserGroupBindInfo(userGroupBindBean);
		}
		//更新班级
		if(null != condition.getClassId() && condition.getClassId() > 0){
			userGroupBindBean.setGroupId(condition.getClassId());
			userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
			userGroupBindService.validateUserGroupBindInfo(userGroupBindBean);
		}
		return authUserDao.updateUserInfo(condition);
	}

	/**
	 * 根据帐号和密码查找用户
	 *
	 * @param userBean
	 * @return
	 */
	@Override
	public AuthUserBean getUserByAccountAndPwd(AuthUserBean userBean) throws Exception {
		return authUserDao.getUserByAccountAndPwd(userBean);
	}

	/**
	 * 根据用户ID查询用户
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AuthUserBean getUserById(Integer userId) throws Exception {
		return authUserDao.getUserById(userId);
	}

	/**
	 * 获取个人信息
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public AuthUserBean getStudentInfo(AuthUserBean condition) throws Exception {
		List<AuthUserBean> userBeanList = authUserDao.getUserByCondition(condition);
		if(!CollectionUtils.isEmpty(userBeanList)) {
			AuthUserBean userBean = userBeanList.get(0);
			//查询学生和组织的绑定关系
			AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
			userGroupBindBean.setUserId(userBean.getUserId());
			userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
			List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindDao.getUserGroupBindByCondition(userGroupBindBean);
			if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
				for (AuthUserGroupBindBean groupBindBean : userGroupBindBeanList) {
					//查询学校
					if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_SCHOOL.getType()) {
						AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
						userBean.setSchoolId(groupBean.getGroupId());
						userBean.setSchoolName(groupBean.getGroupName());
					}
					//查询年级
					if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_GRADE.getType()) {
						AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
						userBean.setGradeId(groupBean.getGroupId());
						userBean.setGradeName(groupBean.getGroupName());
						userBean.setGradeIden(groupBean.getGroupIden());
					}
					//查询班级
					if (groupBindBean.getGroupType() == ConstantEnum.GROUP_TYPE_CLASS.getType()) {
						AuthGroupBean groupBean = groupDao.getGroupInfoById(groupBindBean.getGroupId());
						userBean.setClassId(groupBean.getGroupId());
						userBean.setClassName(groupBean.getGroupName());
					}
				}
				//查询学校购买绑定的学科应用到学生
				AuthGroupDictionaryBindBean groupDictionaryBindBean = new AuthGroupDictionaryBindBean();
				groupDictionaryBindBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
				groupDictionaryBindBean.setGroupId(userBean.getGradeId());
				groupDictionaryBindBean.setDataType(ConstantEnum.DATA_DICTIONARY_SUBJECT_TYPE.getType());
				List<AuthGroupDictionaryBindBean> groupDictionaryBindBeanList =
						dataDictionaryService.getGroupDictionaryBindByCondition(groupDictionaryBindBean);
				if (!CollectionUtils.isEmpty(groupDictionaryBindBeanList)) {
					String[] subjectCodes = new String[groupDictionaryBindBeanList.size()];
					for (int i = 0; i <= groupDictionaryBindBeanList.size() - 1; i++) {
						AuthDataDictionaryBean dataDictionaryBean = new AuthDataDictionaryBean();
						dataDictionaryBean.setDataId(groupDictionaryBindBeanList.get(i).getDataId());
						AuthDataDictionaryBean dictionaryBean = dataDictionaryService.getDataDictionaryByCondition(dataDictionaryBean).get(0);
						subjectCodes[i] = dictionaryBean.getDataIden();
					}
					userBean.setUserSubjects(subjectCodes);
				}
				return userBean;
			}
			return userBean;
		}
		return null;
	}
	/**
	 * 获取学校，学年下的学生列表
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public PageInfo<AuthUserBean> getUserBySchoolAndGrade(AuthUserBean condition,Integer pageNum,Integer pageSize) throws Exception {
		if (pageNum!=null && pageSize!=null) {
			PageHelper.startPage(pageNum,pageSize);
		}
        List<AuthUserBean> userBySchoolAndGrade = authUserDao.getUserBySchoolAndGrade(condition);
        PageInfo<AuthUserBean> pageInfo = new PageInfo<AuthUserBean>(userBySchoolAndGrade);
        return pageInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delStudentGroup(AuthUserBean condition){
		int count = 1;
		try {
			AuthUserGroupBindBean querybind = new AuthUserGroupBindBean();
			querybind.setUserId(condition.getUserId());
			querybind.setUserType(condition.getUserType());
			List<AuthUserGroupBindBean> result = userGroupBindDao.getUserGroupBindForRecord(querybind);
			if (CollectionUtils.isEmpty(result)) {
				throw new RuntimeException("组织记录信息添加失败，userId:" + condition.getUserId() + "，userType:" + condition.getUserType());
			}
			List<AuthUserGroupRecordBean> list = new ArrayList<>();
			list = PageHelperUtil.converterList(result,AuthUserGroupRecordBean.class);
			for(AuthUserGroupRecordBean bean : list){
				bean.setUserOperating(ConstantEnum.GROUP_OPERATING_DELETE.getType());
			}
			authUserGroupRecordDao.batchSaveUserGroupRecord(list);

//			AuthUserGroupRecordBean recordBean = new AuthUserGroupRecordBean();
//			recordBean.setUserId(condition.getUserId());
//			recordBean.setUserType(condition.getUserType());
//			recordBean.setUserOperating(ConstantEnum.GROUP_OPERATING_DELETE.getType());
//			int recordcount = authUserGroupRecordService.addUserGroupRecordByUserGroup(recordBean);
//			if(recordcount <= 0) {
//				throw new RuntimeException("组织记录信息添加失败，userId:" + condition.getUserId() + "，userType:" + condition.getUserType());
//			}
			AuthUserManagerBean bean = new AuthUserManagerBean();
			bean.setUserId(condition.getUserId());
			bean.setUserType(condition.getUserType());
			count = userGroupBindDao.delUserGroupBindByUserID(bean);
			count = authUserDao.updateUserGroupNull(condition);
			return count;
		}catch(Exception e){
         e.printStackTrace();
         throw new RuntimeException();
		}
	}

	/**
	 *重新绑定学生的组织信息
	 * @param condition
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int reBindStudentGroup(AuthUserBean condition){

		try {

			AuthUserGroupBindBean querybind = new AuthUserGroupBindBean();
			querybind.setUserId(condition.getUserId());
			querybind.setUserType(condition.getUserType());
			List<AuthUserGroupBindBean> result = userGroupBindDao.getUserGroupBindForRecord(querybind);
			if (CollectionUtils.isEmpty(result)) {
				throw new RuntimeException("组织记录信息添加失败，userId:" + condition.getUserId() + "，userType:" + condition.getUserType());
			}
			List<AuthUserGroupRecordBean> list = new ArrayList<>();
			list = PageHelperUtil.converterList(result,AuthUserGroupRecordBean.class);
			for(AuthUserGroupRecordBean bean : list){
				bean.setUserOperating(ConstantEnum.GROUP_OPERATING_UPDATE.getType());
			}
			authUserGroupRecordDao.batchSaveUserGroupRecord(list);
//			AuthUserGroupRecordBean recordBean = new AuthUserGroupRecordBean();
//			recordBean.setUserId(condition.getUserId());
//			recordBean.setUserType(condition.getUserType());
//			recordBean.setUserOperating(ConstantEnum.GROUP_OPERATING_UPDATE.getType());
//			int recordcount = authUserGroupRecordService.addUserGroupRecordByUserGroup(recordBean);
//			if (recordcount <= 0) {
//				throw new RuntimeException("组织记录信息添加失败，userId:" + condition.getUserId() + "，userType:" + condition.getUserType());
//			}
			AuthUserManagerBean bean = new AuthUserManagerBean();
			bean.setUserId(condition.getUserId());
			bean.setUserType(condition.getUserType());
			int count = userGroupBindDao.delUserGroupBindByUserID(bean);
			if(count > 0) {
				AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
				userGroupBindBean.setUserId(condition.getUserId());
				userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
				//更新学校
				if (null != condition.getSchoolId() && condition.getSchoolId() > 0) {
					userGroupBindBean.setGroupId(condition.getSchoolId());
					userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
					userGroupBindDao.addUserGroupBind(userGroupBindBean);
				}
				//更新年级
				if (null != condition.getGradeId() && condition.getGradeId() > 0) {
					userGroupBindBean.setGroupId(condition.getGradeId());
					userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
					userGroupBindDao.addUserGroupBind(userGroupBindBean);
				}
				//更新班级
				if (null != condition.getClassId() && condition.getClassId() > 0) {
					userGroupBindBean.setGroupId(condition.getClassId());
					userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
					userGroupBindDao.addUserGroupBind(userGroupBindBean);
				}
			}
			return count;
		}catch(Exception e){
           e.printStackTrace();
           throw new RuntimeException();
		}

	}

	/**
	 * 异步更新学生的登录时间
	 * @param authUserBean
	 */
	@Override
	public void saveStudentLoginTime(AuthUserBean authUserBean) throws Exception{
		AuthEvent event = new AuthEvent(new EventSourceHandler(JSONObject.toJSONString(authUserBean), EventSourceEnum.SAVE_STUDENT_LOGIN_TIME));
		applicationContext.publishEvent(event);
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	/**
	 * 批量删除学生绑定的组织关系
	 * @param conditions
	 * @throws BeansException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int delStudentGroupBatch(List<AuthUserBean> conditions){
		int count = 1;
		try {
			Map<String,Object> maps = new HashMap();
			maps.put("userIdList",conditions);
			maps.put("userType",ConstantEnum.USER_TYPE_STUDENT.getType());
//			maps.put("userOperating",ConstantEnum.GROUP_OPERATING_DELETE.getType());
			List<AuthUserGroupBindBean> result = userGroupBindDao.getUserGroupBindBystudentIds(maps);
			if (CollectionUtils.isEmpty(result)) {
				throw new RuntimeException("批量添加组织记录信息添加失败，UserList:" + conditions);
			}
			List<AuthUserGroupRecordBean> list = new ArrayList<>();
			list = PageHelperUtil.converterList(result,AuthUserGroupRecordBean.class);
			for(AuthUserGroupRecordBean bean : list){
				bean.setUserOperating(ConstantEnum.GROUP_OPERATING_UPDATE.getType());
			}
			authUserGroupRecordDao.batchSaveUserGroupRecord(list);

//			int recordcount = authUserGroupRecordDao.addUserGroupRecordByUserGroupBatch(maps);
//			if(recordcount <= 0) {
//				throw new RuntimeException("学生组织记录信息批量添加失败，userId:" + conditions.toArray().toString());
//			}
			count = userGroupBindDao.delUserGroupBindByUserIDBatch(maps);
			count = authUserDao.updateUserGroupNullBatch(maps);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public void batchSaveStudentInfo(List<AuthUserBean> authUserBeans) {
		authUserDao.batchSaveStudentInfo(authUserBeans);
	}
}
