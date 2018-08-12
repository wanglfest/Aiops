package com.sinosoft.utils;

import java.util.Arrays;
import java.util.Map;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
public class MD5UtilSino {
   
	//MD5签名
    public static String Md5Code(String sign){
    	String encodeStr = DigestUtils.md5DigestAsHex(sign.getBytes());
        return  encodeStr;
    }
    
    /** 
     * 构建签名原文 
     *  
     * @param signFilds 参数列表 
     * @param param 参数与值的map 
     * @return 
     */  
    private static String orgSignSrc(String[] signFields, Map<String, Object> map) {  
        if (signFields != null) {  
            Arrays.sort(signFields); // 对key按照 字典顺序排序  
        }  
  
        StringBuffer signSrc = new StringBuffer("");  
        int i = 0;  
        for (String field : signFields) {
            signSrc.append(field);  
            signSrc.append("=");  
            signSrc.append((StringUtils.isEmpty(map.get(field)) ? ""  
                    :map.get(field)));  
            // 最后一个元素后面不加&  
            if (i < (signFields.length - 1)) {  
                signSrc.append("&");  
            }  
            i++;  
        }  
        
        return signSrc.toString();  
    }  
    
    /** 
     * MD5生成签名字符串 
     *  
     * @param map 
     *            需签名参数 
     * @param key 
     *            MD5key 
     * @return 
     */  
    public static String MD5sign(Map<String, Object> map, String key) {  
        String genSign = "";  
        try {  
            String[] signFields = new String[map.size()];  
            int i = 0;  
        	for (String mkey : map.keySet()) {
        		signFields[i]=mkey;
        	    i++;
        	}
        	// 生成签名原文  
            String signSrc = orgSignSrc(signFields, map);  
            // MD5的方式签名  
            signSrc += "&key=" + key;  
            genSign = Md5Code(signSrc);  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return genSign;  
    }  
    
}
