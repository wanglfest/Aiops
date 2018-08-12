package com.sinosoft.exception;

/**
 * 
 * 【EShopException】
 * @author zhanght
 * @date 2017年3月9日 上午10:21:16 
 * @version V1.0
 */
public class EShopException extends Exception {
    /**
     * 自动生成的串行版本表示
     */
    private static final long serialVersionUID = -402941666292138309L;

    /**
     * 构造函数.
     */
    public EShopException() {
        super();
    }

    /**
     * 构造函数.
     */
    public EShopException(String message) {
        super(message);
    }

    /**
     * 构造函数.
     */
    public EShopException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造函数.
     */
    public EShopException(Throwable cause) {
        super(cause);
    }
}
