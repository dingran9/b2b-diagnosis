package com.eedu.diagnosis.protal.model;

import java.util.List;

/**
 * 中兴验证返回
 *
 * @author zhucw
 * @e-email: zhuchaowei@e-eduspace.com
 * @create 2017-09-09 11:36
 **/
public class ZTEResponse {
    private Body BODY;
    private SYSHEAD SYS_HEAD;

    public SYSHEAD getSYS_HEAD() {
        return SYS_HEAD;
    }

    public void setSYS_HEAD(SYSHEAD SYS_HEAD) {
        this.SYS_HEAD = SYS_HEAD;
    }

    public Body getBODY() {
        return BODY;
    }

    public void setBODY(Body BODY) {
        this.BODY = BODY;
    }
    public class SYSHEAD{
        private String RET_STATUS;
        private List<RET> RET;

        public List<ZTEResponse.RET> getRET() {
            return RET;
        }

        public void setRET(List<ZTEResponse.RET> RET) {
            this.RET = RET;
        }

        public String getRET_STATUS() {
            return RET_STATUS;
        }

        public void setRET_STATUS(String RET_STATUS) {
            this.RET_STATUS = RET_STATUS;
        }
    }
    class RET{
        private String RET_MSG;
        private String RET_CODE;
        private String SERVICE_CODE;

        public String getRET_MSG() {
            return RET_MSG;
        }

        public void setRET_MSG(String RET_MSG) {
            this.RET_MSG = RET_MSG;
        }

        public String getRET_CODE() {
            return RET_CODE;
        }

        public void setRET_CODE(String RET_CODE) {
            this.RET_CODE = RET_CODE;
        }

        public String getSERVICE_CODE() {
            return SERVICE_CODE;
        }

        public void setSERVICE_CODE(String SERVICE_CODE) {
            this.SERVICE_CODE = SERVICE_CODE;
        }
    }
    public class Body{
        private String GRADE_NAME;
        private String USER_ID;
        private String GRADE_ID;
        private String USER_TYPE;
        private String TMToken;

        public String getGRADE_NAME() {
            return GRADE_NAME;
        }

        public void setGRADE_NAME(String GRADE_NAME) {
            this.GRADE_NAME = GRADE_NAME;
        }

        public String getUSER_ID() {
            return USER_ID;
        }

        public void setUSER_ID(String USER_ID) {
            this.USER_ID = USER_ID;
        }

        public String getGRADE_ID() {
            return GRADE_ID;
        }

        public void setGRADE_ID(String GRADE_ID) {
            this.GRADE_ID = GRADE_ID;
        }

        public String getUSER_TYPE() {
            return USER_TYPE;
        }

        public void setUSER_TYPE(String USER_TYPE) {
            this.USER_TYPE = USER_TYPE;
        }

        public String getTMToken() {
            return TMToken;
        }

        public void setTMToken(String TMToken) {
            this.TMToken = TMToken;
        }
    }
}
