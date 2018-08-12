/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    public ArrayList<String> arFiles = new ArrayList<String>();

    public static final int BUFFER_SIZE = 1024 * 1024;

    public static boolean isDirectory(File file) {
        if (null == file || !file.exists() || !file.isDirectory()) {
            return false;
        }
        return true;
    }

    public static boolean isFile(File file) {
        if (null == file || !file.exists() || !file.isFile()) {
            return false;
        }
        return true;
    }

    public static String getFileSuffix(String path) {
        File file = new File(path);
        return getFileSuffix(file);
    }

    public static String getFileNameSuffix(String fileName) {
        String suffix = "";
        if ("".equals(fileName) || fileName == null) {
            return suffix;
        }
        int index = fileName.lastIndexOf('.');
        int start = index + 1;
        if (index > 0 && start < fileName.length()) {
            suffix = fileName.substring(start);
        }
        return suffix;
    }

    public static InputStream getFileInByClsPth(String path) throws IOException {
        InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(path);
        return in;
    }

    public static String getFileSuffix(File file) {
        String suffix = "";
        if (isFile(file)) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf('.');
            int start = index + 1;
            if (index > 0 && start < fileName.length()) {
                suffix = fileName.substring(start);
            }
        }
        return suffix;
    }

    /**
     * 创建目录
     * @param path 创建目录的绝对路径
     */
    public static void createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 删除指定文件夹内的所有文件
     * @param dir 指定要删除的文件夹对象
     * @return true or false
     */
    public static boolean deleteDir(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return false; // 检查参数
        }
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                file.delete(); // 删除所有文件
            } else if (file.isDirectory()) {
                deleteDir(file); // 递规的方式删除文件夹
            }
        }
        dir.delete();//删除目录本身
        return true;
    }

    public static void createNewFile(String dirPath, String fileName) throws IOException {
        File dir = new File(dirPath);
        File file = new File(dir, fileName);
        if (dir != null) {
            if (!dir.exists()) {
                log.info("目录不存在,创建目录");
                dir.mkdirs();
            }
            if (!file.exists()) {
                log.info("文件不存在，创建文件");
                file.createNewFile();
            } else {
                log.info("文件已存在");
            }

        }
    }
//        public static void main(String[] args) throws IOException, Exception {
//            for(int i=0;i<1;i++) {
//            String timeNo = ""+i+"";
//            System.out.println(i);
//            String filePath = "d:/test/86162017101600000564/";
//            String zipFilePath = "d:/test/861620171016000005641/";
//            zip(timeNo,filePath,zipFilePath);}
//        }

    /**
     * 压缩文件,需引入ant-1.7.0.jar包
     * @param timeNo 压缩文件加后的文件名，以导出批次表中的时间序号命名 
     * @param filePath 需要压缩文件夹对象的路径
     * @param zipFilePath 压缩后压缩文件存放的目录
     */
    public static void zip(String orderno, String filePath, String zipFilePath) throws IOException, Exception {
        log.info("开始创建压缩文件");
        //压缩文件总大小
        long size = 0;
        createDir(zipFilePath); //创建压缩包的存放目录
        log.info("影像文件路径：" + filePath);
        zipFilePath = zipFilePath +"/"+ orderno;
        log.info("压缩文件名称及路径为：" + zipFilePath + ".zip");
        File file = new File(filePath);
        File[] files = file.listFiles();
        byte[] buf = new byte[1024];
        CheckedOutputStream ch = new CheckedOutputStream(new FileOutputStream(zipFilePath + ".zip"), new CRC32());
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(ch));
        FileInputStream in = null;
        try {
            for (int i = 0; i < files.length; i++) {
                in = new FileInputStream(files[i]);
                out.putNextEntry(new ZipEntry(files[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.closeEntry();
                size = size + files[i].length();
                log.info("压缩文件创建成功");
            }
            log.info("压缩文件大小" + size);
        } catch (Exception e) {
            log.info(e.getMessage());
            log.error("时间序号为：\" + timeNo + \"的导出文件压缩失败");
            throw new Exception("时间序号为：" + orderno + "的导出文件压缩失败");
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (ch != null) {
                ch.close();
            }
        }
    }
    
    /**
     * 压缩文件,需引入ant-1.7.0.jar包
     * @param timeNo 压缩文件加后的文件名，以导出批次表中的时间序号命名 
     * @param filePath 需要压缩文件夹对象的路径
     * @param zipFilePath 压缩后压缩文件存放的目录
     */
    public static void totzip(String timeNo,String filePath, String zipFilePath) throws IOException, Exception {
        log.info("开始创建压缩文件");
        //压缩文件总大小
        long size = 0;
        createDir(zipFilePath); //创建压缩包的存放目录
        log.info("影像文件路径：" + filePath);
        zipFilePath = zipFilePath + timeNo;
        log.info("压缩文件名称及路径为：" + zipFilePath + ".zip");
        File file = new File(filePath);
        File[] files = file.listFiles();
        byte[] buf = new byte[1024];
        CheckedOutputStream ch = new CheckedOutputStream(new FileOutputStream(zipFilePath + ".zip"), new CRC32());
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(ch));
        FileInputStream in = null;
        try {
            for (int i = 0; i < files.length; i++) {
                in = new FileInputStream(files[i]);
                out.putNextEntry(new ZipEntry(files[i].getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.closeEntry();
                size = size + files[i].length();
                log.info("压缩文件创建成功");
            }
            log.info("压缩文件大小" + size);
        } catch (Exception e) {
            log.error("时间序号为：" + timeNo + "的导出文件压缩失败,失败原因"+e);
            throw new Exception("时间序号为：" + timeNo + "的导出文件压缩失败");
        } finally {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (ch != null) {
                ch.close();
            }
        }
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }

        log.info("info:" + url + " download success");

    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

    @SuppressWarnings("rawtypes")
    public static void uuZipFile(String fileAddress, String unZipAddress) throws IOException {

        File file = new File(fileAddress);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file, "GBK");//设置编码格式
        } catch (IOException exception) {
            exception.printStackTrace();
            log.error("解压文件不存在!");
        }
        Enumeration e = zipFile.getEntries();
        while (e.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) e.nextElement();
            if (zipEntry.isDirectory()) {
                String name = zipEntry.getName();
                name = name.substring(0, name.length() - 1);
                File f = new File(unZipAddress + name);
                f.mkdirs();
            } else {
                File f = new File(unZipAddress + zipEntry.getName());
                f.getParentFile().mkdirs();
                f.createNewFile();
                InputStream is = zipFile.getInputStream(zipEntry);
                FileOutputStream fos = new FileOutputStream(f);
                int length = 0;
                byte[] b = new byte[1024];
                while ((length = is.read(b, 0, 1024)) != -1) {
                    fos.write(b, 0, length);
                }
                is.close();
                fos.close();
            }
        }
        if (zipFile != null) {
            zipFile.close();
        }
        file.delete();//解压完以后将压缩包删除 
    }

    /**
     * 将压缩包解压到目录下
     * 
     * @param filePath
     * @param UNZIPPATH
     */
    public static void unzip(String filePath, String UNZIPPATH) {
        long startTime = System.currentTimeMillis();
        String path = filePath;
        String unzipPATH = UNZIPPATH;
        try {
            ZipInputStream Zin = new ZipInputStream(new FileInputStream(path));// 输入源zip路径
            BufferedInputStream Bin = new BufferedInputStream(Zin);
            String Parent = unzipPATH; // 输出路径（文件夹目录）
            File Fout = null;
            java.util.zip.ZipEntry entry;
            try {
                while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
                    Fout = new File(Parent, entry.getName());
                    if (!Fout.exists()) {
                        (new File(Fout.getParent())).mkdirs();
                    }
                    FileOutputStream out = new FileOutputStream(Fout);
                    BufferedOutputStream Bout = new BufferedOutputStream(out);
                    int b;
                    while ((b = Bin.read()) != -1) {
                        Bout.write(b);
                    }
                    Bout.close();
                    out.close();
                    log.info("\t 文件" + Fout + "解压成功");
                }
                Bin.close();
                Zin.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (Bin != null) {
                        Bin.close();
                    }
                    if (Zin != null) {
                        Zin.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        log.info("解压缩结束，耗费时间： " + (endTime - startTime) + " ms");
    }

    public void getFiles(String string) {
        File file = new File(string);

        if (file.isDirectory()) {
            log.info(file.getPath());

            File[] files = file.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    getFiles(files[i].getPath());
                } else {
                    log.info(files[i].getPath());
                    arFiles.add(files[i].getPath());
                }
            }
        } else {
            log.info(file.getPath());
            arFiles.add(string);
        }
    }

    /**
     * 解析电子保单报文
     * 方法功能描述
     * @param path
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<Integer, String> analysisXml(String path) {
        Map<Integer, String> map = new HashMap<Integer, String>();
        //        Date time = null;
        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            File f = new File(path);
            SAXReader reader = new SAXReader();
            Document doc = reader.read(f);
            Element root = doc.getRootElement();
            Element foo;
            Element foo1;
            for (Iterator i = root.elementIterator("PAGES"); i.hasNext();) {
                foo = (Element) i.next();
                int key = 0;
                for (Iterator j = foo.elementIterator("PAGE"); j.hasNext();) {
                    foo1 = (Element) j.next();
                    List<Attribute> listAttr = foo1.attributes();
                    String pageUrl = "";
                    //                       String upTime = "";
                    for (Attribute attr : listAttr) {//遍历当前节点的所有属性
                        String name = attr.getName();//属性名称
                        if ("PAGE_URL".equals(name)) {
                            pageUrl = attr.getValue();
                            if (StringUtil.isNotEmpty(pageUrl)) {
                                map.put(key, pageUrl);
                                key++;
                            }
                        }

                        //                           else if("UP_TIME".equals(name)){
                        //                               upTime = attr.getValue();
                        //                               if(time==null){
                        //                                   time = simpleDateFormat.parse(upTime);
                        //                               }else {
                        //                                   if(time.getTime()<simpleDateFormat.parse(upTime).getTime()){
                        //                                       time = simpleDateFormat.parse(upTime);
                        //                                   }
                        //                               }
                        //                           }
                        //                           if(!StringUtil.isEmpty(pageUrl)&&!StringUtil.isEmpty(upTime)) {
                        //                               map.put(upTime,pageUrl);
                        //                           }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        for (String value:map.keySet()){
        //            log.info("key: "+ value + "  value:  "+ map.get(value));
        //        }
        //        log.info(time);
        //        String pdfPath = map.get(simpleDateFormat.format(time));
        return map;
    }

    /**
     * 
     * 文件转移
     * 
     * @param oldFile
     *            原文件目录
     * @param newFile
     *            新文件目录
     */
    public static void copyFile(File oldFile, File newFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fis = new FileInputStream(oldFile);
            fos = new FileOutputStream(newFile);
            in = fis.getChannel();// 得到对应的文件通道
            out = fos.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                in.close();
                fos.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * 递归删除文件夹
     * 
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {// 判断文件是否存在
            if (file.isFile()) {// 判断是否是文件
                file.delete();// 删除文件
            } else if (file.isDirectory()) {// 否则如果它是一个目录
                File[] files = file.listFiles();// 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
                    deleteFile(files[i]);// 把每个文件用这个方法进行迭代
                }
                file.delete();// 删除文件夹
            }
        }
    }

    /** 
     * 复制整个文件夹内容 
     * @param oldPath String 原文件路径 如：c:/fqf 
     * @param newPath String 复制后路径 如：f:/fqf/ff 
     * @return boolean 
     */
    public static void copyFolder(String oldPath, String newPath) {

        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹 
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹 
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            log.error("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }
}
