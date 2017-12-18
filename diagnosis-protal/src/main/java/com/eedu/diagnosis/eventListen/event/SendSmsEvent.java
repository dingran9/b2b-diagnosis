package com.eedu.diagnosis.eventListen.event;

import org.springframework.context.ApplicationEvent;




@SuppressWarnings("serial")
public class SendSmsEvent extends ApplicationEvent {

    public SendSmsEvent(Object source) {
        super(source);
    }
}
