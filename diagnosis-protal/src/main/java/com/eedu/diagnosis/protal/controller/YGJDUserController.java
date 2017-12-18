package com.eedu.diagnosis.protal.controller;

import com.eedu.diagnosis.protal.model.user.YGJDUserRequestModel;
import com.eedu.diagnosis.protal.responsecode.BaseResponse;
import com.eedu.diagnosis.protal.responsecode.ResponseCode;
import com.eedu.diagnosis.protal.service.UserService;
import com.eedu.diagnosis.util.DESEncode;
import com.eeduspace.uuims.comm.util.base.ValidateUtils;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * Author: dingran
 * Date: 2017/5/4
 * Description:阳光金点-免登陆 专属类
 */
@RestController
@RequestMapping("/ygjd/user")
public class YGJDUserController {

    private final Logger logger = LoggerFactory.getLogger(YGJDUserController.class);
    private Gson gson=new Gson();
    @Autowired
    private UserService userService;
    @Value("${yangguangjindian.secretkey}")
    private String ygjdSK;
    /**
     * 阳光金点-用户登录
     * @author dingran
     */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse login(@RequestParam("requestId") String requestId, @RequestBody String requestBody) {
        BaseResponse baseResponse = new BaseResponse(requestId);
        try {

            YGJDUserRequestModel userRequestModel = gson.fromJson(requestBody, YGJDUserRequestModel.class);
            if(StringUtils.isBlank(userRequestModel.getUserCode())){
                logger.error("login Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".password");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "password");
            }
            if (StringUtils.isBlank(userRequestModel.getTelephone())) {
                logger.error("login Exception：requestId："+  baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_MISS.toString()+".Phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_MISS.toString(), "Phone");
            }
            //TODO 解密
            String phone = DESEncode.decode(userRequestModel.getTelephone(),ygjdSK);//解密telephone
            // 验证数据格式
            if (!ValidateUtils.isMobile(phone)) {
                logger.error("login ValidateMobile Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.PARAMETER_INVALID.toString()+".phone");
                return BaseResponse.setResponse(baseResponse, ResponseCode.PARAMETER_INVALID.toString(), "phone");
            }
            userRequestModel.setEquipmentType("Wechat");
            userRequestModel.setPhone(phone);
            //验证该用户是否存在于UUIMS、学科能力发展测评
            //使用 uuims 提供给的接口，传递手机号、密码进行登录操作，若存在则返回，若不存在则创建用户
            BaseResponse loginResponse = userService.ygjdLogin(userRequestModel,baseResponse);
            loginResponse.setRequestId(baseResponse.getRequestId());
            return loginResponse;
        } catch (Exception e) {
            logger.error("login error:{}", e);
            return BaseResponse.setResponse(new BaseResponse(),ResponseCode.SERVICE_ERROR.toString());
        }
    }

}
