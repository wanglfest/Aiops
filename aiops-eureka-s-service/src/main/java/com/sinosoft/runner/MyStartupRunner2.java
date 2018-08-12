/*********************************************************************/
/**  文件名    ConfigServerApplication.java                               */
/**  功能名   配置中心 Application                                           */
/**  Copyright 2017  sinosoft. All Rights Reserved.                     */
/*********************************************************************/
package com.sinosoft.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 
 * 【类或接口功能描述】
 * @author sinosoft
 * @date 2017年12月19日 上午10:28:38 
 * @version V1.0
 */
@Component
@Order(value = 2)
public class MyStartupRunner2 implements CommandLineRunner {
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(MyStartupRunner2.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("[MyStartupRunner2.run] [>>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 11111111<<<<<<<<<<<<<]");
    }

}