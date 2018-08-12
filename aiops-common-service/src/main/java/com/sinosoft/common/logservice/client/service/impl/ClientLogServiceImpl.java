/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.common.logservice.client.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sinosoft.common.logservice.client.mapper.LkclienttranlogMapper;
import com.sinosoft.common.logservice.client.service.ClientLogService;
import com.sinosoft.dto.log.Lkclienttranlog;
import com.sinosoft.dto.log.LkclienttranlogExample;
import com.sinosoft.dto.log.LkclienttranlogWithBLOBs;

/**  
 * 【前端交易报文记录ServiceImpl】
 * @author sinosoft
 * @date 2017年5月25日 上午10:04:52 
 * @version V1.0  
 */
@Component
public class ClientLogServiceImpl implements ClientLogService {
    @Autowired
    private LkclienttranlogMapper lkclienttranlogMapper;

    /**
     * 根据交易流水号、商户编码查询交易是否重复
     */
    @Override
    public Boolean queryLog(String transactionNo, String sourceCode) {
        Boolean exist = false;
        LkclienttranlogExample lkclienttranlogExample = new LkclienttranlogExample();
        lkclienttranlogExample.createCriteria().andTrannoEqualTo(transactionNo).andSourcecodeEqualTo(sourceCode);
        List<Lkclienttranlog> list = lkclienttranlogMapper.selectByExample(lkclienttranlogExample);
        if (list.size() > 0) {
            exist = true;
        }
        return exist;
    }

    /**
     * 日志落库
     */
    @Override
    public void addTranLog(LkclienttranlogWithBLOBs lkclienttranlogWithBLOBs) {
        //根据交易流水号和商户编码查询请求是否重复
        Boolean flag = queryLog(lkclienttranlogWithBLOBs.getTranno(), lkclienttranlogWithBLOBs.getSourcecode());
        if (!flag) {
            lkclienttranlogMapper.insert(lkclienttranlogWithBLOBs);
        }
    }

    @Override
    public String queryText(String transactionNo, String sourceCode) {
        LkclienttranlogExample lkclienttranlogExample = new LkclienttranlogExample();
        lkclienttranlogExample.createCriteria().andTrannoEqualTo(transactionNo).andSourcecodeEqualTo(sourceCode).andStateEqualTo("Y");
        List<LkclienttranlogWithBLOBs> list = lkclienttranlogMapper.selectByExampleWithBLOBs(lkclienttranlogExample);
        if (list.size() > 0) {
            return list.get(0).getRequestcontent();
        } else {
            return null;
        }
    }

}
