/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.exception;

/**
 * 
 * 【BaseRuntimeException】
 * @author zhanght
 * @date 2017年3月9日 上午10:20:13 
 * @version V1.0
 */

public abstract class BaseRuntimeException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 201110724352165886L;

    /**
     * 嵌套的异常
     */
    private Throwable oCause = null;

    /**
     * 构造函数.
     */
    public BaseRuntimeException() {
        super();
    }

    /**
     * 构造函数.
     * 
     * @param psMsg 异常信息
     */
    public BaseRuntimeException(String psMsg) {
        super(psMsg);
    }

    /**
     * 构造函数.
     * 
     * @param psMsg 异常信息
     * @param poCause 嵌套的异常
     */
    public BaseRuntimeException(String psMsg, Throwable poCause) {
        super(psMsg);
        this.oCause = poCause;
    }

    /**
     * 嵌套异常取得
     * 
     * @return 嵌套异常
     */
    public Throwable getCause() {
        return oCause;
    }

    /**
     * 异常信息取得
     * 
     * @return 异常信息
     */
    public String getComment() {
        String sMessage = super.getMessage();
        Throwable oCause = getCause();
        if (oCause != null) {
            StringBuffer sbMessage = new StringBuffer();
            if (sMessage != null) {
                sbMessage.append(sMessage).append("; ");
            }
            sbMessage.append("嵌套的异常为: ").append(oCause);
            return sbMessage.toString();
        } else {
            return sMessage;
        }
    }

    /**
     * 异常输出
     */
    public void printStackTrace() {
        super.printStackTrace();
        if (oCause != null) {
            oCause.printStackTrace();
        }
    }
}
