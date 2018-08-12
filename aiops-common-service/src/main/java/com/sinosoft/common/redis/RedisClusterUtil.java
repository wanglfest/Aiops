/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月24日
 * 修改历史 : 
 *     1. [2017年5月24日]创建文件 by sinosoft
 */
package com.sinosoft.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * 
 * 【类或接口功能描述】
 * @author sinosoft
 * @date 2017年8月10日 上午11:23:56 
 * @version V1.0
 */
@Component
public class RedisClusterUtil {/*
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClusterUtil.class);

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private RedisProperties redisProperties;

    private static final String KEY_SPLIT = ":"; //用于隔开缓存前缀与缓存键值 

    *//**
     * 设置缓存 
     * @param prefix 缓存前缀（用于区分缓存，防止缓存键值重复）
     * @param key    缓存key
     * @param value  缓存value
     *//*
    public void setPre(String prefix, String key, String value) {
        jedisCluster.set(prefix + KEY_SPLIT + key, value);
        LOGGER.debug("RedisUtil:set cache key={},value={}", prefix + KEY_SPLIT + key, value);
    }

    *//**
    * 设置缓存，并且自己指定过期时间
     * @param prefix
     * @param key
     * @param value
     * @param expireTime 过期时间
     *//*
    public void setPreWithExpireTime(String prefix, String key, String value, int expireTime) {
        jedisCluster.setex(prefix + KEY_SPLIT + key, expireTime, value);
        LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value, expireTime);
    }

    *//**
     * 设置缓存，并且由配置文件指定过期时间
     * @param prefix
     * @param key
     * @param value
     *//*
    public void setPreWithExpireTime(String prefix, String key, String value) {
        int EXPIRE_SECONDS = redisProperties.getExpireSeconds();
        jedisCluster.setex(prefix + KEY_SPLIT + key, EXPIRE_SECONDS, value);
        LOGGER.debug("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", prefix + KEY_SPLIT + key, value, EXPIRE_SECONDS);
    }

    *//**
     * 获取指定key的缓存
     * @param prefix
     * @param key
     *//*
    public String getPre(String prefix, String key) {
        String value = jedisCluster.get(prefix + KEY_SPLIT + key);
        LOGGER.debug("RedisUtil:get cache key={},value={}", prefix + KEY_SPLIT + key, value);
        return value;
    }

    *//**
     * 删除指定key的缓存
     * @param prefix
     * @param key
     *//*
    public void deleteWithPrefix(String prefix, String key) {
        jedisCluster.del(prefix + KEY_SPLIT + key);
        LOGGER.debug("RedisUtil:delete cache key={}", prefix + KEY_SPLIT + key);
    }

    *//**
     * 
     * 设置缓存 
     * @param key
     * @param value
     * @return
     *//*
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    *//**
     * 
     * 获取指定key的缓存
     * @param key
     * @return
     *//*
    public String get(String key) {
        return jedisCluster.get(key);
    }

    *//**
     * 
     * 删除指定key的缓存
     * @param key
     *//*
    public void delete(String key) {
        jedisCluster.del(key);
        LOGGER.debug("RedisUtil:delete cache key={}", key);
    }

    *//**
    *
    * @Description: 设置存存活时间
    * @param key
    * @param second
    * @return
    *//*
    public Long expire(String key, int second) {
        return jedisCluster.expire(key, second);
    }

    *//**
    *
    * @Description: 判断key多久过期
    * @param key
    * @return 秒
    *             >= 0     剩余秒数
    *             = -1    永久存活
    *             = -2    已经消除
    *//*
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    *//**
    * 设置缓存，并且自己指定过期时间
     * @param key
     * @param value
     * @param expireTime 过期时间
     *//*
    public void setWithExpireTime(String key, String value, int expireTime) {
        jedisCluster.setex(key, expireTime, value);
    }

    *//**
     * 设置缓存，并且由配置文件指定过期时间
     * @param key
     * @param value
     *//*
    public void setWithExpireTime(String key, String value) {
        int EXPIRE_SECONDS = redisProperties.getExpireSeconds();
        jedisCluster.setex(key, EXPIRE_SECONDS, value);
    }

    *//**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     *//*
    public boolean exists(final String key) {
        return jedisCluster.exists(key);
    }
*/}
