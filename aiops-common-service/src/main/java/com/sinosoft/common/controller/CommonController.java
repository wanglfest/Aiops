package com.sinosoft.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author sinosoft
 * @date 2017年5月25日 上午10:04:52
 * @version V1.0
 */
@RestController
@RequestMapping(value = "/query")
public class CommonController {

	@ApiOperation(value = "请求", notes = "调用")
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public String query(@RequestParam String epayInfo, @RequestParam boolean query) {
		String logPrefix = "[CommonController.payment][";
		String returnString = "";
	
		return returnString;
	}
	
}
