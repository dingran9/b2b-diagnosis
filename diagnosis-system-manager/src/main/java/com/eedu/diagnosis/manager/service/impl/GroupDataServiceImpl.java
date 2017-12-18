package com.eedu.diagnosis.manager.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGradeBean;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthSubjectBean;
import com.eedu.auth.beans.AuthUserGroupBindBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.auth.service.AuthDataDictionaryService;
import com.eedu.auth.service.AuthGroupService;
import com.eedu.auth.service.AuthUserGroupBindService;
import com.eedu.auth.service.AuthUserManagerService;
import com.eedu.diagnosis.manager.service.GroupDataService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 18:28
 * Describe:
 */
@Service
@Slf4j
public class GroupDataServiceImpl implements GroupDataService {

    @Autowired
    private AuthGroupService groupService;
    @Autowired
    private AuthUserGroupBindService userGroupBindService;
    @Autowired
    private AuthUserManagerService userManagerService;
    @Autowired
    private AuthDataDictionaryService dataDictionaryService;

    /**
     * 添加组织信息
     *
     * @param groupBean
     * @return
     */
    @Override
    public boolean addGroupInfo(AuthGroupBean groupBean) {

        return groupService.addGroupInfo(groupBean) > 0;
    }

    /**
     * 根据Id删除组织信息
     *
     * @param groupId
     * @return
     */
    @Override
    public int delGroupInfoById(String groupId) {
        return groupService.delGroupInfoById(groupId);
    }

    /**
     * 修改组织信息
     *
     * @param groupBean
     * @return
     */
    @Override
    public int updateGroupInfoById(AuthGroupBean groupBean) {
        return groupService.updateGroupInfoById(groupBean);
    }

    /**
     * 根据条件查询组织信息
     *
     * @param groupBean
     * @return
     */
    @Override
    public List<AuthGroupBean> getGroupByCondition(AuthGroupBean groupBean) {
        return groupService.getGroupByCondition(groupBean);
    }

    /**
     * 学校管理功能列表
     *
     * @return
     */
    @Override
    public List<AuthGroupBean> schoolManager(AuthGroupBean groupBean) {
        //查询所有学校
        List<AuthGroupBean> groupBeanList = groupService.getGroupByCondition(groupBean);
        if (CollectionUtils.isEmpty(groupBeanList)) {
            return null;
        }
        //查询校长
        for (AuthGroupBean group : groupBeanList) {
            AuthUserGroupBindBean userGroupBindBean = new AuthUserGroupBindBean();
            userGroupBindBean.setGroupId(group.getGroupId());
            userGroupBindBean.setGroupType(group.getGroupType());
            userGroupBindBean.setUserType(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType());
            List<AuthUserGroupBindBean> userGroupBindBeanList = userGroupBindService.getUserGroupBindByCondition(userGroupBindBean);
            if (!CollectionUtils.isEmpty(userGroupBindBeanList)) {
                AuthUserGroupBindBean userBean = userGroupBindBeanList.get(0);
                group.setSchoolmaster(userManagerService.getUserByUserId(userBean.getUserId()));
            }

        }
        return groupBeanList;
    }

    /**
     * 根据条件查询子标签
     *
     * @param groupBean
     * @return
     */
    @Override
    public List<AuthGroupBean> getGroupByParent(AuthGroupBean groupBean) {
        return groupService.getGroupByCondition(groupBean);
    }

    /**
     * 根据年级查询绑定的学科
     *
     * @param gradeCondition
     * @return
     */
    @Override
    public List<AuthSubjectBean> getSubjectByGradeId(AuthGroupBean gradeCondition) {
        return groupService.getSubjectByGradeId(gradeCondition);
    }

    /**
     * 添加学校信息
     *
     * @param condition
     * @return
     */
    @Override
    public boolean addSchoolInfo(AuthGroupBean condition) {

        return groupService.addSchoolInfo(condition);
    }

    /**
     * 产品后台管理  学校管理列表
     *
     * @param groupBean
     * @return
     */
    @Override
    public PageInfo<AuthGroupBean> schoolManagerList(AuthGroupBean groupBean) {
        return groupService.schoolManagerList(groupBean);
    }

    /**
     * 产品后台管理  学校管理  管理教材版本
     *
     * @param maps
     * @return
     */
    @Override
    public List<AuthSubjectBean> managerMaterial(Map<String, Object> maps) {
        return groupService.managerMaterial(maps);
    }

    /**
     * 产品后台管理  学校管理  修改教材版本
     *
     * @param maps
     * @return
     */
    @Override
    public boolean updateManagerMaterial(Map<String, Object> maps) {
        return groupService.updateManagerMaterial(maps);
    }

    /**
     * 产品后台管理  学校管理  添加教材版本
     *
     * @param maps
     * @return
     */
    @Override
    public boolean addManagerMaterial(Map<String, Object> maps) {
        return groupService.addManagerMaterial(maps);
    }

    /**
     * 产品后台管理  学校管理  删除教材版本
     *
     * @param maps
     * @return
     */
    @Override
    public boolean deleteManagerMaterial(Map<String, Object> maps) {
        return groupService.deleteManagerMaterial(maps);
    }

    /**
     * 添加班级信息
     *
     * @param map
     * @return
     */
    @Override
    public boolean addClassInfo(Map<String, Object> map) {
        return groupService.addClassInfo(map);
    }

    /**
     * 校长后台管理  用户管理----班级管理列表
     *
     * @param maps
     * @return
     */
    @Override
    public PageInfo<AuthGradeBean> classManagerList(Map<String, Object> maps) {
        return groupService.classManagerList(maps);
    }

    @Override
    public List<AuthGroupBean> getSchoolByStageAndArea(Map<String, Object> map) {
        AuthGroupBean groupBean = new AuthGroupBean();
        groupBean.setGroupPeriod((List<String>) map.get("groupPeriod"));
        if (null != map.get("groupAreaCityId") && !"".equals(map.get("groupAreaCityId"))) {
            groupBean.setGroupAreaCityId(Integer.parseInt(map.get("groupAreaCityId").toString()));
        }
        if (null != map.get("groupAreaDistrictId") && !"".equals(map.get("groupAreaDistrictId"))) {
            groupBean.setGroupAreaDistrictId(Integer.parseInt(map.get("groupAreaDistrictId").toString()));
        }
        if (null != map.get("groupAreaProvinceId") && !"".equals(map.get("groupAreaProvinceId"))) {
            groupBean.setGroupAreaProvinceId(Integer.parseInt(map.get("groupAreaProvinceId").toString()));
        }
        groupBean.setGroupType(1);
        List<AuthGroupBean> authGroupBeanList = groupService.getGroupByCondition(groupBean);
        if (authGroupBeanList != null && authGroupBeanList.size() != 0) {
            for (int i = 0; i < authGroupBeanList.size() - 1; i++) {
                for (int j = authGroupBeanList.size() - 1; j > i; j--) {
                    if (authGroupBeanList.get(j).getGroupId().equals(authGroupBeanList.get(i).getGroupId())) {
                        authGroupBeanList.remove(j);
                    }
                }
            }
        }
        return authGroupBeanList;
    }


    /**
     * 根据学校ID，年级ID，学科ID获取教材版本
     *
     * @param schoolId  学校Id
     * @param gradeId   年级标识
     * @param subjectId 学科标识
     * @return
     */
    public AuthSubjectBean getmaterialVersion(Integer schoolId, Integer gradeId, Integer subjectId) {

        return groupService.getmaterialVersion(schoolId, gradeId, subjectId);
    }

    /**
     * 查询学校所绑定的学科
     *
     * @param groupArts
     * @param gradeId
     * @return
     */
    @Override
    public List<AuthSubjectBean> getSchoolSubjectList(String groupArts, Integer gradeId) throws Exception {
        List<AuthSubjectBean> subjectBeans = new ArrayList<>();
        AuthGroupBean authGroupBean = new AuthGroupBean();
        authGroupBean.setGroupId(gradeId);
        authGroupBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
        List<AuthGroupBean> list = groupService.getGroupByCondition(authGroupBean);
        if (!CollectionUtils.isEmpty(list)) {
            Integer[] science = {4, 5, 6};
            Integer[] liberal = {7, 8, 9};
            AuthGroupBean groupBean = list.get(0);
            subjectBeans = JSONObject.parseArray(groupBean.getGroupMaterial(), AuthSubjectBean.class);
            String[] subjects = groupArts.split(",");
            if (subjects.length > 2) return new ArrayList<AuthSubjectBean>();
            else if (subjects.length == 2) {
                if (!groupArts.equals("0,1") && !groupArts.equals("1,0")) return new ArrayList<AuthSubjectBean>();
                else return subjectBeans;
            } else if (groupArts.equals(ConstantEnum.GROUP_ART_NOTYPE.getType().toString())) return subjectBeans;
            else if (groupArts.equals(ConstantEnum.GROUP_ART_LIBERAL.getType().toString())) {
                return removeSubjectList(subjectBeans, science);
            } else if (groupArts.equals(ConstantEnum.GROUP_ART_SCIENCE.getType().toString())) {
                return removeSubjectList(subjectBeans, liberal);
            } else {
                return new ArrayList<AuthSubjectBean>();
            }
        }

        return subjectBeans;
    }

    //将学科移除
    public List<AuthSubjectBean> removeSubjectList(List<AuthSubjectBean> list, Integer[] subjects) {

        Iterator it = list.iterator();
        while (it.hasNext()) {
            AuthSubjectBean sub = (AuthSubjectBean) it.next();
            for (Integer subjectId : subjects) {
                if (sub.getSubjectIden() == subjectId) {
                    it.remove(); //移除该对象
                }
            }
        }
        return list;
    }

    /**
     * 按班级名称查询班级，用于排重
     *
     * @param groupBean
     * @return
     */
    @Override
    public List<AuthGroupBean> getClassInfoByName(AuthGroupBean groupBean) throws Exception{

        return groupService.getClassInfoByName(groupBean);
    }


    /**重写添加学校 **/
    @Override
    public boolean addBaseSchoolInfo(Map<String, Object> map) {
        boolean    b = addBaseSchool(map);
        return  b;
    }
    public boolean addBaseSchool(Map<String, Object> map) {
        JSONArray periodArray = JSON.parseArray(map.get("groupPeriod").toString());
        List<String> periodBeanList = getList(periodArray);
        JSONArray gradeArray = JSON.parseArray(map.get("childGroupBeanList").toString());
        List<AuthGroupBean> gradeBeanList = getListByAuthGroup(gradeArray );
        AuthGroupBean condition = new AuthGroupBean();
        condition.setGroupName(map.get("groupName").toString());
        condition.setGroupAddress(map.get("groupAddress").toString());
        condition.getGroupPeriod().addAll(periodBeanList);
        condition.getChildGroupBeanList().addAll(gradeBeanList);
        condition = getModel(condition, map);
        condition.setGroupExternalSchoolId(Integer.parseInt(map.get("groupExternalSchoolId").toString()));
        return groupService.addSchoolInfo(condition);
    }
    private  List<String>  getList(JSONArray periodArray){
        List<String> periodBeanList = new ArrayList<>();
        Iterator<Object> iterPeriod = periodArray.iterator();
        while (iterPeriod.hasNext()) {
            String a = (String) iterPeriod.next();
            periodBeanList.add(a);
        }
        return  periodBeanList;
    }
    private  List<AuthGroupBean>   getListByAuthGroup(JSONArray gradeArray ){
        List<AuthGroupBean> gradeBeanList = new ArrayList<>();
        Iterator<Object> iter = gradeArray.iterator();
        while (iter.hasNext()) {
            JSONObject a = (JSONObject) iter.next();
            AuthGroupBean gradebean = null;
            if (a.getInteger("groupIden") != null && !StringUtils.isBlank(a.getString("groupMaterial"))) {
                gradebean = new AuthGroupBean();
                gradebean.setGroupIden(a.getInteger("groupIden"));
                gradebean.setGroupMaterial(a.getString("groupMaterial"));
                gradeBeanList.add(gradebean);
            }
        }
        return  gradeBeanList;
    }
    private AuthGroupBean  getModel( AuthGroupBean condition,Map<String, Object> map){
        if (map.containsKey("groupAreaProvinceId") && map.containsKey("groupAreaProvinceName")) {
            condition.setGroupAreaProvinceId(Integer.parseInt(map.get("groupAreaProvinceId").toString()));
            condition.setGroupAreaProvinceName(map.get("groupAreaProvinceName").toString());
        }
        if (map.containsKey("groupAreaCityId") && map.containsKey("groupAreaCityName")) {
            condition.setGroupAreaCityId(Integer.parseInt(map.get("groupAreaCityId").toString()));
            condition.setGroupAreaCityName(map.get("groupAreaCityName").toString());
        }
        if (map.containsKey("groupAreaDistrictId") && map.containsKey("groupAreaDistrictName")) {
            condition.setGroupAreaDistrictId(Integer.parseInt(map.get("groupAreaDistrictId").toString()));
            condition.setGroupAreaDistrictName(map.get("groupAreaDistrictName").toString());
        }
        return  condition;
    }

}
