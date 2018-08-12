/*********************************************************************/
/**  文件名    ConfigServerApplication.java                               */
/**  功能名   配置中心 Application                                           */
/**  Copyright 2017  sinosoft. All Rights Reserved.                     */
/*********************************************************************/
package com.sinosoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 
 * 【服务中心 Application】
 * @author sinosoft
 * @date 2017年5月22日 下午4:35:06 
 * @version V1.0
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    /**
     * 启动主类
     * @param args
     */
    public static void main(String[] args) {
        //启动容器
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
