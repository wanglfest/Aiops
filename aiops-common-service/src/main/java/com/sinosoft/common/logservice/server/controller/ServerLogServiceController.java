/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.common.logservice.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.common.logservice.server.service.ServerLogService;
import com.sinosoft.dto.log.LKservertranslogWithBLOBs;

/**  
 * 【后端交易报文记录Controller】
 * @author sinosoft
 * @date 2017年5月25日 上午10:04:52 
 * @version V1.0  
 */
@RestController
@RequestMapping(value = "/serverLog")
public class ServerLogServiceController {
    private static final Logger log = LoggerFactory.getLogger(ServerLogServiceController.class);
    @Autowired
    private ServerLogService logService;

    /**
     * 日志添加
     * 方法功能描述
     * @param lkTranlog
     * @param lkTranMessageWithBLOBs
     * @throws Exception
     */

    @RequestMapping(value = "/addTranLog", method = RequestMethod.POST)
    public void addTranLog(@RequestBody LKservertranslogWithBLOBs lKservertranslogWithBLOBs) {
    	String logPrefix = "[ServerLogServiceController.addTranLog][";
        try {
            logService.addTranLog(lKservertranslogWithBLOBs);
        } catch (Exception e) {
        	log.error("{} {} ]", logPrefix, e.getMessage());
        }

    }

    /**
     * 日志查询
     * 是否存在，存在返回true，不存在返回false
     * @param seriaNo
     */

    @RequestMapping(value = "/queryLog", method = RequestMethod.GET)
    public Boolean queryLog(@RequestParam String transactionNo, String sourceCode) throws Exception {
    	String logPrefix = "[ServerLogServiceController.queryLog][";
        Boolean exist = false;
        try {
            exist = logService.queryLog(transactionNo, sourceCode);
        } catch (Exception e) {
            exist = true;
        	log.error("{} {} ]", logPrefix, e.getMessage());
        }
        return exist;
    }

}
