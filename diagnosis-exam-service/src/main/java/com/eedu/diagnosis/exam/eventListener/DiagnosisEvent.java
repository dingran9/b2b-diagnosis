package com.eedu.diagnosis.exam.eventListener;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dqy on 2017/3/16.
 */
public class DiagnosisEvent extends ApplicationEvent {

    private EventSourceHandler sourceHandler;
    public DiagnosisEvent(Object source) {
        super(source);
    }
    public DiagnosisEvent(Object source,EventSourceHandler sourceHandler) {
        super(source);
        this.sourceHandler = sourceHandler;
    }

    public EventSourceHandler getSourceHandler() {
        return sourceHandler;
    }
}
