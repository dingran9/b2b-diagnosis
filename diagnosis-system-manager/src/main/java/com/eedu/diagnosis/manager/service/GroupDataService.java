package com.eedu.diagnosis.manager.service;

import com.eedu.auth.beans.AuthGradeBean;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthSubjectBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 18:28
 * Describe:
 */
public interface GroupDataService {

	/**
	 * 添加组织信息
	 *
	 * @param groupBean
	 * @return
	 */
	boolean addGroupInfo(AuthGroupBean groupBean);

	/**
	 * 根据Id删除组织信息
	 *
	 * @param groupId
	 * @return
	 */
	int delGroupInfoById(String groupId);

	/**
	 * 修改组织信息
	 *
	 * @param groupBean
	 * @return
	 */
	int updateGroupInfoById(AuthGroupBean groupBean);

	/**
	 * 根据条件查询组织信息
	 *
	 * @param groupBean
	 * @return
	 */
	List<AuthGroupBean> getGroupByCondition(AuthGroupBean groupBean);

	/**
	 * 学校管理功能列表
	 *
	 * @return
	 */
	List<AuthGroupBean> schoolManager(AuthGroupBean groupBean);

	/**
	 * 根据条件查询子标签
	 *
	 * @param groupBean
	 * @return
	 */
	List<AuthGroupBean> getGroupByParent(AuthGroupBean groupBean);

	/**
	 * 根据年级查询绑定的学科
	 * @return
	 */
	List<AuthSubjectBean> getSubjectByGradeId(AuthGroupBean gradeCondition);

	/**
	 * 添加学校信息
	 *
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
	boolean updateManagerMaterial(Map<String,Object> maps);

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
	 * 添加班级信息
	 *
	 * @param map
	 * @return
	 */
	boolean addClassInfo(Map<String, Object> map);

	/**
	 * 校长后台管理  用户管理----班级管理列表
	 *
	 * @param maps
	 * @return
	 */
	PageInfo<AuthGradeBean> classManagerList(Map<String, Object> maps);

	List<AuthGroupBean> getSchoolByStageAndArea(Map<String, Object> map);

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
	 * 查询学校所绑定的学科
	 *
	 * @param groupArts
	 * @param gradeId
	 * @return
	 */
	public List<AuthSubjectBean> getSchoolSubjectList(String groupArts,Integer gradeId) throws Exception;

	/**
	 * 按班级名称查询班级，用于排重
	 *
	 * @param groupBean
	 * @return
	 */
	public List<AuthGroupBean> getClassInfoByName(AuthGroupBean groupBean) throws Exception;





	boolean addBaseSchoolInfo(Map<String, Object> map);
}
