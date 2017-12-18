package com.eedu.diagnosis.manager.model;

/**
 * 中兴
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-09 11:12
 **/
public class ZTEToken {
    public String getSERVICE_CODE() {
        return SERVICE_CODE;
    }

    public void setSERVICE_CODE(String SERVICE_CODE) {
        this.SERVICE_CODE = SERVICE_CODE;
    }

    public String getCONSUMER_ID() {
        return CONSUMER_ID;
    }

    public void setCONSUMER_ID(String CONSUMER_ID) {
        this.CONSUMER_ID = CONSUMER_ID;
    }

    private String SERVICE_CODE;
    private String CONSUMER_ID;
}
