package com.eedu.auth.eventListener;

import com.alibaba.fastjson.JSONArray;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.beans.AuthUserWeChatBindBean;
import com.eedu.auth.enumration.EventSourceEnum;
import com.eedu.auth.service.AuthUserService;
import com.eedu.auth.service.AuthUserWeChatBindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by liuhongfei on 2017/6/8.
 */
@Component
public class EventListener implements ApplicationListener<AuthEvent> {

    Logger log = LoggerFactory.getLogger(EventListener.class);

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private AuthUserWeChatBindService authUserWeChatBindService;

    @Async
    @Override
    public void onApplicationEvent(AuthEvent diagnosisEvent) {
        EventSourceHandler source = (EventSourceHandler) diagnosisEvent.getSource();
        String jsonBody = source.getJsonBody();
        EventSourceEnum sourceEnum = source.getSource();
        switch (sourceEnum) {
            case SAVE_STUDENT_LOGIN_TIME:
                try {
                    AuthUserBean authUserBean = JSONArray.parseObject(jsonBody, AuthUserBean.class);
//                    authUserBean.setUserLoginDate(new Date());
                    authUserService.updateUserInfo(authUserBean);
                } catch (Exception e) {
                    //保存失败，补偿措施。
                    log.error("EventListener SAVE_STUDENT_LOGIN_TIME  error" + e.getMessage());
                }
            case SAVE_STUDENT_WECHAT_BIND:
                try{

                }catch(Exception e){
                    log.error("EventListener SAVE_STUDENT_WECHAT_BIND  error" + e.getMessage());
                }
            case SAVE_TEACHER_WECHAT_BIND:
                try{

                }catch(Exception e){
                    log.error("EventListener SAVE_TEACHER_WECHAT_BIND  error" + e.getMessage());
                }
        }
    }

    public void saveStudentWechatBind(String openId,AuthUserBean authUserBean) throws Exception{

        AuthUserWeChatBindBean userWeChatBindBean = new AuthUserWeChatBindBean();
        userWeChatBindBean.setOpenId(openId);
        AuthUserWeChatBindBean userWeChatBind = authUserWeChatBindService.getUserWeChatBind(userWeChatBindBean);
//        if (null == userWeChatBind) {
//            logger.error("login WeChat Exception：requestId："+ baseResponse.getRequestId()+","+ ResponseCode.RESOURCE_NOTFOUND.toString()+".Wechat login  userWeChatBind");
//            return BaseResponse.setResponse(baseResponse, ResponseCode.RESOURCE_NOTFOUND.toString(), "Wechat login  userWeChatBind");
//        }
        userWeChatBind.setUserId(authUserBean.getUserId());
        userWeChatBind.setType(authUserBean.getUserType());
        userWeChatBind.setUpdateDate(new Date());
        authUserWeChatBindService.updateUserWeChatBind(userWeChatBind);
    }
}
