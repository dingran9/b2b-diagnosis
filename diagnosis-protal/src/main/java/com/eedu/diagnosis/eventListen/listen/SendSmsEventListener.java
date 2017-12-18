package com.eedu.diagnosis.eventListen.listen;

import com.alibaba.fastjson.JSONArray;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.eventListen.EventSourceHandler;
import com.eedu.diagnosis.eventListen.event.SendSmsEvent;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.protal.model.user.UserRequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class SendSmsEventListener implements ApplicationListener<SendSmsEvent> {

    Logger log = LoggerFactory.getLogger(SendSmsEventListener.class);

    @Autowired
   	private BasePaperOpenService basePaperOpenServiceImpl;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    
    
    @Async
    @Override
    public void onApplicationEvent(SendSmsEvent diagnosisEvent) {
        EventSourceHandler source = (EventSourceHandler) diagnosisEvent.getSource();
        String jsonBody = source.getJsonBody();
        EventSourceEnum sourceEnum = source.getSource();

        switch (sourceEnum) {
            case REGISTER_SMS:
                try {
                	UserRequestModel userModel = JSONArray.parseObject(jsonBody, UserRequestModel.class);
                	String code = basePaperOpenServiceImpl.sendSms( userModel.getPhone(),userModel.getSmsType());
                	log.debug(code);
                    redisClientTemplate.setex("diagnosis_protal_register_sms"+userModel.getPhone(),1800, code);
                } catch (Exception e) {
                    //保存失败，补偿措施。
                    log.error("SendSmsEventListener  REGISTER_SMS "+ "  error" + e.getMessage());
                }
                break;
            case EDIT_PHONE_SMS:
                try{
                	UserRequestModel userModel = JSONArray.parseObject(jsonBody, UserRequestModel.class);
                	String code = basePaperOpenServiceImpl.sendSms( userModel.getPhone(),userModel.getSmsType());
                	log.debug(code);
                    redisClientTemplate.setex("diagnosis_protal_edit_phone_sms"+userModel.getPhone(),1800, code);
                }catch(Exception e){
                    log.error("SendSmsEventListener EDIT_PHONE_SMS "+ "  error" + e.getMessage());
                }
                break;
          }
    }
    

    
    

}
