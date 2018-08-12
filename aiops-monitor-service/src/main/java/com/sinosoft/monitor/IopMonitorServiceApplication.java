package com.sinosoft.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.config.EnableAdminServer;
/**
 * 
 * 【boot admin 监控服务】
 * @author sinfosoft
 * @date 2017年12月19日 上午10:39:52 
 * @version V1.0
 */
@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class IopMonitorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IopMonitorServiceApplication.class, args);
	}
}
