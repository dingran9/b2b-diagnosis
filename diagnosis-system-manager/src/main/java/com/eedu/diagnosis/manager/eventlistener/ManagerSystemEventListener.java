package com.eedu.diagnosis.manager.eventlistener;

import com.eedu.diagnosis.common.enumration.EventSourceEnum;
import com.eedu.diagnosis.common.service.redisService.RedisClientTemplate;
import com.eedu.diagnosis.paper.api.openService.ResourceOpenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class ManagerSystemEventListener implements ApplicationListener<ManagerSystemEvent> {
    @Autowired
    private RedisClientTemplate redisClientTemplate;
    @Autowired
    private ResourceOpenService resourceOpenService;

    @Override
    public void onApplicationEvent(ManagerSystemEvent event) {
        EventHandler source = event.getSourceHandler();
        String paperCode = source.getJsonBody();
        EventSourceEnum sourceEnum = source.getSource();
        switch (sourceEnum) {
            case CREATE_DIAGNOSIS_PAPER:
                String paperJson = redisClientTemplate.get("repositoryPaper_" + paperCode);
                if (StringUtils.isBlank(paperJson)) {//如果redis里取不到试卷信息，到资源库取，如果能取到 需要存入redis一份
                    try {
                        resourceOpenService.getResourcePaperInfo(paperCode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            break;
        }
    }
}