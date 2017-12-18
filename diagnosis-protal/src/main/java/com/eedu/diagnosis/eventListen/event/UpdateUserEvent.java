package com.eedu.diagnosis.eventListen.event;

import org.springframework.context.ApplicationEvent;


@SuppressWarnings("serial")
public class UpdateUserEvent extends ApplicationEvent {

    public UpdateUserEvent(Object source) {
        super(source);
    }
}
