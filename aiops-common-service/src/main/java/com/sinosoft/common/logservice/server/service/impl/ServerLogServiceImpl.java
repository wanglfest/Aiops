/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.common.logservice.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.common.logservice.server.mapper.LKservertranslogMapper;
import com.sinosoft.common.logservice.server.service.ServerLogService;
import com.sinosoft.dto.log.LKservertranslog;
import com.sinosoft.dto.log.LKservertranslogExample;
import com.sinosoft.dto.log.LKservertranslogWithBLOBs;

/**  
 * 【后端交易报文记录ServiceImpl】
 * @author sinosoft
 * @date 2017年5月25日 上午10:04:52 
 * @version V1.0  
 */
@Component
public class ServerLogServiceImpl implements ServerLogService {
    @Autowired
    private LKservertranslogMapper lKservertranslogMapper;

    /**
     * 根据交易流水号、商户编码查询交易是否重复
     */
    @Override
    public Boolean queryLog(String transactionNo, String sourceCode) {
        Boolean exist = false;
        LKservertranslogExample lKservertranslogExample = new LKservertranslogExample();
        lKservertranslogExample.createCriteria().andTrannoEqualTo(transactionNo).andSourcecodeEqualTo(sourceCode);
        List<LKservertranslog> list = lKservertranslogMapper.selectByExample(lKservertranslogExample);
        if (list.size() > 0) {
            exist = true;
        }
        return exist;

    }

    /**
     * 日志落库
     */
    @Override
    public void addTranLog(LKservertranslogWithBLOBs lkTranlog) {
        //根据交易流水号和商户编码查询请求是否重复
        queryLog(lkTranlog.getTranno(), lkTranlog.getSourcecode());
        lKservertranslogMapper.insert(lkTranlog);
    }

}
