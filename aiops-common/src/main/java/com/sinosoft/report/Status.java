/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.report;

import java.util.List;

/**
 * 
 * 返回报文公共状态信息
 * 
 * @author sinosoft
 * @date 2017年6月20日 下午2:01:12 
 * @version V1.0
 */
public class Status {
    /**状态编码*/
    private String statuscode;
    /**状态描述*/
    private List<String> statusmessage;
    /**是否成功*/
    private String success;
    
    public Status() {
		super();
	}

    public Status(String statuscode, List<String> statusmessage) {
		super();
		this.statuscode = statuscode;
		this.statusmessage = statusmessage;
	}

	public String getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(String statuscode) {
        this.statuscode = statuscode;
    }

    public List<String> getStatusmessage() {
        return statusmessage;
    }

    public void setStatusmessage(List<String> statusmessage) {
        this.statusmessage = statusmessage;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

	@Override
	public String toString() {
		return "Status [statuscode=" + statuscode + ", statusmessage=" + statusmessage + ", success=" + success + "]";
	}

    
    
}
