package com.sinosoft.exception.newexception;

import java.io.Serializable;

import com.sinosoft.enums.ENUM_EXCEPTION_TYPE;

/**  
 * 【数据校验异常】
 * @author sinfosoft
 * @date 2017年6月5日 下午3:25:52 
 * @version V1.0  
 */
public class CheckException extends RuntimeException implements Serializable{

    private static final long serialVersionUID = 3582937900671865210L;
    private String errCode ;
    private String errMsg;


    public CheckException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public CheckException(String errMsg) {
        super(errMsg);
        this.errCode = ENUM_EXCEPTION_TYPE.FAIL.getCode();
        this.errMsg = errMsg;
    }

    public CheckException(ENUM_EXCEPTION_TYPE statusEnum) {
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
