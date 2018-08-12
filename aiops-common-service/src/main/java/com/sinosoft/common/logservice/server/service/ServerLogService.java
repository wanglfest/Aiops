/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.common.logservice.server.service;

import com.sinosoft.dto.log.LKservertranslogWithBLOBs;

/**  
 * 【后端交易报文记录Service】
 * @author sinosoft
 * @date 2017年5月25日 上午10:04:52 
 * @version V1.0  
 */
public interface ServerLogService {
    /**查询请求是否重复*/
    public Boolean queryLog(String transactionNo, String sourceCode);

    /**记录日志信息*/
    public void addTranLog(LKservertranslogWithBLOBs lkTranlog);
}
