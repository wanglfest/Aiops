package com.sinosoft.exception.newexception;

import java.io.Serializable;

import com.sinosoft.enums.ENUM_EXCEPTION_TYPE;

/**  
 * 【重复请求异常】
 * @author sinfosoft
 * @date 2017年6月5日 下午3:25:52 
 * @version V1.0  
 */
public class RepeatException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = -753795314657104303L;
    private String errCode ;
    private String errMsg;


    public RepeatException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public RepeatException(String errMsg) {
        super(errMsg);
        this.errCode = ENUM_EXCEPTION_TYPE.FAIL.getCode();
        this.errMsg = errMsg;
    }

    public RepeatException(ENUM_EXCEPTION_TYPE statusEnum) {
        super(statusEnum.getMessage());
        this.errCode = statusEnum.getCode();
        this.errMsg = statusEnum.getMessage();
    }
    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
