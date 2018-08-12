/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月24日
 * 修改历史 : 
 *     1. [2017年5月24日]创建文件 by sinosoft
 */
package com.sinosoft.common.redis;


import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author sinosoft
 * @date 2017年8月10日 上午11:23:44 
 * @version V1.0
 */
@Configuration
public class JedisClusterConfig {/*

    @Autowired
    private RedisProperties redisProperties;

    *//**
     * 注意：
     * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
     * @return
     *//*
    @Bean
    public JedisCluster getJedisCluster() {
        String[] serverArray = redisProperties.getClusterNodes().split(",");//获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        return new JedisCluster(nodes, redisProperties.getCommandTimeout());
    }

*/}