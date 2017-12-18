package com.eedu.diagnosis.manager.service.impl;

import com.eedu.auth.beans.AuthClassBean;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserGroupBindBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.auth.service.AuthUserGroupBindService;
import com.eedu.auth.service.AuthUserService;
import com.eedu.diagnosis.common.utils.Order;
import com.eedu.diagnosis.manager.service.AuthStudentService;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 19:54
 * Describe:
 */
@Service
public class AuthStudentServiceImpl implements AuthStudentService {

	@Autowired
	private AuthUserService userService;
	@Autowired
	private AuthGroupService groupService;
	@Autowired
	private AuthUserGroupBindService userGroupBindService;

	/**
	 * 学生注册
	 * @param condition
	 * @return
	 */
	@Override
	public boolean stuRegister(AuthUserBean condition) throws Exception{

		condition.setUserType(ConstantEnum.USER_TYPE_STUDENT.getType());
		//md5加密
//		condition.setUserPassword(Digest.md5Digest(condition.getUserPassword()));

		return userService.addUserInfo(condition) > 0;
	}

	/**
	 * 查找用户
	 *
	 * @param userBean
	 * @return
	 */
	@Override
	public List<AuthUserBean> getUserBean(AuthUserBean userBean)throws Exception {
		return userService.getUserByCondition(userBean);
	}

	/**
	 * 修改用户
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public boolean updateStuInfo(AuthUserBean condition)throws Exception {

		return userService.updateUserInfo(condition) > 0;
	}

	/**
	 * 后台学生管理列表
	 *
	 * @param condition
	 */
	@Override
	public List<AuthUserBean> stuManager(AuthUserBean condition)throws Exception {
		List<AuthUserBean> userBeanList = userService.getUserByCondition(condition);
		if(CollectionUtils.isEmpty(userBeanList)){
			return null;
		}
		for(AuthUserBean userBean : userBeanList){
			//查询学校
			if(null != condition && condition.getSchoolId() != null && condition.getSchoolId() > 0){
				AuthGroupBean groupBean = groupService.getGroupInfoById(condition.getSchoolId());
				userBean.setSchoolName(groupBean.getGroupName());
			}
			//查询年级
			if(null != condition && condition.getGradeId() != null && condition.getGradeId() > 0){
				AuthGroupBean groupBean = groupService.getGroupInfoById(condition.getGradeId());
				userBean.setGradeName(groupBean.getGroupName());
			}
			//查询班级
			if(null != condition && condition.getClassId() != null && condition.getClassId() > 0){
				AuthGroupBean groupBean = groupService.getGroupInfoById(condition.getClassId());
				userBean.setClassName(groupBean.getGroupName());
			}
		}
		return userBeanList;
	}

	/**
	 * 获取个人信息
	 *
	 * @param condition
	 * @return
	 */
	@Override
	public AuthUserBean getStudentInfo(AuthUserBean condition)throws Exception {

		return userService.getStudentInfo(condition);
	}

	@Override
	public PageInfo<AuthUserBean> getStudentInfoBySchoolAndGrade(AuthUserBean condition, Integer pageNum, Integer pageSize) throws Exception{
		return userService.getUserBySchoolAndGrade(condition, pageNum, pageSize);
	}
}
