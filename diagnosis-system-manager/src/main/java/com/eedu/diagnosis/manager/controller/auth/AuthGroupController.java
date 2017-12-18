package com.eedu.diagnosis.manager.controller.auth;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.*;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthTeacherService;
import com.eedu.diagnosis.manager.service.GroupDataService;
import com.evaluate.base.data.api.service.BaseSchoolService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 9:33
 * Describe:
 */
@RestController
@RequestMapping("/groupManager")
public class AuthGroupController {

    private final Logger logger = LoggerFactory.getLogger(AuthGroupController.class);
    @Autowired
    private GroupDataService groupDataService;
    @Autowired
    private AuthTeacherService teacherService;
    @Autowired
    private BaseSchoolService baseSchoolService;
    /**
     * 学校管理功能列表
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/schoolManager", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse schoolManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            //组装条件，查询学校
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            condition.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
            List<AuthGroupBean> groupBeanList = groupDataService.schoolManager(condition);
            if (CollectionUtils.isEmpty(groupBeanList)) {
                logger.error("AuthGroupController.schoolManager error,获取数据失败");
                return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
            }
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("AuthGroupController.schoolManager error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 查询所有学校
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSchoolList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getSchoolList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            //组装条件，查询学校
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            condition.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
            List<AuthGroupBean> groupBeanList = groupDataService.getGroupByCondition(condition);
            if (CollectionUtils.isEmpty(groupBeanList)) {
                logger.error("AuthGroupController.getSchoolList error,获取数据失败");
                return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
            }
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("AuthGroupController.getSchoolList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询学校下的所有年级
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGradeList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getGradeList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            //组装条件，查询年级
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            if (null == condition || null == condition.getGroupId() || condition.getGroupId() <= 0) {
                logger.error("groupId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupId");
            }
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(condition.getGroupId());
            groupBean.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
            List<AuthGroupBean> groupBeanList = groupDataService.getGroupByParent(groupBean);
            if (CollectionUtils.isEmpty(groupBeanList)) {
                logger.error("AuthGroupController.getSchoolList error,获取数据失败");
                return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
            }
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("AuthGroupController.getGradeList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询年级下的所有班级
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClassList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getClassList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            if (condition == null || condition.getGroupId() == null || condition.getGroupId() <= 0) {
                logger.error("groupId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupId");
            }
            AuthGroupBean grade = new AuthGroupBean();
            grade.setGroupId(condition.getGroupId());
            List<AuthGroupBean> gradeList = groupDataService.getGroupByCondition(grade);
            if (CollectionUtils.isEmpty(gradeList)) {
                logger.error("groupId is not exist");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupId is not exist");
            }
            AuthGroupBean bean = gradeList.get(0);
            if(null != bean.getGroupIden() && !bean.getGroupIden().equals(0)){
                if(bean.getGroupIden().equals(32) ||  bean.getGroupIden().equals(33)){
                    if (condition == null || condition.getGroupArt() == null ) {
                        logger.error("groupArt is null or error" + baseResponse.getRequestId());
                        return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupArt");
                    }
                }
            }
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(condition.getGroupId());
            groupBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
            if (condition != null && condition.getGroupArt() != null ) {
                groupBean.setGroupArt(condition.getGroupArt());
            }
            List<AuthGroupBean> groupBeanList = groupDataService.getGroupByParent(groupBean);

            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getClassList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 查询年级绑定的学科
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubjectByGradeId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getSubjectByGradeId(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            if (condition == null || condition.getGroupIden() == null || condition.getGroupIden() <= 0) {
                logger.error("groupIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupIden");
            }
            if (condition == null || condition.getGroupParentId() == null || condition.getGroupParentId() <= 0) {
                logger.error("groupParentId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupParentId");
            }
            AuthGroupBean grade = new AuthGroupBean();
            grade.setGroupIden(condition.getGroupIden());
            grade.setGroupParentId(condition.getGroupParentId());
            List<AuthGroupBean> gradeList = groupDataService.getGroupByCondition(grade);
            if (CollectionUtils.isEmpty(gradeList)) {
                logger.error("groupId is not exist");
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "groupId is not exist");
            }

            List<AuthSubjectBean> subjectBeanList = groupDataService.getSubjectByGradeId(gradeList.get(0));

            baseResponse.setResult(subjectBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getSubjectByGradeId error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 根据名称模糊查询组织信息
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getGroupByNameAndType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getGroupByNameAndType(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);
            AuthGroupBean groupBean = new AuthGroupBean();
            if (!ConstantEnum.GROUP_TYPE_SCHOOL.getType().equals(condition.getGroupType())) {
                if (condition == null || condition.getGroupParentId() == null || condition.getGroupParentId() <= 0) {
                    logger.error("groupParentId is null or error" + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupParentId");
                }
                groupBean.setGroupParentId(condition.getGroupParentId());
            }

            if (condition == null || StringUtils.isBlank(condition.getGroupName())) {
                logger.error("groupName is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupName");
            }
            if (condition == null || null == condition.getGroupType() || condition.getGroupType() < 0) {
                logger.error("groupType is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupType");
            }

            groupBean.setGroupType(condition.getGroupType());
            groupBean.setGroupName(condition.getGroupName());
            List<AuthGroupBean> groupBeanList = groupDataService.getGroupByParent(groupBean);
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 产品后台管理  添加学校
     *
     * @param requestId
     * @param requestBody
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/addSchoolInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
//    public BaseResponse addSchoolInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
//        BaseResponse baseResponse = new BaseResponse(requestId);
//        try {
//            Map<String, Object> map = JSONObject.parseObject(requestBody);
//            if (map == null || null == map.get("groupAddress")) {
//                logger.error("groupAddress is null or error" + baseResponse.getRequestId());
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupAddress is null");
//            }
//            if (map == null || null == map.get("groupName")) {
//                logger.error("groupName is null or error" + baseResponse.getRequestId());
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupName is null");
//            }
//            if (map == null || null == map.get("groupPeriod")) {
//                logger.error("groupPeriod is null or error" + baseResponse.getRequestId());
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupPeriod is null");
//            }
//            if (map == null || null == map.get("childGroupBeanList")) {
//                logger.error("childGroupBeanList is null or error" + baseResponse.getRequestId());
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "childGroupBeanList is null");
//            }
//            if (map == null || null == map.get("groupExternalSchoolId")) {
//                logger.error("groupExternalSchoolId is null or error" + baseResponse.getRequestId());
//                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupExternalSchoolId is null");
//            }
//            JSONArray periodArray = JSON.parseArray(map.get("groupPeriod").toString());
//
//            List<String> periodBeanList = new ArrayList<>();
//            Iterator<Object> iterPeriod = periodArray.iterator();
//            while (iterPeriod.hasNext()) {
//                String a = (String) iterPeriod.next();
//                periodBeanList.add(a);
//            }
//
//            JSONArray gradeArray = JSON.parseArray(map.get("childGroupBeanList").toString());
//
//            List<AuthGroupBean> gradeBeanList = new ArrayList<>();
//            Iterator<Object> iter = gradeArray.iterator();
//            while (iter.hasNext()) {
//                JSONObject a = (JSONObject) iter.next();
//                AuthGroupBean gradebean = null;
//                if (a.getInteger("groupIden") != null && !StringUtils.isBlank(a.getString("groupMaterial"))) {
//                    gradebean = new AuthGroupBean();
//                    gradebean.setGroupIden(a.getInteger("groupIden"));
//                    gradebean.setGroupMaterial(a.getString("groupMaterial"));
//                    gradeBeanList.add(gradebean);
//                }
//            }
//
//            AuthGroupBean condition = new AuthGroupBean();
//            condition.setGroupName(map.get("groupName").toString());
//            condition.setGroupAddress(map.get("groupAddress").toString());
//            condition.getGroupPeriod().addAll(periodBeanList);
//            condition.getChildGroupBeanList().addAll(gradeBeanList);
//            if (map.containsKey("groupAreaProvinceId") && map.containsKey("groupAreaProvinceName")) {
//                condition.setGroupAreaProvinceId(Integer.parseInt(map.get("groupAreaProvinceId").toString()));
//                condition.setGroupAreaProvinceName(map.get("groupAreaProvinceName").toString());
//            }
//            if (map.containsKey("groupAreaCityId") && map.containsKey("groupAreaCityName")) {
//                condition.setGroupAreaCityId(Integer.parseInt(map.get("groupAreaCityId").toString()));
//                condition.setGroupAreaCityName(map.get("groupAreaCityName").toString());
//            }
//            if (map.containsKey("groupAreaDistrictId") && map.containsKey("groupAreaDistrictName")) {
//                condition.setGroupAreaDistrictId(Integer.parseInt(map.get("groupAreaDistrictId").toString()));
//                condition.setGroupAreaDistrictName(map.get("groupAreaDistrictName").toString());
//            }
//            condition.setGroupExternalSchoolId(Integer.parseInt(map.get("groupExternalSchoolId").toString()));
//            boolean bool = groupDataService.addSchoolInfo(condition);
//            baseResponse.setResult(bool);
//        } catch (Exception ex) {
//            logger.error("AuthGroupController.addSchoolInfo error ", ex);
//            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
//        }
//        return baseResponse;
//    }

    /**
     * 产品后台管理  学校管理列表
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/schoolManagerList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse schoolManagerList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            AuthGroupBean groupCondition = GsonUtils.getGson().fromJson(requestBody, AuthGroupBean.class);

            groupCondition.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
            PageInfo<AuthGroupBean> groupBeanList = groupDataService.schoolManagerList(groupCondition);
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 产品后台管理  学校管理  管理教材版本列表
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/managerMaterial", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse managerMaterial(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> maps = JSONObject.parseObject(requestBody);
            if (null == maps || !maps.containsKey("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            AuthGroupBean groupCondition = new AuthGroupBean();
            groupCondition.setGroupParentId(Integer.parseInt(maps.get("schoolId").toString()));
            groupCondition.setGroupType(ConstantEnum.GROUP_TYPE_GRADE.getType());
            List<AuthGroupBean> groupBeanLIst = groupDataService.getGroupByCondition(groupCondition);
            if (CollectionUtils.isEmpty(groupBeanLIst)) {
                logger.error("schoolId is not exist" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is not exist");
            }
            List<AuthSubjectBean> subjectBeanList = groupDataService.managerMaterial(maps);
            baseResponse.setResult(subjectBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 产品后台管理  学校管理  修改教材版本
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateManagerMaterial", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse updateManagerMaterial(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> maps = JSONObject.parseObject(requestBody);
            if (null == maps || !maps.containsKey("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == maps || !maps.containsKey("gradeIden")) {
                logger.error("gradeIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeIden is null");
            }
            if (null == maps || !maps.containsKey("subjectId")) {
                logger.error("subjectId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectId is null");
            }
            if (null == maps || !maps.containsKey("subjectIden")) {
                logger.error("subjectIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectIden is null");
            }
            if (null == maps || !maps.containsKey("updateToSubjectIden")) {
                logger.error("updateTosubjectIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "updateTosubjectIden is null");
            }
            if (null == maps || !maps.containsKey("updateToMaterialVersion")) {
                logger.error("updateToMaterialVersion is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "updateToMaterialVersion is null");
            }

            AuthGroupBean groupCondition = new AuthGroupBean();
            groupCondition.setGroupParentId(Integer.parseInt(maps.get("schoolId").toString()));
            groupCondition.setGroupIden(Integer.parseInt(maps.get("gradeIden").toString()));
            List<AuthGroupBean> groupBeanLIst = groupDataService.getGroupByCondition(groupCondition);
            if (CollectionUtils.isEmpty(groupBeanLIst)) {
                logger.error("schoolId is not exist" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is not exist");
            }
            boolean bool = groupDataService.updateManagerMaterial(maps);
            baseResponse.setResult(bool);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 产品后台管理  学校管理  添加教材版本
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addManagerMaterial", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse addManagerMaterial(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> maps = JSONObject.parseObject(requestBody);
            if (null == maps || !maps.containsKey("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == maps || !maps.containsKey("gradeIden")) {
                logger.error("gradeIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeIden is null");
            }
            if (null == maps || !maps.containsKey("subjectId")) {
                logger.error("subjectId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectId is null");
            }
            if (null == maps || !maps.containsKey("subjectIden")) {
                logger.error("subjectIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectIden is null");
            }
            if (null == maps || !maps.containsKey("addToMaterialVersion")) {
                logger.error("addToMaterialVersion is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "addToMaterialVersion is null");
            }

            AuthGroupBean groupCondition = new AuthGroupBean();
            groupCondition.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
            groupCondition.setGroupId(Integer.parseInt(maps.get("schoolId").toString()));
            List<AuthGroupBean> groupBeanLIst = groupDataService.getGroupByCondition(groupCondition);
            if (CollectionUtils.isEmpty(groupBeanLIst)) {
                logger.error("schoolId is not exist" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is not exist");
            }
            boolean bool = groupDataService.addManagerMaterial(maps);
            baseResponse.setResult(bool);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 产品后台管理  学校管理  删除教材版本
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteManagerMaterial", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse deleteManagerMaterial(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> maps = JSONObject.parseObject(requestBody);
            if (null == maps || !maps.containsKey("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == maps || !maps.containsKey("gradeIden")) {
                logger.error("gradeIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeIden is null");
            }
            if (null == maps || !maps.containsKey("subjectId")) {
                logger.error("subjectId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectId is null");
            }
            if (null == maps || !maps.containsKey("subjectIden")) {
                logger.error("subjectIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "subjectIden is null");
            }

            AuthGroupBean groupCondition = new AuthGroupBean();
            groupCondition.setGroupParentId(Integer.parseInt(maps.get("schoolId").toString()));
            groupCondition.setGroupIden(Integer.parseInt(maps.get("gradeIden").toString()));
            List<AuthGroupBean> groupBeanLIst = groupDataService.getGroupByCondition(groupCondition);
            if (CollectionUtils.isEmpty(groupBeanLIst)) {
                logger.error("schoolId is not exist" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is not exist");
            }
            boolean bool = groupDataService.deleteManagerMaterial(maps);
            baseResponse.setResult(bool);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 校长后台管理  添加班级
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addClassInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse addClassInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);
            if (null == map || null == map.get("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == map || null == map.get("gradeIden")) {
                logger.error("gradeIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeIden is null");
            }
            if (null == map || null == map.get("className")) {
                logger.error("className is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "className is null");
            }
            if (map.get("gradeIden").equals(32) || map.get("gradeIden").equals(33)) {
                if (null == map || null == map.get("groupArt")) {
                    logger.error("groupArt is null or error" + baseResponse.getRequestId());
                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupArt is null");
                }
            }

            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(Integer.parseInt(map.get("schoolId").toString()));
            groupBean.setGroupIden(Integer.parseInt(map.get("gradeIden").toString()));
            groupBean.setGroupName(map.get("className").toString());
            List<AuthGroupBean> list = groupDataService.getClassInfoByName(groupBean);
            if (!CollectionUtils.isEmpty(list)) {
                logger.error("className is duplicate" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(), "className is duplicate");
            }

            boolean bool = groupDataService.addClassInfo(map);
            baseResponse.setResult(bool);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 校长后台管理  用户管理----班级管理列表
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/classManagerList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse classManagerList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);
            if (null == map || !map.containsKey("schoolId") || null == map.get("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupId(Integer.parseInt(map.get("schoolId").toString()));
            List<AuthGroupBean> schoolList = groupDataService.getGroupByCondition(groupBean);
            if (CollectionUtils.isEmpty(schoolList)) {
                logger.error("schoolId is not exist " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is not exist ");
            }
            PageInfo<AuthGradeBean> groupBeanList = groupDataService.classManagerList(map);
            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

    /**
     * 根据地区和学段获取学校
     */
    @RequestMapping(value = "/getSchoolByStageAndArea", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public BaseResponse getSchoolByStageAndArea(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);
            if (map == null || null == map.get("groupPeriod")) {
                logger.error("groupPeriod is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupPeriod is null");
            }
            List<AuthGroupBean> authGroupBeanList = groupDataService.getSchoolByStageAndArea(map);
            baseResponse.setResult(authGroupBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 校长后台管理  用户管理----班级管理列表
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getmaterialVersion", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getmaterialVersion(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);
            if (null == map || !map.containsKey("schoolId") || null == map.get("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == map || !map.containsKey("gradeId") || null == map.get("gradeId")) {
                logger.error("gradeId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == map || !map.containsKey("subjectId") || null == map.get("subjectId")) {
                logger.error("subjectId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            baseResponse.setResult(groupDataService.getmaterialVersion(Integer.valueOf(String.valueOf(map.get("schoolId"))), Integer.valueOf(String.valueOf(map.get("gradeId"))), Integer.valueOf(String.valueOf(map.get("subjectId")))));
        } catch (Exception ex) {
            logger.error("AuthGroupController.getGroupByNameAndType error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 查询年级下的所有班级
     *
     * @param requestId
     * @param requestBody
     * @return  {"schoolId":61,"gradeId":62,"gradeIden":32,"subjectIden":1}
     */
    @ResponseBody
    @RequestMapping(value = "/getNoClassList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getNoClassList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);

            if (null == map || !map.containsKey("schoolId") || null == map.get("schoolId")) {
                logger.error("schoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == map || !map.containsKey("gradeId") || null == map.get("gradeId")) {
                logger.error("gradeId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == map || !map.containsKey("gradeIden") || null == map.get("gradeIden")) {
                logger.error("gradeIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
            if (null == map || !map.containsKey("subjectIden") || null == map.get("subjectIden")) {
                logger.error("subjectIden is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId is null");
            }
//            if(map.get("gradeIden").equals(32) ||  map.get("gradeIden").equals(33)){
//                if (!map.containsKey("groupArt")|| map.get("groupArt") == null ) {
//                    logger.error("groupArt is null or error" + baseResponse.getRequestId());
//                    return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupArt");
//                }
//            }
            AuthGroupBean groupBean = new AuthGroupBean();
            groupBean.setGroupParentId(Integer.valueOf(String.valueOf(map.get("gradeId"))));
            groupBean.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
//            if (map.get("groupArt") != null ) {
//                groupBean.setGroupArt(Integer.valueOf(String.valueOf(map.get("groupArt"))));
//            }
            List<AuthGroupBean> groupBeanList = groupDataService.getGroupByParent(groupBean);
            if(null != groupBeanList &&  groupBeanList.size() > 0) {
                AuthUserManagerConditionBean condition = new AuthUserManagerConditionBean();
                condition.setUserSchoolId(Integer.valueOf(String.valueOf(map.get("schoolId"))));
                condition.setUserSubjectIden(String.valueOf(map.get("subjectIden")));
                condition.setUserGradeIden(String.valueOf(map.get("gradeIden")));
//                condition.setUserSchoolId(Integer.valueOf(String.valueOf(map.get("groupArt"))));
                condition.setPageSize(Integer.MAX_VALUE);
                condition.setPageNum(1);
                PageInfo<AuthUserManagerConditionBean> page= teacherService.getTeacherManagerList(condition);
                if(null != page) {
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
                        List<AuthGroupBean> authGroupBeans = new ArrayList<>();
                        for (AuthGroupBean authGroupBean : groupBeanList) {
                            if (set.add(authGroupBean.getGroupId())) authGroupBeans.add(authGroupBean);
                        }
                        baseResponse.setResult(authGroupBeans);
                        return baseResponse;
                    }
                }
            }

            baseResponse.setResult(groupBeanList);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getNoClassList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


    /**
     * 查询学校所绑定的学科
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSchoolSubjectList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse getSchoolSubjectList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);

            if (null == map || !map.containsKey("gradeId") || null == map.get("gradeId")) {
                logger.error("gradeId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "gradeId is null");
            }
            if (null == map || !map.containsKey("groupArts") || null == map.get("groupArts")) {
                logger.error("groupArts is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupArts is null");
            }

            baseResponse.setResult(groupDataService.getSchoolSubjectList(String.valueOf(map.get("groupArts")),Integer.parseInt(map.get("gradeId")+"")));
        } catch (Exception ex) {
            logger.error("AuthGroupController.getNoClassList error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }

/************************************华丽丽分割线**************************************************/

    /**
     * 根据地区和学段获取学校
     */
    @RequestMapping(value = "/getBaseSchoolByStageAndArea", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public BaseResponse getBaseSchoolByStageAndArea(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            JSONObject jsonObject = JSONObject.parseObject(requestBody);
            if(null == jsonObject.getLong("pId")){
                logger.error("pId is null or error " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"pId is null or error");
            }
            if(null == jsonObject.getInteger("pageNum")){
                logger.error("pageNum is null or error " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"pageNum is null or error");
            }
            if(null == jsonObject.getInteger("pageSize")){
                logger.error("pageSize is null or error " + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"pageSize is null or error");
            }
            PageInfo pageInfo = baseSchoolService.getBaseSchoolByAreaCode(jsonObject.getLong("pId"), jsonObject.getInteger("pageNum"), jsonObject.getInteger("pageSize"));
            baseResponse.setResult(pageInfo);
        } catch (Exception ex) {
            logger.error("AuthGroupController.getBaseSchoolByStageAndArea error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }
    /**
     * 产品后台管理  添加学校
     *
     * @param requestId
     * @param requestBody
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addSchoolInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public BaseResponse addBaseSchoolInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {
            Map<String, Object> map = JSONObject.parseObject(requestBody);
            if (map == null || null == map.get("groupAddress")) {
                logger.error("groupAddress is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupAddress is null");
            }
            if (map == null || null == map.get("groupName")) {
                logger.error("groupName is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupName is null");
            }
            if (map == null || null == map.get("groupPeriod")) {
                logger.error("groupPeriod is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupPeriod is null");
            }
            if (map == null || null == map.get("childGroupBeanList")) {
                logger.error("childGroupBeanList is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "childGroupBeanList is null");
            }
            if (map == null || null == map.get("groupExternalSchoolId")) {
                logger.error("groupExternalSchoolId is null or error" + baseResponse.getRequestId());
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "groupExternalSchoolId is null");
            }
            boolean bool = groupDataService.addBaseSchoolInfo(map);
            baseResponse.setResult(bool);
        } catch (Exception ex) {
            logger.error("AuthGroupController.addSchoolInfo error ", ex);
            return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
        }
        return baseResponse;
    }


}
