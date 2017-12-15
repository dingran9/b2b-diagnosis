package com.eedu.auth.service;

import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import com.eedu.auth.beans.enums.EnumBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/20
 * Time: 11:51
 * Describe:
 */
public interface AuthUserManagerService {

	/**
	 * 添加管理者信息
	 * @param userManagerBean
	 * @return
	 */
	int addUserManager(AuthUserManagerBean userManagerBean);

	List<AuthUserManagerBean> batchSaveUserManager(List<AuthUserManagerBean> list);

	/**
	 * 删除管理者信息
	 * @param userId
	 * @return
	 */
	int delUserManager(Integer userId);

	/**
	 * 修改管理者信息
	 * @param managerBean
	 * @return
	 */
	int updateUserManager(AuthUserManagerBean managerBean);

	/**
	 * 根据条件查询管理者列表
	 * @param managerBean
	 * @return
	 */
	List<AuthUserManagerBean> getUserManagerList(AuthUserManagerBean managerBean);

	/**
	 * 根据帐号和密码查找用户
	 * @param managerBean
	 * @return
	 */
	AuthUserManagerBean getUserByAccountAndPwd(AuthUserManagerBean managerBean);

	/**
	 * 根据手机号查询用户
	 * @param userPhone
	 * @return
	 */
	AuthUserManagerBean getUserByUserPhone(String userPhone);

	AuthUserManagerBean getUserByAccount(String account);


	/**
	 * 根据老师信息查询老师下面的班级信息
	 * @return
	 */
	List<AuthGroupBean> getClassByTeacherId(Integer teacherId);

	/**
	 * 根据班级获取学生信息
	 * @param classId
	 * @return
	 */
	List<AuthUserBean> getMyStudentByClassId(Integer classId);

	/**
	 * 根据userId集合查询
	 * @param maps key=userIdList
	 * @return
	 */
	List<AuthUserManagerBean> getUserByListId(Map<String,Object> maps);

	/**
	 * 根据用户Id查询用户
	 * @return
	 */
	AuthUserManagerBean getUserByUserId(Integer userId);

	/**
	 * 根据班级和学科查询老师
	 * @param gradeId 班级id
	 * @param subjectId 学科id
	 * @return
	 */
	AuthUserManagerBean getUserByGradeIdBySubjectId(Integer gradeId,Integer subjectId);
	/**
	 * 查询帐号和手机号是否存在
	 * @param userManagerBean
	 * @return
	 */
	AuthUserManagerBean getUserIsExist(AuthUserManagerBean userManagerBean);

	/**
	 * 获取老师个人信息
	 * @param userManagerBean
	 * @return
	 */
	AuthUserManagerBean getTeacherInfo(AuthUserManagerBean userManagerBean);
	/**
	 * 添加老师
	 * @param userManagerBean
	 * @return
	 */
	boolean addTeacherManager(AuthUserManagerBean userManagerBean);

	/**
	 * 后端管理  教师列表
	 * @param conditionBean
	 * @return
	 */
	PageInfo<AuthUserManagerConditionBean> getUserManagerListByCondition(AuthUserManagerConditionBean conditionBean);

	/**
	 * 权限管理-管理用户-用户禁用，启用
	 *
	 * @param authUserManagerBean
	 * @return
	 */
	public int updateUserManagerStatus(AuthUserManagerBean authUserManagerBean);

	/**
	 * 一期  权限管理  用户登录获取左侧菜单列表(资源列表  一期只获取第一级)
	 * @param authUserManagerBean
	 * @return
	 */
	public List<EnumBean> getUserManagerResourceList(AuthUserManagerBean authUserManagerBean);


	/**
	 * 二期  权限管理  用户登录获取左侧菜单列表(资源列表)
	 *
	 * @param authUserManagerBean
	 * @return
	 */
	public List<EnumBean> getUserManagerResourceByUserManager(AuthUserManagerBean authUserManagerBean);

	/**
	 * 批量更新用户account(账号)信息
	 *
	 * @param authUserManagerBeans
	 * @return
	 */
	public boolean batchUpdateManagerAccount(List<AuthUserManagerBean> authUserManagerBeans);

	/**
	 * 修改老师绑定的年级，学科，班级信息
	 *
	 * @param userManagerBean
	 * @return
	 */
	public boolean updateTeacherManagerGroup(AuthUserManagerBean userManagerBean);

	/**
	 * 删除教师信息
	 *
	 * @param authUserManagerBean
	 * @return
	 */
	public int delTeacherManager(AuthUserManagerBean authUserManagerBean);

	/**
	 * 校长端，查询校长学校，学段信息
	 * @param condition
	 * @return
	 */
	public AuthUserManagerBean getPrincipalInfo(AuthUserManagerBean condition)throws Exception;

	/**
	 * 根据条件分页查询管理者列表
	 *
	 * @param managerBean
	 * @return
	 */
	public PageInfo<AuthUserManagerBean> getUserManagerPage(AuthUserManagerBean managerBean);
}
