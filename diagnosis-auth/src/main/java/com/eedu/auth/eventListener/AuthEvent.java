package com.eedu.auth.eventListener;

import org.springframework.context.ApplicationEvent;

/**
 * Created by liuhongfei on 2017/6/8.
 */
public class AuthEvent extends ApplicationEvent {

    public AuthEvent(Object source) {
        super(source);
    }
}
