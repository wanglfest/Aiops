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
 * 【业务异常】
 * @author sinosoft
 * @date 2017年3月9日 上午10:20:30 
 * @version V1.0
 */
public class IopBizException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4116827912194578646L;

    public IopBizException() {}

    public IopBizException(String message) {
        super(message);
    }

}
