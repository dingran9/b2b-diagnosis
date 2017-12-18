package com.eedu.diagnosis.manager.eventlistener;

import org.springframework.context.ApplicationEvent;

/**
 * Created by dqy on 2017/6/8.
 */
public class ManagerSystemEvent extends ApplicationEvent {
    private EventHandler sourceHandler;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public ManagerSystemEvent(Object source) {
        super(source);
    }

    public ManagerSystemEvent(Object source,EventHandler sourceHandler) {
        super(source);
        this.sourceHandler = sourceHandler;
    }

    public EventHandler getSourceHandler() {
        return sourceHandler;
    }
}
