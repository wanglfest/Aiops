/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年7月4日
 * 修改历史 : 
 *     1. [2017年7月4日]创建文件 by sinosoft
 */
package com.sinosoft.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**  
 * 【支付公共类】
 * @author sinosoft
 * @date 2017年7月4日 上午9:42:00 
 * @version V1.0  
*/
@Configuration
public class Config {/*
    *//**
     * 支付请求服务器地址
     *//*
    @Value("${pay.service.payurl}")
    private String paySvrUrl;
    *//**
     * #支付查询服务器地址
     *//*
    @Value("${pay.service.queryurl}")
    private String querySvrUrl;
    *//**
     * #支付平台密钥
     *//*
    @Value("${merchant.secret.key}")
    private String merchantSecretKey;
    //发送短信验证码的URL
    @Value("${jykl.sendMsg}")
    private String sendMsg;
    // 短信超时时间
    @Value("${msg.captchaExpires}")
    private String msgCaptchaExpires;
    //token
    @Value("${jykl.stoken}")
    private String stoken;

    public String getPaySvrUrl() {
        return paySvrUrl;
    }

    public void setPaySvrUrl(String paySvrUrl) {
        this.paySvrUrl = paySvrUrl;
    }

    public String getQuerySvrUrl() {
        return querySvrUrl;
    }

    public void setQuerySvrUrl(String querySvrUrl) {
        this.querySvrUrl = querySvrUrl;
    }

    public String getMerchantSecretKey() {
        return merchantSecretKey;
    }

    public void setMerchantSecretKey(String merchantSecretKey) {
        this.merchantSecretKey = merchantSecretKey;
    }

    public String getSendMsg() {
        return sendMsg;
    }

    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    public String getStoken() {
        return stoken;
    }

    public void setStoken(String stoken) {
        this.stoken = stoken;
    }

    public String getMsgCaptchaExpires() {
        return msgCaptchaExpires;
    }

    public void setMsgCaptchaExpires(String msgCaptchaExpires) {
        this.msgCaptchaExpires = msgCaptchaExpires;
    }

*/}
