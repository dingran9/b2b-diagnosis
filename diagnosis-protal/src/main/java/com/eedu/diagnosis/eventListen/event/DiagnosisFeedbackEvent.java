package com.eedu.diagnosis.eventListen.event;

import org.springframework.context.ApplicationEvent;


public class DiagnosisFeedbackEvent extends ApplicationEvent {


    public DiagnosisFeedbackEvent(Object source) {
        super(source);
    }
}
