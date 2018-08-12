/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.common.logservice.client.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.common.logservice.client.service.ClientLogService;
import com.sinosoft.dto.log.LkclienttranlogWithBLOBs;

/**
 * 【前端交易报文记录Controller】
 * 
 * @author sinosoft
 * @date 2017年5月25日 上午10:04:52
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/clientLog")
public class ClientLogServiceController {
	private static final Logger log = LoggerFactory.getLogger(ClientLogServiceController.class);
	@Autowired
	private ClientLogService clientLogService;

	/**
	 * 日志添加 方法功能描述
	 */

	@RequestMapping(value = "/addTranLog", method = RequestMethod.POST)
	public void addTranLog(@RequestBody LkclienttranlogWithBLOBs lkclienttranlogWithBLOBs) {
		String logPrefix = "[ClientLogServiceController.addTranLog][";
		try {
			clientLogService.addTranLog(lkclienttranlogWithBLOBs);
		} catch (Exception e) {
			log.error("{} {} ]", logPrefix, e.getMessage());
		}

	}

	/**
	 * 日志查询 方法功能描述
	 */

	@RequestMapping(value = "/queryLog", method = RequestMethod.GET)
	public Boolean queryLog(@RequestParam String transactionNo, String sourceCode) throws Exception {
		String logPrefix = "[ClientLogServiceController.queryLog][";
		Boolean exist = false;
		try {
			exist = clientLogService.queryLog(transactionNo, sourceCode);
		} catch (Exception e) {
			log.error("{} {} ]", logPrefix, e.getMessage());
		}
		return exist;
	}

	@RequestMapping(value = "/queryText", method = RequestMethod.GET)
	public String queryText(@RequestParam String transactionNo, String sourceCode) throws Exception {
		String logPrefix = "[ClientLogServiceController.queryText][";
		String reqText = null;
		try {
			reqText = clientLogService.queryText(transactionNo, sourceCode);
		} catch (Exception e) {
			log.error("{} {} ]", logPrefix, e.getMessage());
		}
		return reqText;

	}

}
