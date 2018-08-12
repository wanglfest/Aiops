/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.report;

/**
 * 
 * 【客户经理接口请求报文类】
 * @author zhaoWenQiang
 * @date 2017年5月19日 下午2:12:07 
 * @version V1.0
 */
public class Head {
    /**出单来源*/
    private String sourcecode;
    /**渠道*/
    private String channel;
    /**请求流水号(须唯一)*/
    private String transactionid;
    /**请求类型*/
    private String transactiontype;
    /**请求号--理赔申请号，订单号，保全号*/
    private String transactionno;
    /**请求日期(“yyyy-MM-dd”)*/
    private String transactionexedate;
    /**请求时间(“HH:mm:ss”)*/
    private String transactionexetime;
    /** 保单来源（区分个团险：个险lis、团险lisg）  */
    private String contsource;

    public String getContsource() {
		return contsource;
	}

	public void setContsource(String contsource) {
		this.contsource = contsource;
	}

	public String getSourcecode() {
        return sourcecode;
    }
    
    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setSourcecode(String sourcecode) {
        this.sourcecode = sourcecode;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getTransactionno() {
        return transactionno;
    }

    public void setTransactionno(String transactionno) {
        this.transactionno = transactionno;
    }

    public String getTransactionexedate() {
        return transactionexedate;
    }

    public void setTransactionexedate(String transactionexedate) {
        this.transactionexedate = transactionexedate;
    }

    public String getTransactionexetime() {
        return transactionexetime;
    }

    public void setTransactionexetime(String transactionexetime) {
        this.transactionexetime = transactionexetime;
    }

	@Override
	public String toString() {
		return "Head [sourcecode=" + sourcecode + ", channel=" + channel + ", transactionid=" + transactionid
				+ ", transactiontype=" + transactiontype + ", transactionno=" + transactionno + ", transactionexedate="
				+ transactionexedate + ", transactionexetime=" + transactionexetime + ", contsource=" + contsource
				+ "]";
	}
    
    
    
}
