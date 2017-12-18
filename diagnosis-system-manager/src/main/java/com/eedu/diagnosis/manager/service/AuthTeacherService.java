package com.eedu.diagnosis.manager.service;

import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 18:11
 * Describe:
 */
public interface AuthTeacherService {

	/**
	 * 添加管理者信息
	 * @param managerBean
	 * @return
	 */
	boolean addUserManager(AuthUserManagerBean managerBean);


	/**
	 * 查询学校下面的老师
	 * @param groupBean
	 * @return
	 */
	List<AuthUserManagerBean> getTeacherListBySchool(AuthGroupBean groupBean);

	/**
	 * 查询老师下面的班级
	 * @param userManagerBean
	 * @return
	 */
	List<AuthGroupBean> getClassByTeacher(AuthUserManagerBean userManagerBean);

	/**
	 * 查询班级下面的学生
	 * @param groupBean
	 * @return
	 */
	List<AuthUserBean> getMyStudentByClass(AuthGroupBean groupBean);

	/**
	 * 根据条件查询管理者信息
	 * @param userManagerBean
	 * @return
	 */
	List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean userManagerBean);

	/**
	 * 根据教师id查询教师下面的班级信息
	 * @param teacherId
	 * @return
	 */
	List<AuthGroupBean> getClassByTeacherId(Integer teacherId);

	/**
	 * 查询班级下面的学生
	 * @param classId
	 * @return
	 */
	List<AuthUserBean> getMyStudentByClassId(Integer classId);

	/**
	 * 教师管理列表
	 * @param conditionBean
	 * @return
	 */
	PageInfo<AuthUserManagerConditionBean> getTeacherManagerList(AuthUserManagerConditionBean conditionBean);

	/**
	 * 删除用户
	 * @param userManagerBean
	 * @return
	 */
	boolean delTeacherManager(AuthUserManagerBean userManagerBean);

	/**
	 * 修改老师信息
	 * @param userManagerBean
	 * @return
	 */
	boolean updateTeacherManager(AuthUserManagerBean userManagerBean);

	/**
	 * 查询老师个人信息
	 * @param userManagerBean
	 * @return
	 */
	AuthUserManagerBean getTeacherInfo(AuthUserManagerBean userManagerBean);

	/**
	 * 根据userId查询用户信息
	 * @param userId
	 * @return
	 */
	AuthUserManagerBean getTeacherInfoById(Integer userId);

	/**
	 * 查询帐号和手机号是否存在
	 *
	 * @param userManagerBean
	 * @return
	 */
	public AuthUserManagerBean getUserIsExist(AuthUserManagerBean userManagerBean);

	/**
	 *
	 * @param userManagerBean
	 * @return
	 */
	boolean addTeacherManager(AuthUserManagerBean userManagerBean);

	/**
	 * 批量导入教师信息
	 * @param teacherInfoList
	 * @param schoolId
	 * @return
	 */
    boolean batchSaveTeacher(List<String[]> teacherInfoList, Integer schoolId) throws Exception;

	/**
	 * 根据手机号查询用户
	 *
	 * @param userPhone
	 * @return
	 */
	public AuthUserManagerBean getUserByUserPhone(String userPhone);

	/**
	 * 批量更新用户account（账号）信息
	 *
	 * @param list
	 * @return
	 */
	public boolean batchUpdateManagerAccount(List<AuthUserManagerBean> list);


	/**
	 * 后台 修改老师年级，学科，班级信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	public boolean updateTeacherManagerGroup(AuthUserManagerBean userManagerBean);

	/**
	 * 校长端 查询校长学校，学段信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	public AuthUserManagerBean getPrincipalInfo(AuthUserManagerBean userManagerBean) throws Exception;

	/**
	 * 后台 删除老师年级，学科，班级信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	public boolean delTeacherManagerGroup(AuthUserManagerBean userManagerBean) throws Exception;

	void batchSaveStudentInfo(List<String[]> studentInfoList) throws Exception;
}
