package com.sinosoft.exception;

/**
 *      条件检查异常
 */
@SuppressWarnings("serial")
public class ConditionCheckedException  extends  RuntimeException{

    /**
     * 错误码
     */
    private String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ConditionCheckedException (String message, Throwable cause){
        super(message, cause);
    }

    public ConditionCheckedException (String message){
        super(message);
    }

    public ConditionCheckedException (String message, String errorCode){
        super(message);
        this.setErrorCode(errorCode);
    }

    public ConditionCheckedException(String message, Throwable cause,  String errorCode){
        this(message, cause);
        this.setErrorCode(errorCode);
    }

}
