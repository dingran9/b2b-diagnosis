package com.eedu.diagnosis.common.exception;


import com.eedu.diagnosis.common.enumration.MoudleEnum;
import com.eedu.diagnosis.common.exception.exceptionCode.ExceptionCodeEnum;

/**
 * 诊断异常类
 * Created by dqy on 2017/1/23.
 */
public class DiagnosisException  extends RuntimeException{
    private String message;
    private String code;
    public DiagnosisException() {
        super();
    }

    public DiagnosisException(String message) {
        super(message);
    }

    public DiagnosisException(String code, String message) {
        super();
        this.code = code;
        this.message = message;

    }

    public DiagnosisException(ExceptionCodeEnum codeEnum, String message) {
        super();
        this.code = codeEnum.toString();
        this.message = message;

    }

    public DiagnosisException(ExceptionCodeEnum codeEnum, MoudleEnum moudleEnum) {
        super();
        this.code = codeEnum.toString();
        this.message = moudleEnum.toString() + "----" +codeEnum.getMessage();

    }

    public DiagnosisException(String message, Throwable cause) {
        super(message, cause);
    }

    public DiagnosisException(Throwable cause) {
        super(cause);
    }

    protected DiagnosisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
