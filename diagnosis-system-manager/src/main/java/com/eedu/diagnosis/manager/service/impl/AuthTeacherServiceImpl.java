package com.eedu.diagnosis.manager.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.service.*;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.exception.exceptionCode.ExceptionCodeEnum;
import com.eedu.diagnosis.common.utils.MD5Utils;
import com.eedu.diagnosis.common.utils.ProcessUtils;
import com.eedu.diagnosis.manager.service.AuthTeacherService;
import com.eeduspace.uuims.api.OauthClient;
import com.eeduspace.uuims.api.request.batch.BatchCreateUserRequest;
import com.eeduspace.uuims.api.response.batch.BatchCreateUserResponse;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 18:11
 * Describe:
 */
@Service
@Slf4j
public class AuthTeacherServiceImpl implements AuthTeacherService {

	@Autowired
	private AuthUserManagerService userManagerService;
	@Autowired
	private AuthGroupService groupService;
	@Autowired
	private AuthUserGroupBindService userGroupBindService;
	@Autowired
	private AuthRoleService roleService;
	@Autowired
	private AuthUserService authUserService;

	@Value("${diagnosis.protal.uuims.server.url}")
	private String serverUrl;
	@Value("${diagnosis.protal.accessKey}")
	private String accessKey;
	@Value("${diagnosis.protal.secretKey}")
	private String secretKey;
	@Value("${diagnosis.protal.productType}")
	private String productType;


	/**
	 * 添加管理者信息
	 *
	 * @param managerBean
	 * @return
	 */
	@Override
	public boolean addUserManager(AuthUserManagerBean managerBean) {
		managerBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
//		managerBean.setUserPassword(Digest.md5Digest(managerBean.getUserPassword()));
		return userManagerService.addUserManager(managerBean) > 0;
	}

	/**
	 * 查询学校下面的老师
	 *
	 * @param groupBean
	 * @return
	 */
	@Override
	public List<AuthUserManagerBean> getTeacherListBySchool(AuthGroupBean groupBean) {
		AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
		userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
		userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
		userGroupBindBean.setGroupId(groupBean.getGroupId());
		List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindService.getUserGroupBindByCondition(userGroupBindBean);
		Map<String,Object> maps = new HashMap();
		maps.put("userIdList",userGroupBindBeanList);
		List<AuthUserManagerBean> managerBeenList = userManagerService.getUserByListId(maps);
		return managerBeenList;
	}

	/**
	 * 查询老师下面的班级
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public List<AuthGroupBean> getClassByTeacher(AuthUserManagerBean userManagerBean) {
		return userManagerService.getClassByTeacherId(userManagerBean.getUserId());
	}

	/**
	 * 查询班级下面的学生
	 *
	 * @param groupBean
	 * @return
	 */
	@Override
	public List<AuthUserBean> getMyStudentByClass(AuthGroupBean groupBean) {
		return userManagerService.getMyStudentByClassId(groupBean.getGroupId());
	}

	/**
	 * 根据条件查询管理者信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean userManagerBean) {
		return userManagerService.getUserManagerList(userManagerBean);
	}

	/**
	 * 根据教师id查询教师下面的班级信息
	 *
	 * @param teacherId
	 * @return
	 */
	@Override
	public List<AuthGroupBean> getClassByTeacherId(Integer teacherId) {
		return userManagerService.getClassByTeacherId(teacherId);
	}

	/**
	 * 查询班级下面的学生
	 *
	 * @param classId
	 * @return
	 */
	@Override
	public List<AuthUserBean> getMyStudentByClassId(Integer classId) {
		return userManagerService.getMyStudentByClassId(classId);
	}

	/**
	 * 教师管理列表
	 *
	 * @param conditionBean
	 * @return
	 */
	@Override
	public PageInfo<AuthUserManagerConditionBean> getTeacherManagerList(AuthUserManagerConditionBean conditionBean) {
		return userManagerService.getUserManagerListByCondition(conditionBean);
	}

	/**
	 * 删除老师，删除老师的时候需要删除有关联的数据
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public boolean delTeacherManager(AuthUserManagerBean userManagerBean) {
		if(null != userManagerBean && userManagerBean.getUserId() != null && userManagerBean.getUserId() > 0){
			if(userManagerService.delUserManager(userManagerBean.getUserId()) > 0){
				//删除用户和角色的绑定信息
				AuthUserRoleBean userRoleBean = new AuthUserRoleBean();
				userRoleBean.setUserId(userRoleBean.getUserId());
				List<AuthUserRoleBean> userRoleBeanList = roleService.getUserRoleBindInfo(userRoleBean);
				if(!CollectionUtils.isEmpty(userRoleBeanList)){
					for(AuthUserRoleBean userRole : userRoleBeanList){
						roleService.delUserRoleInfo(userRole.getId());
					}
				}
				//删除用户和组织的绑定信息
				AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
				userGroupBindBean.setUserId(userManagerBean.getUserId());
				List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindService.getUserGroupBindByCondition(userGroupBindBean);
				if(!CollectionUtils.isEmpty(userGroupBindBeanList)){
					for(AuthUserGroupBindBean userGroupBind : userGroupBindBeanList){
						userGroupBindService.delUserGroupBind(userGroupBind.getId());
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 修改老师信息
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean updateTeacherManager(AuthUserManagerBean condition) {
//		if(null != condition && StringUtils.isNotEmpty(condition.getUserPassword())){
//			condition.setUserPassword(Digest.md5Digest(condition.getUserPassword()));
//		}
		AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
		userGroupBindBean.setUserId(condition.getUserId());
		userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
		//更新学校
		if(null != condition.getUserSchoolId() && condition.getUserSchoolId() > 0){
			userGroupBindBean.setGroupId(condition.getUserSchoolId());
			userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
			userGroupBindService.validateUserGroupBindInfo(userGroupBindBean);
		}
		//更新年级
		if(null != condition.getUserGradeId() && condition.getUserGradeId() > 0){
			userGroupBindBean.setGroupId(condition.getUserGradeId());
			userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
			userGroupBindService.validateUserGroupBindInfo(userGroupBindBean);
		}
		//更新班级
		if(null != condition.getUserClassId() && condition.getUserClassId() > 0){
			userGroupBindBean.setGroupId(condition.getUserClassId());
			userGroupBindBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
			userGroupBindService.validateUserGroupBindInfo(userGroupBindBean);
		}

		return userManagerService.updateUserManager(condition) > 0;
	}

	/**
	 * 教师端 查询老师个人信息
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public AuthUserManagerBean getTeacherInfo(AuthUserManagerBean condition) {
		return userManagerService.getTeacherInfo(condition);
	}

	/**
	 * 根据userId查询用户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public AuthUserManagerBean getTeacherInfoById(Integer userId) {
		return userManagerService.getUserByUserId(userId);
	}

	/**
	 * 查询帐号和手机号是否存在
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public AuthUserManagerBean getUserIsExist(AuthUserManagerBean userManagerBean) {
		return userManagerService.getUserIsExist(userManagerBean);
	}

	/**
	 * 后台 添加教师
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public boolean addTeacherManager(AuthUserManagerBean userManagerBean) {
		//默认6位随机数
//		userManagerBean.setUserAccount(String.valueOf((int)((Math.random()*9+1)*100000)));
		//默认密码123456
		userManagerBean.setUserPassword(MD5Utils.getMD5("123456"));
		return userManagerService.addTeacherManager(userManagerBean);
	}

	/**
	 * 批量导入教师信息
	 * @param teacherInfoList
	 * @param schoolId
	 * @return
	 */
	@Override
	public boolean batchSaveTeacher(List<String[]> teacherInfoList, Integer schoolId) throws Exception {

		AuthGroupBean groupInfo = groupService.getGroupInfoById(schoolId);
		if(null == groupInfo) throw new RuntimeException("查询不到学校数据，SchoolId : " + schoolId);

		List<AuthUserManagerBean> userManagerBeans = new ArrayList<>();
		AuthUserManagerBean userManagerBean = null;
		for (String[] ss:teacherInfoList) {
			userManagerBean = new AuthUserManagerBean();
			userManagerBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
			userManagerBean.setUserProductId(ConstantEnum.PRODUCT_TYPE_ID.getType());
//			userManagerBean.setUserAccount(String.valueOf((int)((Math.random()*9+1)*100000)));
			userManagerBean.setUserPassword(MD5Utils.getMD5("123456"));
			userManagerBean.setUserName(ss[0]);
			int sex = 0;
			if(StringUtils.isEmpty(ss[1])){
				sex = 1;
			}else if("男".equals(ss[1].trim())){
				sex = 1;
			}else{
				sex = 0;
			}
//			userManagerBean.setUserPhone(ss[2]);
			userManagerBean.setUserSex(sex);
			userManagerBean.setCreateDate(new Date());
			userManagerBeans.add(userManagerBean);
		}
		List<AuthUserManagerBean> users = userManagerService.batchSaveUserManager(userManagerBeans);

		for(AuthUserManagerBean bean : users){
			bean.setUserAccount(ProcessUtils.parseLong(bean.getUserId()));
		}
		boolean result = userManagerService.batchUpdateManagerAccount(users);
		if(!result) throw new RuntimeException("更新用户account失败 ！ ");
		List<AuthUserGroupBindBean> beans = new ArrayList<>();
		if(null != users && !users.isEmpty()){
			AuthUserGroupBindBean userGroupBindBean = null;
			for (AuthUserManagerBean bean:users) {
				userGroupBindBean = new AuthUserGroupBindBean();
				userGroupBindBean.setUserId(bean.getUserId());
				userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
				userGroupBindBean.setGroupId(groupInfo.getGroupId());
				userGroupBindBean.setGroupType(groupInfo.getGroupType());
				beans.add(userGroupBindBean);
			}
			userGroupBindService.batchSaveUserGroupBind(beans);
		}
		return true;
	}

	/**
	 * 根据手机号查询用户
	 *
	 * @param userPhone
	 * @return
	 */
	@Override
	public AuthUserManagerBean getUserByUserPhone(String userPhone) {
		return userManagerService.getUserByUserPhone(userPhone);
	}

	/**
	 * 批量更新用户account（账号）信息
	 *
	 * @param list
	 * @return
	 */
	@Override
	public boolean batchUpdateManagerAccount(List<AuthUserManagerBean> list) {
		return userManagerService.batchUpdateManagerAccount(list);
	}


	/**
	 * 后台 修改老师年级，学科，班级信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public boolean updateTeacherManagerGroup(AuthUserManagerBean userManagerBean) {

		return userManagerService.updateTeacherManagerGroup(userManagerBean);
	}

	/**
	 * 校长端 查询校长学校，学段信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public AuthUserManagerBean getPrincipalInfo(AuthUserManagerBean userManagerBean) throws Exception{

		return userManagerService.getPrincipalInfo(userManagerBean);
	}

	/**
	 * 后台 删除老师年级，学科，班级信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	@Override
	public boolean delTeacherManagerGroup(AuthUserManagerBean userManagerBean) {

		return userManagerService.delTeacherManager(userManagerBean) > 0;
	}

	@Override
	public void batchSaveStudentInfo(List<String[]> studentInfoList) throws Exception{
		List<String> userAccounts = new ArrayList<>();
		List<com.eeduspace.uuims.api.model.UserModel> userModels = new ArrayList<>();
		for (String[] user : studentInfoList) {
			if(userAccounts.contains(user[1])) throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_DUPLICATE,"学号为 "+user[1]+" 重复，请核对导入的数据。");
			userAccounts.add(user[1]);
			com.eeduspace.uuims.api.model.UserModel userModel = new com.eeduspace.uuims.api.model.UserModel();
			userModel.setAccessKey(accessKey);
			userModel.setSecretKey(secretKey);
			userModel.setProductType(productType);
			userModel.setName(user[1]);
			userModel.setPassword(MD5Utils.getMD5("123456"));
			userModels.add(userModel);
		}
		BatchCreateUserRequest createUserRequest = new BatchCreateUserRequest();
		createUserRequest.setUserList(userModels);
		OauthClient oauthClient = new OauthClient(serverUrl,accessKey,secretKey);
		BatchCreateUserResponse execute = oauthClient.execute(createUserRequest);
		System.out.println(JSONObject.toJSONString(execute));
		BatchCreateUserResponse result = JSONObject.parseObject(JSONObject.toJSONString(execute.getResult()),BatchCreateUserResponse.class);
		if(result.getBatchRepeatSize()>0) throw new DiagnosisException(ExceptionCodeEnum.RESOURCE_DUPLICATE,"学号重复，请核对导入的数据。");

		List<AuthUserBean> authUserBeans = new ArrayList<>();
		for (com.eeduspace.uuims.api.model.UserModel userModel : result.getUserList()) {
			for (String[] user : studentInfoList) {
				if(user[1].equals(userModel.getName())){
					AuthUserBean authUserBean = new AuthUserBean();
					authUserBean.setUserOpenId(userModel.getOpenId());
					authUserBean.setUserName(user[0]);
					authUserBean.setUserAccount(userModel.getName());
					authUserBean.setUserAccesskey(userModel.getAccessKey());
					authUserBean.setUserSecretkey(userModel.getSecretKey());
					authUserBeans.add(authUserBean);
				}
			}
		}
		authUserService.batchSaveStudentInfo(authUserBeans);

	}
}
