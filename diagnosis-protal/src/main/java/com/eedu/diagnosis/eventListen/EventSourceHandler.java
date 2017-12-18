package com.eedu.diagnosis.eventListen;


import com.eedu.diagnosis.common.enumration.EventSourceEnum;

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
