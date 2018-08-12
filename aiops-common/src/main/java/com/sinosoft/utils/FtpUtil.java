/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * Ftp操作工具类
 * @date 2010-11-20
 * @since JDK 1.5
 * @author dingkui
 * @version 1.0
 *
 */
public class FtpUtil {

    /**
     * apache的FTPClient，本工具类主要根据它，进行封装
     */
    private FTPClient ftpClient;

    /**
     * 数据类型：二进制数据
     */
    public static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;

    /**
     * 数据类型：ascii型
     */
    public static final int ASCII_FILE_TYPE = FTP.ASCII_FILE_TYPE;

    /**
     * 根据指定config相关信息，连接远程Ftp服务器
     * @param ftpConfig ftp服务配置相关
     * @throws SocketException, IOException
     */
    public void connectServer(FtpConfig ftpConfig) throws SocketException, IOException {
        String server = ftpConfig.getServer();
        int port = ftpConfig.getPort();
        String username = ftpConfig.getUsername();
        String password = ftpConfig.getPassword();
        String location = ftpConfig.getLocation();
        String encode = ftpConfig.getEncode();
        connectServer(server, port, username, password, location, encode);
    }

    /**
     * 链接远程ftp服务器
     * @param server 服务器ip
     * @param port 服务器端口
     * @param user 用户名
     * @param password 密码
     * @param path 登陆后的默认路径
     * @param encode 服务器文件系统编码
     * @throws SocketException
     * @throws IOException
     */
    public void connectServer(String server, int port, String user, String password, String path, String encode) throws SocketException, IOException {
        ftpClient = new FTPClient();
        if (null != encode && 0 != encode.length()) {
            ftpClient.setControlEncoding(encode);
        }
        ftpClient.connect(server, port);
        ftpClient.login(user, password);
        setFileType(BINARY_FILE_TYPE);
        if (null != path && 0 != path.length()) {
            ftpClient.changeWorkingDirectory(path);
        }
    }

    /**
     * 设置文件类型
     * @param fileType 文件类型编号
     * @throws IOException
     */
    public void setFileType(int fileType) throws IOException {
        ftpClient.setFileType(fileType);
    }

    /**
     * 关闭服务器连接
     * @throws IOException
     */
    public void closeServer() throws IOException {
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
    }

    /**
     * 改变路径
     * @param path 新路径
     * @throws IOException
     */
    public boolean changeDirectory(String path) throws IOException {
        return ftpClient.changeWorkingDirectory(path);
    }

    /**
     * 创建目录
     * @param pathName 目录名
     * @throws IOException
     */
    public boolean createDirectory(String pathName) throws IOException {
        return ftpClient.makeDirectory(pathName);
    }

    /**
     * 删除目录
     * @param path 目录名
     * @throws IOException
     */
    public boolean removeDirectory(String path) throws IOException {
        return ftpClient.removeDirectory(path);
    }

    /**
     * 删除指定目录下全部内容（子目录和文件）
     * @param path 
     * @param isAll 确认标志位，true的时候全部删除
     * @throws IOException
     */
    public boolean removeDirectory(String path, boolean isAll) throws IOException {
        if (!isAll) {
            return removeDirectory(path);
        }

        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return removeDirectory(path);
        }

        for (FTPFile ftpFile : ftpFileArr) {
            String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                removeDirectory(path + "/" + name, true);
            } else if (ftpFile.isFile()) {
                deleteFile(path + "/" + name);
            } else if (ftpFile.isSymbolicLink()) {

            } else if (ftpFile.isUnknown()) {

            }
        }
        return ftpClient.removeDirectory(path);
    }

    /**
     * 判断指定目录是否存在
     * @param path 指定路径
     * @return 存在则返回true，否则返回false
     * @throws IOException
     */
    public boolean existDirectory(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获得指定目录下的文件列表(含子目录)
     * @param path 指定目录
     * @return 文件列表
     * @throws IOException
     */
    public List<String> getFileList(String path) throws IOException {

        if (!ftpClient.isConnected()) {
            this.connectServer(new FtpConfig());
        }

        FTPFile[] ftpFiles = ftpClient.listFiles(path);

        List<String> retList = new ArrayList<String>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                retList.add(ftpFile.getName());
            }
        }
        return retList;
    }

    /**
     * 删除文件
     * @param pathName 文件名
     * @return 成功返回true，失败返回false
     * @throws IOException
     */
    public boolean deleteFile(String pathName) throws IOException {
        return ftpClient.deleteFile(pathName);
    }

    /**
     * 上传文件（上传同时改名）
     * @param fileName 原文件名
     * @param newName 上传后保存的文件名
     * @return 成功返回true，失败返回false
     * @throws IOException
     */
    public boolean uploadFile(String fileName, String newName) throws IOException {
        boolean flag = false;
        InputStream iStream = null;
        try {
            iStream = new FileInputStream(fileName);
            flag = ftpClient.storeFile(newName, iStream);
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        return flag;
    }

    /**
     * 上传文件（保持原文件名）
     * @param fileName 文件名
     * @return 成功返回true，失败返回false
     * @throws IOException
     */
    public boolean uploadFile(String fileName) throws IOException {
        return uploadFile(fileName, fileName);
    }

    /**
     * 上传文件
     * @param iStream 上传文件的InputStream
     * @param newName 上传后保存的文件名
     * @return 成功返回true，失败返回false
     * @throws IOException
     */
    public boolean uploadFile(InputStream iStream, String newName) throws IOException {
        boolean flag = false;
        try {
            // can execute [OutputStream storeFileStream(String remote)]   
            // Above method return's value is the local file stream.   
            flag = ftpClient.storeFile(newName, iStream);
        } catch (IOException e) {
            flag = false;
            return flag;
        } finally {
            if (iStream != null) {
                iStream.close();
            }
        }
        return flag;
    }

    /**
     * 下载文件
     * @param remoteFileName 远程文件名
     * @param localFileName 下载后，本地保存的文件名
     * @return 成功返回true，失败返回false
     * @throws IOException
     */
    public boolean download(String remoteFileName, String localFileName) throws IOException {
        boolean flag = false;
        File outfile = new File(localFileName);
        OutputStream oStream = null;
        try {
            //判断服务是否正常连接
            if (!ftpClient.isConnected()) {
                System.out.println("FTP连接失败，重新连接！");
                this.connectServer(new FtpConfig());
            }
            oStream = new FileOutputStream(outfile);
            //指定使用二进制进行传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            flag = ftpClient.retrieveFile(remoteFileName, oStream);
        } catch (IOException e) {
            System.out.println("文件：" + remoteFileName + "下载失败，原因是：" + e.getMessage());
            flag = false;
            return flag;
        } finally {
            oStream.close();
        }
        return flag;
    }

    /**
     * 取得远程文件的Stream
     * @param sourceFileName
     * @return 取得远程文件的Stream
     * @throws IOException
     */
    public InputStream downFile(String sourceFileName) throws IOException {
        return ftpClient.retrieveFileStream(sourceFileName);
    }

    /**
     * 下载文件，将远程FTP文件，写入HttpServletResponse
     * @param remoteFileName 远程文件名
     * @param response servlet的响应
     * @throws IOException
     */
    public void download(String remoteFileName, HttpServletResponse response) throws IOException {

        if (ftpClient == null) {
            ftpClient = new FTPClient();
        }
        if (!ftpClient.isConnected()) {
            this.connectServer(new FtpConfig());
        }

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            ftpClient.retrieveFile(remoteFileName, os);

        } catch (IOException e) {
            throw new RuntimeException("远程文件" + remoteFileName + "读入失败" + e.getMessage());
        } finally {
            if (os != null) {
                os.close();
            }
        }
        if (ftpClient != null) {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}