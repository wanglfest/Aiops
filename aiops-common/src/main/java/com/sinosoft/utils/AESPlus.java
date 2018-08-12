/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.exception.CommonRuntimeException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * AES加密解密工具类
 * @author Administrator
 * @date 2017年7月13日 上午9:23:48 
 * @version V1.0
 */
@SuppressWarnings("restriction")
public class AESPlus {
    public static String secretKey = "^BJSHGZ!@#ZZZDGJ$%^JRSR&*(ZNJ!@#^";
    private static final Logger log = LoggerFactory.getLogger(AESPlus.class);
    /**
     * 加密
     *
     * @param strIn
     * @return
     */
    public static String encrypt(String strIn) {
        try {
            strIn = strIn.toUpperCase();
            SecretKeySpec skeySpec = getKey(secretKey);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0108489312380708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(strIn.getBytes());
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 加密（非大写）
     *
     * @param strIn
     * @return
     */
    public static String encrypt1(String strIn) {
        try {
            SecretKeySpec skeySpec = getKey("jiaotongyinhang");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0108489312380708".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(strIn.getBytes());
            return new BASE64Encoder().encode(encrypted);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * 解密
     *
     * @param strIn
     * @return
     */
    public static String decrypt(String strIn) {
        String logPrefix = "[AESPlus.decrypt].[";
        log.info("{} 报文解密 开始]", logPrefix);
        try {
            if (strIn.indexOf(" ") > -1) {
                strIn = strIn.replaceAll(" ", "+");
            }
            SecretKeySpec skeySpec = getKey(secretKey);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0108489312380708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(strIn);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "UTF-8");
            log.info("{} 报文解密 结束]", logPrefix);
            return originalString;
        } catch (Exception e) {
            log.error("{} 报文解密产生异常，原因：", logPrefix, e);
            throw new CommonRuntimeException("报文解密失败");
        }
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    //    public static void main(String[] args) {
    //        System.out.println("解密：" + decrypt("nmEK3uxDe4TE9bHrde9hG2R0Hzvredr8kT07S5YY1HDaTUkljXj/NsxQch7sCtTPMXMXSxMnAyai\n" +
    //                "uVIMsvEDkv15BmdUyirr0oNYK09SsqUfCma84I0rkvJuPu7yYUhjYXjGa1HjsJwhIGJMK9dSRdji\n" +
    //                "H2MwBK9KBrZn5xlpJ36XZskcAqx4/sFMVWHoHHTDHcx+O8SsHcRBH/bXhAYM2E+CjFsVTxOe3BSd\n" +
    //                "y6uYkYblHqDKf8KzW5szc/y8Stu6Wu1YtzgGK1KuGwsqWpKvH0SMPZVZ6DfkbCigt13pi9t7drff\n" +
    //                "jwyCYOd2ynUA7xMuRJCpE6/hlOc+yjjgtlqy7v/xcFHLsJLaIDpKk8Kd48MRNXfiFBn9tqws/Bk3\n" +
    //                "73wDjPbnwBDi2o8FufYSg+DOfsEz0r1Awak5uqlc9ubMsBo="));

    //        AESPlus aesPlus = new AESPlus();
    //        Activity activity = new Activity();
    //        activity.setId(1);
    //        activity.setCode("a");
    //        System.out.println(aesPlus.decrypt("U2FsdGVkX1+ZwcgL1B1EfZ8LSjW0KF4fM1Z/KTzDVqTLajRJOwmBKZVRjFMpYEXz"));
    //        System.out.println(aesPlus.encrypt1(new String(JSON.toJSONString(activity))));
    //        System.out.println(aesPlus.encrypt("15"));
    //        byte[] a = "0108489312380708".getBytes();
    //        for(byte b:a) {
    //            System.out.print(b+",");
    //        }
    //        System.out.println();
    //    }
}