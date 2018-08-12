/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

/**
 * Ftp配置信息类
 * 
 * @date 2010-11-23
 * @since JDK 1.5
 * @author dingkui
 * @version 1.0
 * 
 */
public class FtpConfig {

    /**
     * 服务器地址
     */
    private String server;

    /**
     * 端口号
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 默认路径
     */
    private String location;

    /**
     * 编码
     */
    private String encode;

    /**
     * 本地缓存路径（下载文件未指定路径时，使用此路径）
     */
    private String localCachePath;

    /**
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server
     *            the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port
     *            the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the encode
     */
    public String getEncode() {
        return encode;
    }

    /**
     * @param encode
     *            the encode to set
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * @return the localCachePath
     */
    public String getLocalCachePath() {
        return localCachePath;
    }

    /**
     * @param localCachePath
     *            the localCachePath to set
     */
    public void setLocalCachePath(String localCachePath) {
        this.localCachePath = localCachePath;
    }

    /**
     * FtpConfig 默认构造函数
     */
    public FtpConfig() {}

    /**
     * FtpConfig 构造函数
     * 
     * @param server
     *            服务器IP
     * @param port
     *            服务器端口
     * @param username
     *            登录用户名
     * @param password
     *            登录密码
     * @param location
     *            登录时，默认跳转路径
     * @param encode
     *            文件系统编码
     */
    public FtpConfig(String server, int port, String username, String password, String location, String encode) {
        super();
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.location = location;
        this.encode = encode;
    }

}
