package com.eedu.diagnosis.exam.eventListener;


import com.eedu.diagnosis.common.enumration.EventSourceEnum;

/**
 * Created by dqy on 2017/3/16.
 */
public class EventSourceHandler {
    private String jsonBody;
    private EventSourceEnum source;

    public EventSourceHandler(String jsonBody,EventSourceEnum source){
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
