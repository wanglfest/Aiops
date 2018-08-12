/*********************************************************************/
/**  文件名    ConfigServerApplication.java                               */
/**  功能名   配置中心 Application                                           */
/**  Copyright 2017  sinosoft. All Rights Reserved.                     */
/*********************************************************************/
package com.sinosoft.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;

/**
 * 用于监听eureka服务停机通知 Created by ace on 2017/7/8.
 */
@SuppressWarnings("rawtypes")
@Configuration
@EnableScheduling
public class EurekaInstanceCanceledListener implements ApplicationListener {
    /**
     * 日志
     */
    private Logger log = LoggerFactory.getLogger(EurekaInstanceCanceledListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        // 服务挂掉事件
        if (applicationEvent instanceof EurekaInstanceCanceledEvent) {
            EurekaInstanceCanceledEvent event = (EurekaInstanceCanceledEvent) applicationEvent;
            // 获取当前Eureka实例中的节点信息
            PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
            Applications applications = registry.getApplications();
            // 遍历获取已注册节点中与当前失效节点ID一致的节点信息
            applications.getRegisteredApplications().forEach((registeredApplication) -> {
                registeredApplication.getInstances().forEach((instance) -> {
                    if (instance.getInstanceId().equals(event.getServerId())) {
                        log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务：" + instance.getAppName() + " 已停止！！！]");
                    }
                });
            });

        }
        if (applicationEvent instanceof EurekaInstanceRegisteredEvent) {
            EurekaInstanceRegisteredEvent event = (EurekaInstanceRegisteredEvent) applicationEvent;
            if (event.getInstanceInfo() != null) {
                log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务：" + event.getInstanceInfo().getAppName() + " 注册成功！！！]");
            }
        }
        if (applicationEvent instanceof EurekaInstanceRenewedEvent) {
            EurekaInstanceRenewedEvent event = (EurekaInstanceRenewedEvent) applicationEvent;
            if (event.getInstanceInfo() != null) {
                log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [心跳检测服务：" + event.getInstanceInfo().getAppName() + " 注册成功！！！]");
            }
        }
        if (applicationEvent instanceof EurekaRegistryAvailableEvent) {
            // EurekaInstanceRenewedEvent event = (EurekaInstanceRenewedEvent) applicationEvent;
            log.info("[EurekaInstanceCanceledListener.onApplicationEvent] [服务 Aualiable。。]");
        }

    }

}
