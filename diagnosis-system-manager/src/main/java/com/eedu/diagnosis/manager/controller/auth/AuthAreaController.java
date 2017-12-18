package com.eedu.diagnosis.manager.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.eedu.auth.beans.AuthCityBean;
import com.eedu.auth.beans.AuthDistrictBean;
import com.eedu.auth.beans.AuthProvinceBean;
import com.eedu.diagnosis.common.utils.GsonUtils;
import com.eedu.diagnosis.common.utils.PageHelperUtil;
import com.eedu.diagnosis.manager.model.response.BaseAreaVo;
import com.eedu.diagnosis.manager.responsecode.BaseResponse;
import com.eedu.diagnosis.manager.responsecode.ResponseCode;
import com.eedu.diagnosis.manager.service.AreaDataService;
import com.evaluate.base.data.api.dto.BaseAreaDto;
import com.evaluate.base.data.api.service.BaseAreaService;
import com.evaluate.base.data.api.service.BaseSchoolService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: BJCAOYZ
 * Date: 2017/3/22
 * Time: 9:35
 * Describe: 省市县 基础数据查询
 */
@RestController
@RequestMapping("/areaManager")
public class AuthAreaController {

	private final Logger logger = LoggerFactory.getLogger(AuthAreaController.class);
	@Autowired
	private AreaDataService areaDataService;
	@Autowired
	private BaseAreaService   baseAreaService;

	/**
	 * 查询所有省
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProvince", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getProvince(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			List<AuthProvinceBean> provinceBeanList = areaDataService.getAllProvince();
			if(CollectionUtils.isEmpty(provinceBeanList)){
				logger.error("AuthAreaController.getProvince error,获取数据失败");
				return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
			}
			baseResponse.setResult(provinceBeanList);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("AuthAreaController.getProvince error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 根据省ID查询市
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCityByProvinceId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getCityByProvinceId(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthCityBean authCityBean = GsonUtils.getGson().fromJson(requestBody,AuthCityBean.class);
			if(null == authCityBean || null == authCityBean.getProvinceId() || authCityBean.getProvinceId() <= 0 ){
				logger.error("provinceId is null or error " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"provinceId is null or error");
			}
			List<AuthCityBean> cityBeanList = areaDataService.getCityByProvinceId(authCityBean.getProvinceId());
			if(CollectionUtils.isEmpty(cityBeanList)){
				logger.error("AuthAreaController.getCity error,获取数据失败");
				return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
			}
			baseResponse.setResult(cityBeanList);
		}catch(Exception ex){
			logger.error("AuthAreaController.getCity error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

	/**
	 * 根据市ID查询区县
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDistrictByCityId", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getDistrictByCityId(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			AuthDistrictBean districtBean = GsonUtils.getGson().fromJson(requestBody,AuthDistrictBean.class);
			if(null == districtBean || null == districtBean.getCityId() || districtBean.getCityId() <= 0 ){
				logger.error("cityId is null or error " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"cityId is null or error");
			}
			List<AuthDistrictBean> districtBeanList = areaDataService.getDistrictByCityId(districtBean.getCityId());
			if(CollectionUtils.isEmpty(districtBeanList)){
				logger.error("AuthAreaController.getDistrict error,获取数据失败");
				return BaseResponse.setResponse(baseResponse,ResponseCode.SERVICE_ERROR.toString());
			}
			baseResponse.setResult(districtBeanList);
		}catch(Exception ex){
			logger.error("AuthAreaController.getDistrict error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}

/**********************************华丽丽分割线***************************************************/

	/**
	 * 查询所有省
	 * @param requestId
	 * @param requestBody
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getArea", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	public BaseResponse getBaseProvince(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
		BaseResponse baseResponse = new BaseResponse(requestId);
		try{
			List<BaseAreaVo>  areaVo = new ArrayList<BaseAreaVo>();
			JSONObject jsonObject = JSONObject.parseObject(requestBody);
			if(StringUtils.isBlank(jsonObject.getString("parentId"))){
				logger.error("parentId is null or error " + baseResponse.getRequestId());
				return BaseResponse.setResponse(baseResponse,ResponseCode.PARAMETER_MISS.toString(),"parentId is null or error");
			}
			List<BaseAreaDto> area = baseAreaService.getArea(jsonObject.getString("parentId"));
			if (null != area && area.size() != 0) {
				areaVo =  PageHelperUtil.converterList(area, BaseAreaVo.class);
			}
			baseResponse.setResult(areaVo);
		}catch(Exception ex){
			ex.printStackTrace();logger.error("AuthAreaController.getArea error ",ex);
			return BaseResponse.setResponse(baseResponse, ResponseCode.SERVICE_ERROR.toString());
		}
		return baseResponse;
	}


}
