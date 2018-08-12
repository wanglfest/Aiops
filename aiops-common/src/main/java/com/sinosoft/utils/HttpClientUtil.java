/**
 * @Copyright ®2015 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 电子商务云服务平台
 * 创建日期 : 2017年3月16日
 * 修改历史 : 
 *     1. [2017年3月16日]创建文件 by chihouliang
 */
package com.sinosoft.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * 【httpClient工具】
 * @author chihouliang
 * @date 2017年3月16日 上午10:36:11 
 * @version V1.0  
 */
public class HttpClientUtil {
    private static final Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
    //http get请求: 加参数paras
    public static String GetRequest(String url, Map<String, String> paras) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;
        //封装请求参数
        StringBuffer str = new StringBuffer();
        StringBuffer build = new StringBuffer();
        try {
            //转换为键值对
            List<NameValuePair> list = new ArrayList<>();
            for (String key : paras.keySet()) {
                String value = paras.get(key);
                list.add(new BasicNameValuePair(key, value));
            }
            for (NameValuePair nameValuePair : list) {
                str.append(nameValuePair+" ");
            }
            String reqEntity =str.toString().trim().replace(" ", "&");
            //创建Get请求
            HttpGet httpGet = new HttpGet(url + "?" + reqEntity);
            //执行Get请求，
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                while ((body = br.readLine()) != null) {
                    build.append(body);
                    System.out.println(body);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输入流，释放资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //消耗实体内容
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return build.toString();
    }

    public static String sendGet(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;
        //封装请求参数
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        String str = "";
        StringBuffer build = new StringBuffer();
        try {
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            //创建Get请求
            HttpGet httpGet = new HttpGet(url + "?" + str);
            //执行Get请求，
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                while ((body = br.readLine()) != null) {
                    build.append(body);
                    System.out.println(body);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输入流，释放资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //消耗实体内容
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return build.toString();

    }

    public static String sendPost(String url, Map<String, String> paras) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);

        try {

            List<NameValuePair> list = new ArrayList<>();

            for (String key : paras.keySet()) {
                String value = paras.get(key);
                list.add(new BasicNameValuePair(key, value));
            }
            UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(list);

            post.setEntity(urlEntity);
            CloseableHttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, "utf-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendJsonByPost(String url, String jsonParams) {
        String response = null;
        try {
            CloseableHttpClient httpclient = null;
            CloseableHttpResponse httpresponse = null;
            try {
                httpclient = HttpClients.createDefault();
                HttpPost httppost = new HttpPost(url);
                StringEntity stringentity = new StringEntity(jsonParams, ContentType.create("application/json", "UTF-8"));
                httppost.setEntity(stringentity);
                httpresponse = httpclient.execute(httppost);
                response = EntityUtils.toString(httpresponse.getEntity());
                log.info("response: " + response);
            } finally {
                if (httpclient != null) {
                    httpclient.close();
                }
                if (httpresponse != null) {
                    httpresponse.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String request(String urlstr, String req) {
        HttpURLConnection conn = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            URL url = new URL(urlstr);
            // System.out.println("SERVER: info - " + telegram.toJsonString());
            byte[] info = req == null ? null : req.getBytes("UTF-8");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setReadTimeout(100000);
            conn.setConnectTimeout(100000);
            conn.setRequestProperty("Content-Type", "text/xml");
            if (info != null)
                conn.setRequestProperty("Content-Length", String.valueOf(info.length));

            OutputStream up = conn.getOutputStream();
            if (info != null)
                up.write(info);
            up.flush();
            up.close();

            in = conn.getInputStream();
            return stringOf(in);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {}

            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {}

            if (conn != null)
                conn.disconnect();
        }

        return null;
    }

    public static String stringOf(InputStream in) {
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();

            byte[] b = new byte[2048];
            int c;
            while ((c = in.read(b)) >= 0) {
                out.write(b, 0, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {}
        }

        byte[] b = out.toByteArray();

        try {
            String str = new String(b, "UTF-8");
            return str == null || "".equals(str) || "null".equalsIgnoreCase(str) ? null : str;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 新支付接口请求（M1500010）
     *
     * @param body
     * @param url
     * @return
     * @throws IOException
     */
    public static String responseBusiness(String body, URL url) throws IOException {
        String response = null;
        InputStream instr = null;
        try {
            byte[] xmlData = body.getBytes();
            HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("Content-Type", "text/xml");
            urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            //            System.out.println(String.valueOf(xmlData.length));
            //                DataOutputStream printout = new DaOutputStream(urlCon.getOutputStream());
            //                printout.write(xmlData);
            //                printout.flush();
            //                printout.close();
            PrintWriter out1 = new PrintWriter(new OutputStreamWriter(urlCon.getOutputStream(), "utf-8"));
            out1.println(body);
            out1.close();
            if (urlCon.getResponseCode() >= 400) {
                instr = urlCon.getErrorStream();
            } else {
                instr = urlCon.getInputStream();
            }
            byte[] bis = IOUtils.toByteArray(instr);
            response = new String(bis, "UTF-8");
            if ((response == null) || ("".equals(response.trim()))) {
                //                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>返回空");
            }
            //            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>返回数据为:" + response);
            instr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    /**
     *  http://129.1.9.103/getTokenByPhone?phone=1348221104
     *  @param url   
     *          http://129.1.9.103/getTokenByPhone?phone=    
     *  @param param 
     *          13482211049
     * @throws Exception 
     */
    public static String httpSendGet(String url, String param) throws Exception {  
        String result = "";  
        String urlName = url + param;  
        try {  
            URL realURL = new URL(urlName);  
            URLConnection conn = realURL.openConnection();  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");  
            conn.connect();  
            /*Map<String, List<String>> map = conn.getHeaderFields();  
            for (String s : map.keySet()) {  
                System.out.println(s + "-->" + map.get(s));  
            }  */
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }  
        return result;  
    }  
    /**
     *  http://129.1.9.103/getTokenByPhone?phone=1348221104
     *  @param url   
     *          http://129.1.9.103/getTokenByPhone?phone=    
     *  @param param 
     *          13482211049
     * @throws Exception 
     */
    public static String httpMobileSendGetMobile(String url, String param) throws Exception {  
        String result = "";  
        String urlName = url + param;  
        try {  
            URL realURL = new URL(urlName);  
            URLConnection conn = realURL.openConnection();  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1");  
            conn.connect();  
            /*Map<String, List<String>> map = conn.getHeaderFields();  
            for (String s : map.keySet()) {  
                System.out.println(s + "-->" + map.get(s));  
            }  */
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }  
        return result;  
    }  
    
    /**
     *  http://129.1.9.103/getTokenByPhone?phone=1348221104
     *  @param url   
     *          http://129.1.9.103/getTokenByPhone?phone=    
     *  @param param 
     *          13482211049
     * @throws Exception 
     */
    public static String httpIphoneSendGet(String url, String param) throws Exception {  
        String result = "";  
        String urlName = url + param;  
        try {  
            URL realURL = new URL(urlName);  
            URLConnection conn = realURL.openConnection();  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("User-Agent", "iPhone");  
            conn.connect();  
            /*Map<String, List<String>> map = conn.getHeaderFields();  
            for (String s : map.keySet()) {  
                System.out.println(s + "-->" + map.get(s));  
            }  */
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }  
        return result;  
    }  
    
    /**
     *  http://129.1.9.103/getTokenByPhone?phone=1348221104
     *  @param url   
     *          http://129.1.9.103/getTokenByPhone     
     *  @param param 
     *          phone=13482211049
     */
    public static String sendGet(String url, String param) throws Exception {  
        String result = "";  
        String urlName = url + "?" + param;  
        try {  
            URL realURL = new URL(urlName);  
            URLConnection conn = realURL.openConnection();  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");  
            conn.connect();  
            /*Map<String, List<String>> map = conn.getHeaderFields();  
            for (String s : map.keySet()) {  
                System.out.println(s + "-->" + map.get(s));  
            }  */
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += "\n" + line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception(e.getMessage());
        }  
        return result;  
    }  
  
    /**
     *  http://129.1.9.103/getTokenByPhone?phone=1348221104
     *  @param url   
     *          http://129.1.9.103/getTokenByPhone     
     *  @param param 
     *          phone=13482211049
     */
    public static String sendPost(String url,String param) throws Exception {  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            URLConnection conn = realUrl.openConnection();  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");  
            //post设置如下两行  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            PrintWriter out = new PrintWriter(conn.getOutputStream());  
            out.print(param);  
            out.flush();  
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));  
            String line;  
            while((line = in.readLine()) != null){  
                result +="\n" + line;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw new Exception(e.getMessage());
        }  
        return result;  
    }  
}
