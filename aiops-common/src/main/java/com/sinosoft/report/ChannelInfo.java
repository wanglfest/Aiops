/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年10月24日
 * 修改历史 : 
 *     1. [2017年10月24日]创建文件 by sinosoft
 */
package com.sinosoft.report;

/**  
 * 【类或接口功能描述】
 * @author sinfosoft
 * @date 2017年10月24日 下午1:42:48 
 * @version V1.0  
*/
public class ChannelInfo {

    private String agent;

    private String clientSystem;

    private String sellingChannel;
    
    private String agency;
    
    private String clientManager;

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getClientSystem() {
		return clientSystem;
	}

	public void setClientSystem(String clientSystem) {
		this.clientSystem = clientSystem;
	}

	public String getSellingChannel() {
		return sellingChannel;
	}

	public void setSellingChannel(String sellingChannel) {
		this.sellingChannel = sellingChannel;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getClientManager() {
		return clientManager;
	}

	public void setClientManager(String clientManager) {
		this.clientManager = clientManager;
	}
    
   
}
