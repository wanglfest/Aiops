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
 * 【SessionTimeoutException】
 * @author zhanght
 * @date 2017年3月9日 上午10:19:11 
 * @version V1.0
 */
public class SessionTimeoutException extends BaseRuntimeException {

    /**
     * 自动生成的串行版本表示
     */
    private static final long serialVersionUID = 3892058786550604205L;

    /**
     * 构造函数.
     */
    public SessionTimeoutException() {
        super();
    }

    /**
     * 构造函数.
     * 
     * @param psMsg 异常信息
     */
    public SessionTimeoutException(String psMsg) {
        super(psMsg);
    }
}
