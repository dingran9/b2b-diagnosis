package com.eedu.auth.eventListener;

import com.eedu.auth.enumration.EventSourceEnum;

/**
 * Created by liuhongfei on 2017/6/8.
 */
public class EventSourceHandler {
    private String jsonBody;
    private EventSourceEnum source;

    public EventSourceHandler(String jsonBody, EventSourceEnum source){
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
