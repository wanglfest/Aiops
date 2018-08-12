/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * HTTP Service
 * 
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static final String REQUEST_LOG_PRINT = "[http request ] [{}] [{}] [{}]"; // traceNo  busitype  requestUri

    public static final String REQUEST_CONTENT_LOG_PRINT = "[http request ] [{}] [{}] [{}]"; // traceNo  header  requestContent

    public static final String RESPONSE_CONTENT_LOG_PRINT = "[http response] [{}] [{}] [{}]"; // traceNo  returnStatusCode  responseContent

    // 连接超时  - 连接超时时间，毫秒   - 5s
    public static int CONNECT_TIMEOUT = 5000;

    // 数据读取超时时间，即SocketTimeout  - 读写超时时间，毫秒    - 10s
    public static int READ_TIMEOUT = 10000;

    // 连接不够用的等待时间，不宜过长，必须设置，比如连接不够用时，时间过长将是灾难性的
    public static int CONNECTION_REQUEST_TIMEOUT = 200;

    // 连接池大小
    public static int POOL_SIZE = 500;

    /**
     * 代理设置
     */
    private static final boolean enableProxy = false;
    private static final String proxyHost = "10.1.199.1";
    private static final int proxyPort = 80;
    private static final String username = "";
    private static final String password = "";
    private static final String nonProxyHostRegularExpression = "10.1.*;129.1.*;localhost*;*.local;192.*;10.*;11.*;12.*;127.0.0.1";

    public static CloseableHttpClient httpClient;

    // private static Executor            executor        = Executor.newInstance();
    private static Executor executor;

    private static boolean INIT_FLAG_HTTPCLIENT = false;

    private static boolean INIT_FLUENT_EXECUTOR = false;

    /*static {
        initHttpClient();
    }*/

    // 创建包含connection pool与超时设置的client
    public static void initHttpClient() {

        if (INIT_FLAG_HTTPCLIENT) {
            return;
        }

        INIT_FLAG_HTTPCLIENT = true;

        // 设置请求重试处理 - 自动恢复机制
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount > 1) {
                    // 如果超过最大重试次数，那么就不要继续了
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    // 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    // 不要重试SSL握手异常 SSL
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                /*
                 * HttpRequest request = (HttpRequest) context.getAttribute(HttpCoreContext.HTTP_REQUEST);
                 * boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                 * if (idempotent) {
                 * // 如果请求被认为是幂等的，那么就重试
                 * return true;
                 * }
                 * return false;
                 */
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;

            }
        };

        // 设置重定向策略
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy() {
            // 给httpclient增加重定向策略    - 解决302错误的内容，联系到API服务器是采用了Nginx进行反向代理的，该错误应该是由于API接口由反向代理进行了重定向，从而导致出现302错误
            @Override
            public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) {
                boolean isRedirect = false;
                try {
                    isRedirect = super.isRedirected(request, response, context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!isRedirect) {
                    int responseCode = response.getStatusLine().getStatusCode();
                    logger.debug("return http response code:" + responseCode);
                    if (responseCode == 301 || responseCode == 302) {
                        return true;
                    }
                }
                return isRedirect;
            }
        };

        // 定制的连接保持活动的策略
        ConnectionKeepAliveStrategy keepAliveStrategy = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // 兑现'keep-alive'头部信息
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {}
                    }
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpHost target = clientContext.getTargetHost();
                //HttpHost target = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if ("www.baidu.com".equalsIgnoreCase(target.getHostName())) {
                    // 只保持活动5秒
                    return 5 * 1000;
                } else {
                    // 否则保持活动10秒
                    return 10 * 1000;
                }
            }
        };

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(READ_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
        // HttpClientBuilder httpClientBuilder = HttpClients.custom();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.disableContentCompression();
        httpClientBuilder.setMaxConnTotal(POOL_SIZE);
        httpClientBuilder.setMaxConnPerRoute(POOL_SIZE);
        //        // 长连接保持30秒
        //        PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
        //        // 总连接数 - 整个连接池的并发
        //        pollingConnectionManager.setMaxTotal(POOL_SIZE);
        //        // 同路由的并发数   - 每个主机的并发
        //        pollingConnectionManager.setDefaultMaxPerRoute(POOL_SIZE);
        //        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        // 保持长连接配置，需要在头添加Keep-Alive
        //httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        httpClientBuilder.setKeepAliveStrategy(keepAliveStrategy);
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        // 重试次数，默认是3次，没有开启  - 关闭重试
        //  httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(2, true));
        //httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, true));
        httpClientBuilder.setRetryHandler(httpRequestRetryHandler);
        httpClientBuilder.setRedirectStrategy(redirectStrategy);

        //        List<Header> headers = new ArrayList<>();
        //        headers.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.16 Safari/537.36"));
        //        headers.add(new BasicHeader("Accept-Encoding", "gzip,deflate"));
        //        headers.add(new BasicHeader("Accept-Language", "zh-CN"));
        //        headers.add(new BasicHeader("Connection", "Keep-Alive"));
        //        httpClientBuilder.setDefaultHeaders(headers);

        // 代理认证
        if (enableProxy) {
            CredentialsProvider credsProvider = new BasicCredentialsProvider(); // 认证提供者
            credsProvider.setCredentials(new AuthScope(proxyHost, proxyPort),
                    new UsernamePasswordCredentials(username == null ? "" : username, password == null ? "" : password));
            // credsProvider.setCredentials(new AuthScope("somehost", AuthScope.ANY_PORT), new UsernamePasswordCredentials("u1", "p1"));
            // credsProvider.setCredentials(new AuthScope("otherhost", 8080, AuthScope.ANY_REALM, "ntlm"), new UsernamePasswordCredentials("u3", "p3"));

            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);

            // Simple proxy setting, can't handle non-proxy-host
            // httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,new HttpHost(proxyHost, proxyPort));
            httpClientBuilder.setRoutePlanner(new HttpRoutePlanner() {

                @Override
                public HttpRoute determineRoute(HttpHost target, HttpRequest request, HttpContext contenxt) throws HttpException {
                    HttpRoute proxyRoute = new HttpRoute(target, null, new HttpHost(proxyHost, proxyPort),
                            "https".equalsIgnoreCase(target.getSchemeName()));
                    if (nonProxyHostRegularExpression == null) {
                        return proxyRoute;
                    }
                    Pattern pattern = Pattern.compile(nonProxyHostRegularExpression, Pattern.CASE_INSENSITIVE);
                    Matcher m = pattern.matcher(target.getHostName());
                    if (m.find()) {
                        return new HttpRoute(target, null, target, "https".equalsIgnoreCase(target.getSchemeName()));
                    } else {
                        return proxyRoute;
                    }
                }

            });
        }
        httpClient = httpClientBuilder.build();
        logger.warn("httpclient init success...");
    }

    public static void initFluentExecutor() {
        if (INIT_FLUENT_EXECUTOR) {
            return;
        }

        initHttpClient();
        if (httpClient != null) {
            executor = Executor.newInstance(httpClient);
        }

        INIT_FLUENT_EXECUTOR = true;
    }

    public static void destroyHttpClient() {
        try {
            if (INIT_FLAG_HTTPCLIENT && httpClient != null) {
                httpClient.close();
                INIT_FLAG_HTTPCLIENT = false;
            }
        } catch (IOException e) {
            logger.warn("httpclient close fail", e);
        }
    }

    private static String fetchBase(String traceNo, Request request, Charset responseCharset) throws IOException {
        // Response response = request.execute();
        /*String reqUUID = UUID.randomUUID().toString();
        logger.warn("[http request][" + reqUUID + "]" + request.toString());*/
        //logger.warn("[http request][" + traceNo + "]" + request.toString());

        initFluentExecutor();
        Response response = executor.execute(request);

        HttpResponse httpResponse = response.returnResponse();
        // 判断返回值
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        // header
        HeaderIterator it = httpResponse.headerIterator("Set-Cookie");
        while (it.hasNext()) {
            logger.warn("Cookie: " + it.next());
        }

        String result = "";
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity, responseCharset);
            //result = new String(result.getBytes(Consts.ISO_8859_1), responseCharset);
        }
        executor.clearCookies();
        logger.warn(RESPONSE_CONTENT_LOG_PRINT, traceNo, statusCode, result);
        return result;
    }

    private static String fetchBase(String traceNo, Request request, String responseCharset) throws IOException {
        // Response response = request.execute();
        // logger.warn("[http request][" + traceNo + "]" + request.toString());

        initFluentExecutor();
        Response response = executor.execute(request);

        HttpResponse httpResponse = response.returnResponse();
        // 判断返回值
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        // header
        HeaderIterator it = httpResponse.headerIterator("Set-Cookie");
        while (it.hasNext()) {
            logger.warn("Cookie: " + it.next());
        }

        String result = "";
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity, responseCharset);
        }
        executor.clearCookies();
        logger.warn(RESPONSE_CONTENT_LOG_PRINT, traceNo, statusCode, result);
        return result;
    }

    @SuppressWarnings("unused")
    private static InputStream fetchBase4Stream(String traceNo, Request request) throws IOException {
        // Response response = request.execute();

        initFluentExecutor();
        Response response = executor.execute(request);

        HttpResponse httpResponse = response.returnResponse();
        // 判断返回值
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        // header
        HeaderIterator it = httpResponse.headerIterator("Set-Cookie");
        while (it.hasNext()) {
            logger.warn("Cookie: " + it.next());
        }

        InputStream is = executor.execute(request).returnContent().asStream();

        executor.clearCookies();
        logger.warn(RESPONSE_CONTENT_LOG_PRINT, traceNo, statusCode, "RTN_STREAM");
        return is;
    }

    /**
     * http获取内容输出到servlet中
     * 
     * @param response
     * @param contentUrl
     * @throws IOException
     */
    @SuppressWarnings("unused")
    private static void fetchBase4Proxy(String traceNo, HttpServletResponse response, String contentUrl) throws IOException {
        initFluentExecutor();
        // 获取内容
        Response httpResponse = executor.execute(Request.Get(contentUrl));
        // 判断返回值
        int statusCode = httpResponse.returnResponse().getStatusLine().getStatusCode();
        logger.warn(RESPONSE_CONTENT_LOG_PRINT, traceNo, statusCode, "RTN_STREAM_TOSERVLET");
        if (statusCode >= 400) {
            response.sendError(statusCode, "fetch image error from " + contentUrl);
            return;
        }
        HttpEntity entity = httpResponse.returnResponse().getEntity();
        // 设置Header
        response.setContentType(entity.getContentType().getValue());
        if (entity.getContentLength() > 0) {
            response.setContentLength((int) entity.getContentLength());
        }
        // 输出内容
        InputStream input = entity.getContent();
        OutputStream output = response.getOutputStream();
        // 基于byte数组读取InputStream并直接写入OutputStream, 数组默认大小为4k.
        IOUtils.copy(input, output);
        output.flush();
    }

    // 获取文字 , 使用默认连接池(200 total/100 per route), returnContent()会自动获取全部内容后关闭inputstream。
    // String resultString = Request.Get("contentUrl").execute().returnContent().asString();

    // 获取图片, 增加超时设定。
    // byte[] resultBytes = Request.Get("contentUrl").connectTimeout(TIMEOUT_SECONDS * 1000).socketTimeout(TIMEOUT_SECONDS * 1000).execute().returnContent().asBytes();

    /**
     * Form 提交获取的处理
     * fetch(request, params, "UTF-8")
     * 
     * @param request
     * @param params
     * @param charset
     * @return
     * @throws IOException
     */
    public static String fetchByForm(String traceNo, Request request, Map<String, String> params, String charset) throws IOException {
        //.addHeader("Referer", URL_PREFIX+"/");
        Form bodyForm = Form.form();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            bodyForm.add(entry.getKey(), entry.getValue());
        }
        logger.warn("[http request ][" + traceNo + "]" + request.toString() + " " + params.toString());
        return fetchBase(traceNo, request.bodyForm(bodyForm.build(), Charset.forName(charset)), Consts.UTF_8);
    }

    /**
     * 文本报文的提交处理
     * 
     * @param request
     * @param bodyString
     * @return
     * @throws IOException
     */
    public static String fetchByplaintext(String traceNo, Request request, String bodyString) throws IOException {
        logger.warn("[http request ][" + traceNo + "]" + request.toString() + " " + bodyString);
        return fetchBase(traceNo, request.bodyString(bodyString, ContentType.create("text/plain", "UTF-8")), Consts.UTF_8);
    }

    /**
     * Get方式http交互
     * 
     * @param url
     * @param params
     * @param requestCharset
     * @param responseCharset
     * @return
     * @throws IOException
     */
    public static String fetchGet(String traceNo, String url, Map<String, String> params, String requestCharset, String responseCharset)
            throws IOException {
        String gateway = url;
        StringBuffer requestUrl = new StringBuffer();
        requestUrl.append(gateway);
        if (params != null && !params.isEmpty()) {
            requestUrl.append("?");
            int index = 0;
            for (String name : params.keySet()) {
                String value = params.get(name);
                if (index > 0) {
                    requestUrl.append("&");
                }
                requestUrl.append(name);
                requestUrl.append("=");
                requestUrl.append(URLEncoder.encode(value, requestCharset));
                index++;
            }
        }
        return fetchBase(traceNo, Request.Get(requestUrl.toString()), responseCharset);
    }

    /**
     * Post方式http交互   
     * 
     * "text/xml"
     * @param url
     * @param params
     * @param bodySting
     * @param requestCharset
     * @param responseCharset
     * @return
     * @throws IOException
     */
    public static String fetchPost(String traceNo, String busiType, String url, Map<String, String> params, String bodySting,
            Map<String, String> headers, String requestCharset, String responseCharset) throws IOException {
        logger.warn(REQUEST_LOG_PRINT, traceNo, busiType, url);
        Request request = Request.Post(url);

        String headersStr = "";
        if (headers != null && !headers.isEmpty()) {
            Iterator<String> it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                request.addHeader(key, headers.get(key));
            }
            headersStr = headers.toString();
        }

        if (params != null && !params.isEmpty()) {
            Form form = Form.form();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
            request.bodyForm(form.build(), Charset.forName(requestCharset));
            logger.warn(REQUEST_CONTENT_LOG_PRINT, traceNo, headersStr, params.toString());
        }

        if (StringUtils.isNotBlank(bodySting)) {
            logger.warn(REQUEST_CONTENT_LOG_PRINT, traceNo, headersStr, bodySting);
            request.bodyString(bodySting, ContentType.create("text/plain", requestCharset));
        }
        return fetchBase(traceNo, request, responseCharset);
    }

    /**
     * Post方式http交互
     * 
     * @param url
     * @param paras
     * @param headers
     * @param requestCharset
     * @param responseCharset
     * @return
     * @throws IOException
     */
    public static String formSendPost(String traceNo, String url, Map<String, String> paras, Map<String, String> headers, String requestCharset,
            String responseCharset) throws IOException {
        Request post = Request.Post(url);

        if (headers != null && !headers.isEmpty()) {
            Iterator<String> it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                post.addHeader(key, headers.get(key));
            }
        }

        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String key : paras.keySet()) {
            String value = paras.get(key);
            list.add(new BasicNameValuePair(key, value));
        }
        UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(list, Charset.forName(requestCharset));
        post.body(urlEntity);
        return fetchBase(traceNo, post, "utf-8");
    }

    /**
     * 上传带附件的Post方式http交互
     * 
     * @param url
     * @param formParams
     * @param headers
     * @param requestCharset
     * @param responseCharset
     * @return
     * @throws IOException 
     */
    public static String multipartFormSendPost(String traceNo, String url, Map<String, ?> formParams, Map<String, String> headers,
            String requestCharset, String responseCharset) throws IOException {
        Request post = Request.Post(url);
        if (headers != null && !headers.isEmpty()) {
            Iterator<String> it = headers.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                post.addHeader(key, headers.get(key));
            }
        }
        //HttpPost post = new HttpPost(url);
        //填入表单参数
        if (formParams != null && !formParams.isEmpty()) {
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            entityBuilder.setCharset(Charset.forName(requestCharset));//设置请求的编码格式

            ContentType contentFileType = ContentType.create("multipart/form-data", Charset.forName(requestCharset));
            ContentType contentType = ContentType.create("text/plain", Charset.forName(requestCharset));

            for (String key : formParams.keySet()) {
                Object val = formParams.get(key);
                ContentBody body = null;

                if (val instanceof File[]) {
                    int findex = 0;
                    for (File f : (File[]) val) {
                        body = new FileBody(f, contentFileType);
                        entityBuilder.addPart(key + findex, body);
                        findex++;
                    }
                } else {
                    if (val instanceof File) {
                        body = new FileBody((File) val, contentFileType);
                    } else if (val instanceof byte[]) {
                        body = new ByteArrayBody((byte[]) val, key); // 文件名和文件内容数据
                    } else if (val instanceof InputStream) {
                        body = new InputStreamBody((InputStream) val, key); // 文件名和文件内容数据输入流
                    } else {
                        body = new StringBody((String) val, contentType);
                    }
                    entityBuilder.addPart(key, body);
                }
            }

            post.body(entityBuilder.build());
            //post.setEntity(entityBuilder.build());
        }
        return fetchBase(traceNo, post, responseCharset);
    }

}
