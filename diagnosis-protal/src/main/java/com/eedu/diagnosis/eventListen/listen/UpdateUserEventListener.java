package com.eedu.diagnosis.eventListen.listen;

import com.alibaba.fastjson.JSONArray;
import com.eedu.auth.beans.AuthUserBean;
import com.eedu.auth.service.AuthUserService;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.eventListen.EventSourceHandler;
import com.eedu.diagnosis.eventListen.event.UpdateUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class UpdateUserEventListener implements ApplicationListener<UpdateUserEvent> {

    Logger log = LoggerFactory.getLogger(UpdateUserEventListener.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AuthUserService authUserService;
    
    @Async
    @Override
    public void onApplicationEvent(UpdateUserEvent diagnosisEvent) {
        EventSourceHandler source = (EventSourceHandler) diagnosisEvent.getSource();
        String jsonBody = source.getJsonBody();
        EventSourceEnum sourceEnum = source.getSource();
        switch (sourceEnum) {
            case EDIT_PHONE:
                try {
                    AuthUserBean findModel = JSONArray.parseObject(jsonBody, AuthUserBean.class);
                    authUserService.updateUserInfo(findModel);
                } catch (Exception e) {
                    //保存失败，补偿措施。
                    log.error("UpdateUserEventListener  REGISTER_SMS "+ "  error" + e.getMessage());
                }
                break;

          }
    }
    

    
    

}
