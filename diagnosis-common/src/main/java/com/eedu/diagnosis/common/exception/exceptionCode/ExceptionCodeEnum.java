package com.eedu.diagnosis.common.exception.exceptionCode;

/**
 * Created by dqy on 2017/1/23.
 */
public enum ExceptionCodeEnum {
    PARAMETER_MISS( "40001", "The request parameter is missed." ),
    PARAMETER_INVALID( "40003", "The request parameter is invalid." ),
    RESOURCE_NOTFOUND("40005", "Resource is not exist."),
    RESOURCE_INUSE("40006", "Resource is in use."),
    RESOURCE_DUPLICATE("40007", "Resource is duplicate."),
    RESOURCE_IMPERFECT("40010", "Resource is imperfect.");

    private String code;
    private String message;
    @Override
    public String toString() {
        return  code;
    }
    private ExceptionCodeEnum(String code,String message){
        this.code = code;
        this.message = message;
    }

    public static ExceptionCodeEnum toEnum(String code) {
        for (ExceptionCodeEnum ece : ExceptionCodeEnum.values()) {
            if (ece.toString().equalsIgnoreCase(code)) {
                return ece;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
