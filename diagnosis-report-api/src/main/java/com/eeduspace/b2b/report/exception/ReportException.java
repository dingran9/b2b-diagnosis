package com.eeduspace.b2b.report.exception;


/**
 * 异常类
 * Created by dqy on 2017/1/23.
 */
public class ReportException extends RuntimeException{
    private String message;
    private String code;
    public ReportException() {
        super();
    }

    public ReportException(String message) {
        super(message);
    }

    public ReportException(String code, String message) {
        super();
        this.code = code;
        this.message = message;

    }

    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReportException(Throwable cause) {
        super(cause);
    }

    protected ReportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
