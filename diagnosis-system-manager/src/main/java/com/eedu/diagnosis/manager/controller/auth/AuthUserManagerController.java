package com.eedu.diagnosis.manager.controller.auth;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthGroupBean;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserManagerBean;
import com.eedu.auth.beans.AuthUserManagerConditionBean;
import com.eedu.auth.beans.enums.ConstantEnum;
import com.eedu.diagnosis.common.exception.DiagnosisException;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.inclass.test.api.dto.DiagnosisStudentAnswerMachineDto;
import com.eedu.diagnosis.manager.model.request.ClassTest.DiagnosisStudentAnswerMachineModel;
import com.eedu.diagnosis.manager.model.response.AuthUserManagerModel;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AuthStudentService;
import com.eedu.diagnosis.manager.service.AuthTeacherService;
import com.eedu.diagnosis.manager.service.ClassTestService;
import com.eedu.diagnosis.manager.service.GroupDataService;
import com.eedu.diagnosis.manager.utils.PoiUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 10:13
 * Describe:
 */
@RestController
@RequestMapping("/userManager")
public class AuthUserManagerController {

	private final Logger logger = LoggerFactory.getLogger(AuthGroupController.class);
	@Autowired
	private AuthTeacherService teacherService;
	@Autowired
	private GroupDataService groupService;
	@Autowired
	private AuthStudentService studentService;
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	@Autowired
	private ClassTestService classTestService ;
	/**
	 * 老师注册
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/teacherRegister", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse teacherRegister(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || StringUtils.isBlank(condition.getUserAccount())){
				logger.error("userAccount is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userAccount is null");
			}
			if(StringUtils.isBlank(condition.getUserPassword())){
				logger.error("userPassword is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userPassword is null");
			}
			//根据条件查询教师信息
			List<AuthUserManagerBean> userManagerBeanList = teacherService.getUserManagerList(condition);
			if(!CollectionUtils.isEmpty(userManagerBeanList)){
				logger.error("userAccount is exist" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.REPEAT_LOGIN.toString(),"userAccount is exist");
			}

			boolean bool= teacherService.addUserManager(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.teacherRegister is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 教师端 获取个人信息
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeacherInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getTeacherInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || (null == condition.getUserId() || condition.getUserId() <= 0) && StringUtils.isBlank(condition.getUserAccount())){
				logger.error("condition is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"condition is null");
			}
			//根据条件查询教师信息
			AuthUserManagerBean userManagerBean = teacherService.getTeacherInfo(condition);
			if(null == userManagerBean || userManagerBean.getUserId() <= 0){
				logger.error("condition is not exist" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString(),"condition is not exist");
			}
			baseResponse.setResult(userManagerBean);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getTeacherInfo is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 教师端 修改个人信息
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTeacherInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse updateTeacherInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				logger.error("condition is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"condition is null");
			}
			//根据条件查询教师信息
			AuthUserManagerBean userManagerBean = teacherService.getTeacherInfoById(condition.getUserId());
			if(null == userManagerBean){
				logger.error("condition is not exist" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"condition is not exist");
			}
			if(StringUtils.isNotEmpty(condition.getUserPhone())){
				AuthUserManagerBean userIsExist = new AuthUserManagerBean();
				userIsExist.setUserPhone(condition.getUserPhone());
				AuthUserManagerBean managerBean = teacherService.getUserIsExist(userIsExist);
				if(null != managerBean){
					logger.error("userPhone is exist" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_INUSE.toString(),"userPhone IsUsed");
				}
				AuthUserBean user = new AuthUserBean();
				user.setUserPhone(condition.getUserPhone());
				List<AuthUserBean> userList = studentService.getUserBean(user);
				if(!CollectionUtils.isEmpty(userList)){
					logger.error("userPhone is exist" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_INUSE.toString(),"userPhone IsUsed");
				}
			}

			boolean bool = teacherService.updateTeacherManager(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.updateTeacherInfo is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 教师端 忘记密码
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTeacherByPhone", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse updateTeacherByPhone(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || StringUtils.isEmpty(condition.getUserPhone())){
				logger.error("userPhone is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userPhone is null");
			}
			if(null == condition || StringUtils.isEmpty(condition.getUserPassword())){
				logger.error("userPassword is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userPassword is null");
			}
			//根据条件查询教师信息
			AuthUserManagerBean userManagerBean = teacherService.getUserByUserPhone(condition.getUserPhone());
			if(null == userManagerBean){
				logger.error("userPhone is not exist" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString(),"userPhone is not exist");
			}
			AuthUserManagerBean updateUserManager = new AuthUserManagerBean();
			updateUserManager.setUserId(userManagerBean.getUserId());
			updateUserManager.setUserPassword(condition.getUserPassword());

			boolean bool = teacherService.updateTeacherManager(updateUserManager);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.updateTeacherInfo is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 查询学校下所有教师
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeacherList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getTeacherList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody,AuthGroupBean.class);
			if(null == condition || null == condition.getGroupId() || condition.getGroupId() <= 0){ //判断学校id
				logger.error("groupId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"groupId");
			}
			condition.setGroupType(ConstantEnum.GROUP_TYPE_SCHOOL.getType());
			//验证学校是否存在
			List<AuthGroupBean> groupBeanList = groupService.getGroupByCondition(condition);
			if(CollectionUtils.isEmpty(groupBeanList)){
				logger.error("groupId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString(),"groupId");
			}
			AuthGroupBean groupBean = groupBeanList.get(0);
			baseResponse.setResult(teacherService.getTeacherListBySchool(groupBean));
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getTeacherList is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 查询老师下面的班级
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getClassByTeacher", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getClassByTeacher(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean userManagerBean = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == userManagerBean || null == userManagerBean.getUserId() || userManagerBean.getUserId() <= 0){
				logger.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userId");
			}
			userManagerBean.setUserType(ConstantEnum.USER_TYPE_TEACHER.getType());
			//根据条件查询教师信息
			List<AuthUserManagerBean> userManagerBeanList = teacherService.getUserManagerList(userManagerBean);
			if(CollectionUtils.isEmpty(userManagerBeanList)){
				logger.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString(),"userId");
			}
			//根据教师查询教师下的班级信息
            List<AuthGroupBean> groupBeanList = teacherService.getClassByTeacherId(userManagerBeanList.get(0).getUserId());
			baseResponse.setResult(groupBeanList);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getClassByTeacher is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 根据班级查询班级下的所有学生
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getStudentListByClassId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getStudentListByClassId(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthGroupBean condition = GsonUtils.getGson().fromJson(requestBody,AuthGroupBean.class);
			if(null == condition || null == condition.getGroupId() || condition.getGroupId() <= 0){
				logger.error("groupId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"groupId");
			}
			condition.setGroupType(ConstantEnum.GROUP_TYPE_CLASS.getType());
			List<AuthGroupBean> groupBeanList = groupService.getGroupByCondition(condition);
			if(CollectionUtils.isEmpty(groupBeanList)){
				logger.error("AuthUserManagerController.getStudentListByClassId error,获取数据失败," + condition.getGroupId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString());
			}
			List<AuthUserBean> userBeanList = teacherService.getMyStudentByClassId(groupBeanList.get(0).getGroupId());
            /**查询教师下学生是否绑定答题器**/
			Map<String, Object> map = classTestService.studentList(new DiagnosisStudentAnswerMachineModel());
			List<DiagnosisStudentAnswerMachineDto> list = (List<DiagnosisStudentAnswerMachineDto>) map.get("rows");
			for (AuthUserBean  bean : userBeanList){
				int size = list.stream().filter(g -> g.getStudentCode().equals(String.valueOf(bean.getUserId()))).collect(Collectors.toList()).size();
				if (size > 0){
					bean.setIsBind(1);
				}else {
					bean.setIsBind(0);
				}
			}
			baseResponse.setResult(userBeanList);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getStudentListByClassId is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台校长管理页面  教师管理列表
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getTeacherManagerList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getTeacherManagerList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthUserManagerConditionBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerConditionBean.class);


			PageInfo<AuthUserManagerConditionBean> userManagerBeanList = teacherService.getTeacherManagerList(condition);
			baseResponse.setResult(userManagerBeanList);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getTeacherManagerList is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台后台校长管理页面  教师管理 添加老师
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addTeacherManager", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse addTeacherManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || StringUtils.isBlank(condition.getUserName())){
				logger.error("userName is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userName is null");
			}
			if(null == condition || null == condition.getUserSchoolId() || condition.getUserSchoolId() <= 0){
				logger.error("userSchoolId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userSchoolId is null");
			}
			if(null == condition || null == condition.getUserType() || condition.getUserType() <= 0){
				logger.error("userType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userType is null");
			}
			if(!ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType().equals(condition.getUserType())) {
				if (null == condition || null == condition.getUserGradeId() || condition.getUserGradeId() <= 0) {
					logger.error("userGradeId is null or error" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userGradeId is null");
				}
				if (null == condition || null == condition.getUserSubject() || condition.getUserSubject() <= 0) {
					logger.error("userSubject is null or error" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userSubject is null");
				}
				if (null == condition || CollectionUtils.isEmpty(condition.getClassBeanList())) {
					logger.error("classBeanList is null or error" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classBeanList is null");
				}
			}
            //默认6位随机数
//			condition.setUserAccount(String.valueOf((int)((Math.random()*9+1)*100000)));

//			AuthUserManagerBean userManagerBean = new AuthUserManagerBean();
//			userManagerBean.setUserAccount(condition.getUserAccount());
//			List<AuthUserManagerBean>  userlist = authorizeService.getUserManagerList(userManagerBean);

//			if(null != userlist && userlist.size() > 0){
//				logger.error("userAccount is having" + baseResponse.getRequestId());
//				return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_DUPLICATE.toString(),".userAccount is having");
//			}
			boolean bool = teacherService.addTeacherManager(condition);
//			System.out.println(bool);
//			System.out.println(condition.getUserId());
//			System.out.println(condition.getUserPassword());
//			if(bool && condition.getUserId() > 0){
//				condition.setUserAccount(AccountConverter.parseLong(condition.getUserId()));
//				List<AuthUserManagerBean> list = new ArrayList<>();
//				list.add(condition);
//				bool = teacherService.batchUpdateManagerAccount(list);
//
//			}
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.addTeacherManager is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台教师管理 修改老师
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTeacherManager", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse updateTeacherManager(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || StringUtils.isBlank(condition.getUserPhone())){
				logger.error("userPhone is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userPhone");
			}
			boolean bool = teacherService.updateTeacherManager(condition);
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.updateTeacherManager is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台管理 模版下载 (弃用)
	 * @param requestId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse downloadTemplate(@RequestParam("requestId") String requestId,@RequestParam("requestBody") String requestBody,
	                                     HttpServletRequest request,HttpServletResponse response) throws Exception {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		String fileName = "Teacher Upload Template.xlsx";
		//GsonUtils.getGson().fromJson(requestBody,String.class);
		response.setContentType("text/html;charset=UTF-8");
		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try{
			request.setCharacterEncoding("UTF-8");
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			File f = new File(rootpath + "template/" + fileName);
			response.setContentType("application/x-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="+fileName);
			response.setHeader("Content-Length",String.valueOf(f.length()));
			in = new BufferedInputStream(new FileInputStream(f));
			out = new BufferedOutputStream(response.getOutputStream());
			byte[] data = new byte[1024];
			int len = 0;
			while (-1 != (len=in.read(data, 0, data.length))) {
				out.write(data, 0, len);
			}
			baseResponse.setResult(true);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.downloadTemplate is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
		return baseResponse;
	}

	/**
	 * 读取excel文件中的用户信息，保存教师数据
	 * @param file
	 */
	@RequestMapping(value = "/readExcel/{schoolId}" , method = RequestMethod.POST)
	public BaseResponse readExcel(@RequestParam("excelfile") CommonsMultipartFile file,@PathVariable("schoolId") Integer schoolId){
		BaseResponse baseResponse = new BaseResponse("readExcel");
		if(null == file || file.isEmpty()){
			logger.error("userManager readExcel Exception：excelfile is null."
					+ baseResponse.getRequestId());
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "excelfile");
		}
		if(null == schoolId){
			logger.error("userManager readExcel Exception：schoolId is null."
					+ baseResponse.getRequestId());
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "schoolId");
		}
		System.out.println(file.getOriginalFilename());
		if(!file.getOriginalFilename().endsWith("xls") && !file.getOriginalFilename().endsWith("xlsx")){
			logger.error( "userManager readExcel Exception：不是excel文件");
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "unfavorable excel case ");
		}
		try {
			List<String[]> teacherInfoList = PoiUtils.readExcel(file);
			if(!teacherInfoList.isEmpty()){
				teacherService.batchSaveTeacher(teacherInfoList,schoolId);
				baseResponse.setMessage("导入成功");
			}else{
				baseResponse.setMessage("excel数据为空。");
			}
			baseResponse.setResult(true);
		} catch (Exception e) {
			logger.error("userManager readExcel is error ",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}


	/**
	 * 读取excel文件中的用户信息，保存学生数据
	 * @param file
	 */
	@RequestMapping(value = "/readExcelForStudent" , method = RequestMethod.POST)
	public BaseResponse readExcel(@RequestParam("excelfile") CommonsMultipartFile file) {
		BaseResponse baseResponse = new BaseResponse("readExcel");
		if(null == file || file.isEmpty()){
			logger.error("AuthUserManagerController readExcelForStudent Exception：excelfile is null."
					+ baseResponse.getRequestId());
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "excelfile");
		}
		System.out.println(file.getOriginalFilename());
		if(!file.getOriginalFilename().endsWith("xls") && !file.getOriginalFilename().endsWith("xlsx")){
			logger.error( "AuthUserManagerController readExcelForStudent Exception：不是excel文件");
			return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "unfavorable excel case ");
		}
		try {
			List<String[]> studentInfoList = PoiUtils.readExcel(file);
			if(!studentInfoList.isEmpty()){
				teacherService.batchSaveStudentInfo(studentInfoList);
				baseResponse.setMessage("导入成功");
			}else{
				baseResponse.setMessage("excel数据为空。");
			}
			baseResponse.setResult(true);
		}catch (DiagnosisException e) {
			logger.error("AuthUserManagerController readExcelForStudent is error ",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_DUPLICATE.toString(),e.getMessage());
		}
		catch (Exception e) {
			logger.error("AuthUserManagerController readExcelForStudent is error ",e);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	//导出excel
	@RequestMapping(value = "/exportExcel/{userSchoolId}" , method = RequestMethod.GET)
	public void exportExcel(@PathVariable(value = "userSchoolId") Integer userSchoolId,
							@RequestParam(value = "userGradeIden",required = false) String userGradeIden,
							@RequestParam(value = "userSubjectIden",required = false) String userSubjectIden,HttpServletResponse response){

		String title = "教师信息统计表";
		String[] properties = {"userName","userAccount","userPhone","userSexStr"};
		String[] header = {"姓名","登录账号","手机号","性别"};
		try{
			AuthUserManagerConditionBean condition = new AuthUserManagerConditionBean();
			if(StringUtils.isNotEmpty(userGradeIden)){
				condition.setUserGradeIden(userGradeIden);
			}
			if(StringUtils.isNotEmpty(userSubjectIden)){
				condition.setUserSubjectIden(userSubjectIden);
			}
			condition.setUserSchoolId(userSchoolId);
			condition.setPageSize(Integer.MAX_VALUE);
			condition.setPageNum(1);
			PageInfo<AuthUserManagerConditionBean> userManagerBeanList = teacherService.getTeacherManagerList(condition);
			if(null != userManagerBeanList && !userManagerBeanList.getList().isEmpty()){
				List<AuthUserManagerConditionBean> list = userManagerBeanList.getList();
				List<AuthUserManagerModel> teacherList = PageHelperUtil.converterList(list,AuthUserManagerModel.class);
				JSONArray array = JSONObject.parseArray(JSON.toJSONString(teacherList));
				PoiUtils.downloadExcelFile(title,properties,header,array,response);
			}
		} catch (Exception e) {
			logger.error("userManager exportExcel is error ",e);
		}
	}

	/**
	 * 查询学校对应的校长列表
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPrincipalList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getPrincipalList(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || null == condition.getUserSchoolId() || condition.getUserSchoolId() <= 0){ //判断学校id
				logger.error("userSchoolId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userSchoolId");
			}

			condition.setUserType(ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType());

			baseResponse.setResult(teacherService.getUserManagerList(condition));
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getPrincipalList is error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 后台后台校长管理页面  教师管理 修改教师年级，班级，学科绑定关系
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateTeacherManagerGroup", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse updateTeacherManagerGroup(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				logger.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			if(!ConstantEnum.USER_TYPE_SCHOOL_MASTER.getType().equals(condition.getUserType())) {

				if (null == condition || null == condition.getUserSchoolId() || condition.getUserSchoolId() <= 0) {
					logger.error("userSchoolId is null or error" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userSchoolId is null");
				}
				if (null == condition || null == condition.getUserGradeId() || condition.getUserGradeId() <= 0) {
					logger.error("userGradeId is null or error" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userGradeId is null");
				}
				if (null == condition || null == condition.getUserSubject() || condition.getUserSubject() <= 0) {
					logger.error("userSubject is null or error" + baseResponse.getRequestId());
					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "userSubject is null");
				}
				//老师的班级信息可以解绑，所以可以不传班级列表
//				if (null == condition || CollectionUtils.isEmpty(condition.getClassBeanList())) {
//					logger.error("classBeanList is null or error" + baseResponse.getRequestId());
//					return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "classBeanList is null");
//				}
			}
			boolean bool = teacherService.updateTeacherManagerGroup(condition);
			if(bool){
				redisClientTemplate.del("Unbundled_Teacher_"+condition.getUserId());
				redisClientTemplate.del("Leave_Teacher_"+condition.getUserId());
			}
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.updateTeacherManagerGroup is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 校长端 获取校长学校，学段信息
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPrincipalInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getPrincipalInfo(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			AuthUserManagerBean condition = GsonUtils.getGson().fromJson(requestBody,AuthUserManagerBean.class);
			if(null == condition || null == condition.getUserId() || condition.getUserId() <= 0){
				logger.error("userId is null," + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			//根据条件查询教师信息
			AuthUserManagerBean userManagerBean = teacherService.getPrincipalInfo(condition);
			if(null == userManagerBean){
				logger.error("condition is not exist" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.RESOURCE_NOTFOUND.toString(),"condition is not exist");
			}
			baseResponse.setResult(userManagerBean);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.getPrincipalInfo is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}


	/**
	 * 后台后台校长管理页面  教师管理 删除教师年级，班级，学科绑定关系
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delTeacherManagerGroup", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse delTeacherManagerGroup(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		logger.info("json: " + GsonUtils.getGson().toJson(requestBody));
		try{
			Map<String, Object> condition = JSONObject.parseObject(requestBody);
			if(null == condition || null == condition.get("userId")){
				logger.error("userId is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userId is null");
			}
			if(null == condition || null == condition.get("userType")){
				logger.error("userType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"userType is null");
			}
			if(null == condition || null == condition.get("operatingType")){
				logger.error("operatingType is null or error" + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(),"operatingType is null");
			}
			Integer operatingType = Integer.parseInt(condition.get("operatingType").toString());

			Integer userId = Integer.parseInt(condition.get("userId").toString());

            AuthUserManagerBean authUserManagerBean = new AuthUserManagerBean();

			authUserManagerBean.setUserId(Integer.parseInt(condition.get("userId").toString()));

			authUserManagerBean.setUserType(Integer.parseInt(condition.get("userType").toString()));
			//如果是  离职 那保证userPhone 不为空
			if(operatingType == 1)authUserManagerBean.setUserPhone("1");

			boolean bool = teacherService.delTeacherManagerGroup(authUserManagerBean);
			if(bool){
				if(operatingType == 1)
				    redisClientTemplate.set("Unbundled_Teacher_"+userId, String.valueOf(userId));
				else
					redisClientTemplate.set("Leave_Teacher_"+userId, String.valueOf(userId));
			}
			baseResponse.setResult(bool);
		}catch (Exception ex){
			logger.error("AuthUserManagerController.delTeacherManagerGroup is error ",ex);
			return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}



}
