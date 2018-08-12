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
 * 【EShopRuntimeException】
 * @author zhanght
 * @date 2017年3月9日 上午10:20:30 
 * @version V1.0
 */
public class EShopRuntimeException extends RuntimeException {
    /**
     * 自动生成的串行版本表示
     */
    private static final long serialVersionUID = 275495100850066420L;

    /**
     * 构造函数.
     */
    public EShopRuntimeException() {
        super();
    }

    /**
     * 构造函数.
     */
    public EShopRuntimeException(String message) {
        super(message);
    }

    /**
     * 构造函数.
     */
    public EShopRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数.
     */
    public EShopRuntimeException(Throwable cause) {
        super(cause);
    }
}
