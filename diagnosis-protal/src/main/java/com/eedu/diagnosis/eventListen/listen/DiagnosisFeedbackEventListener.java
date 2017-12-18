package com.eedu.diagnosis.eventListen.listen;

import com.alibaba.fastjson.JSONArray;
import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.eventListen.EventSourceHandler;
import com.eedu.diagnosis.eventListen.event.UpdateUserEvent;
import com.eedu.diagnosis.paper.api.dto.DiagnosisFeedbackDto;
import com.eedu.diagnosis.paper.api.openService.BasePaperOpenService;
import com.eedu.diagnosis.protal.model.request.DiagnosisFeedbackModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class DiagnosisFeedbackEventListener implements ApplicationListener<UpdateUserEvent> {

    Logger log = LoggerFactory.getLogger(DiagnosisFeedbackEventListener.class);

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BasePaperOpenService basePaperOpenServiceImpl;
    
    @Async
    @Override
    public void onApplicationEvent(UpdateUserEvent diagnosisEvent) {
        EventSourceHandler source = (EventSourceHandler) diagnosisEvent.getSource();
        String jsonBody = source.getJsonBody();
        EventSourceEnum sourceEnum = source.getSource();
        switch (sourceEnum) {
            case SAVE_DIAGNOSIS_FEEDBACK:
                try {
                    DiagnosisFeedbackModel model = JSONArray.parseObject(jsonBody, DiagnosisFeedbackModel.class);
                    DiagnosisFeedbackDto dfDto = new DiagnosisFeedbackDto();
	                BeanUtils.copyProperties(model,dfDto);
	                basePaperOpenServiceImpl.saveDiagnosisFeedback(dfDto);
                } catch (Exception e) {
                    //保存失败，补偿措施。
                    log.error("DiagnosisFeedbackEventListener  SAVE_DIAGNOSIS_FEEDBACK "+ "  error" + e.getMessage());
                }
                break;
          }
    }
    

    
    

}
