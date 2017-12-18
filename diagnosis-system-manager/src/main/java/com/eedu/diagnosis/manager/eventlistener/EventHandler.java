package com.eedu.diagnosis.manager.eventlistener;

import com.eedu.diagnosis.common.enumration.EventSourceEnum;

/**
 * Created by dqy on 2017/6/8.
 */
public class EventHandler {
    private String jsonBody;
    private EventSourceEnum source;

    public EventHandler(String jsonBody,EventSourceEnum source){
        this.jsonBody = jsonBody;
        this.source = source;
    }

    public String getJsonBody() {
        return jsonBody;
    }

    public EventSourceEnum getSource() {
        return source;
    }
}
