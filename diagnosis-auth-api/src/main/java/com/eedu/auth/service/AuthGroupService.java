package com.eedu.auth.service;

import com.eedu.auth.beans.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/16
 * Time: 20:41
 * Describe:
 */
public interface AuthGroupService {
	/**
	 * 添加组织信息
	 * @param groupBean
	 * @return
	 */
	int addGroupInfo(AuthGroupBean groupBean);

	/**
	 * 根据Id删除组织信息
	 * @param groupId
	 * @return
	 */
	int delGroupInfoById(String groupId);

	/**
	 * 修改组织信息
	 * @param groupBean
	 * @return
	 */
	int updateGroupInfoById(AuthGroupBean groupBean);

	/**
	 * 根据条件查询组织信息
	 * @param groupBean
	 * @return
	 */
	List<AuthGroupBean> getGroupByCondition(AuthGroupBean groupBean);

	/**
	 * 根据ID查询组织信息
	 * @return
	 */
	AuthGroupBean getGroupInfoById(Integer groupId);

	/**
	 * 根据班级idlist，查询班级下面的学生
	 * @param idLists
	 * @return
	 */
	List<AuthUserBean> getStudentByClassLists(List<Integer> idLists);

	/**
	 * 查询学校下的机构
	 * @param groupBean
	 * @return
	 */
	List<AuthGroupBean> getGroupByParent(AuthGroupBean groupBean);

	/**
	 * 根据学校查询学校下绑定的学科
	 * @param schoolId gradeId
	 * @return
	 */
	AuthGroupBean getSubjectBySchoolGrade(Integer schoolId,Integer gradeIden);

	/**
	 * 根据年级查询绑定的学科
	 * @return
	 */
	List<AuthSubjectBean> getSubjectByGradeId(AuthGroupBean gradeCondition);
	/**
	 * 添加学校
	 * @param condition
	 * @return
	 */
	boolean addSchoolInfo(AuthGroupBean condition);

	/**
	 * 产品后台管理  学校管理列表
	 *
	 * @param groupBean
	 * @return
	 */
	PageInfo<AuthGroupBean> schoolManagerList(AuthGroupBean groupBean);

	/**
	 * 产品后台管理  学校管理  管理教材版本
	 * @param maps
	 * @return
	 */
	List<AuthSubjectBean> managerMaterial(Map<String,Object> maps);

	/**
	 * 产品后台管理  学校管理  修改教材版本
	 * @param maps
	 * @return
	 */
	boolean updateManagerMaterial(Map<String, Object> maps);

	/**
	 * 产品后台管理  学校管理  添加教材版本
	 * @param maps
	 * @return
	 */
	boolean addManagerMaterial(Map<String, Object> maps);

	/**
	 * 产品后台管理  学校管理  删除教材版本
	 * @param maps
	 * @return
	 */
	boolean deleteManagerMaterial(Map<String, Object> maps);

	/**
	 * 根据学校和学校下的年级查询班级
	 * @param schoolId 学校Id
	 * @param gradeIden 年级标识
	 * @return
	 */
	List<AuthGroupBean> getClassBySchoolGrade(Integer schoolId,Integer gradeIden);

	/**
	 * 添加班级信息
	 * @param map
	 * @return
	 */
	boolean addClassInfo(Map<String,Object> map);
	/**
	 * 校长后台管理  用户管理----班级管理列表
	 * @param maps
	 * @return
	 */
	PageInfo<AuthGradeBean> classManagerList(Map<String,Object> maps);

	/**
	 * 根据学校ID，年级ID，学科ID获取教材版本
	 *
	 * @param schoolId    学校Id
	 * @param gradeId 年级标识
	 * @param subjectId 学科标识
	 * @return
	 */
	public AuthSubjectBean getmaterialVersion(Integer schoolId, Integer gradeId,Integer subjectId);

	/**
	 * 验证学校，年级，所有学科下有没有未绑定的老师的班级
	 *
	 * @param schoolId  学校Id
	 * @param gradeId   年级标识
	 * @param groupIden 学科标识
	 * @param subjectIdns 学科标识
	 * @return
	 */
	public boolean subjectNoClass(Integer schoolId,Integer gradeId,Integer groupIden,Integer[] subjectIdns) throws Exception;

	/**
	 * 根据班级名称查询班级 用于验重
	 * @param groupBean
	 * @return
	 */
	public List<AuthGroupBean> getClassInfoByName(AuthGroupBean groupBean) throws Exception;

	/**
	 * 根据id组查询组织信息
	 * @param ids
	 * @return
	 */
	public List<AuthGroupBean> getGroupByIds(List<Integer> ids)throws Exception;

	}
