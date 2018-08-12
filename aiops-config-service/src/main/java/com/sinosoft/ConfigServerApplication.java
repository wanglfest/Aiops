/*********************************************************************/
/**  文件名    ConfigServerApplication.java                               */
/**  功能名   配置中心 Application                                           */
/**  Copyright 2017  sinosoft. All Rights Reserved.                     */
/*********************************************************************/

package com.sinosoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 
 * 【配置中心 Application】
 * @author sinosoft
 * @date 2017年5月22日 下午4:35:06 
 * @version V1.0
 */

@EnableConfigServer
@SpringBootApplication
@EnableDiscoveryClient
public class ConfigServerApplication {
    /**
     * 启动主类
     * @param args 启动参数
     */
    public static void main(String[] args) {
        //启动配置中心容器
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
