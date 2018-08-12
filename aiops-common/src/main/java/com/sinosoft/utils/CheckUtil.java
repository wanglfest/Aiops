package com.sinosoft.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sinosoft.exception.CommonRuntimeException;

/**
 * 
 * 校验数据工具类
 * @author James·Von·LeeBoom  
 * @date 2017年10月11日 上午9:19:51 
 * @version V1.0
 */
public class CheckUtil {

    private static Matcher m = null;

    /** 半角数字的正则 */
    private static final String NUMBER = "^[0-9]+$";
    private static Pattern PATTERN_NUMBER = null;

    /** 半角英文的正则 */
    private static final String ALPHABET = "^[a-zA-Z]+$";
    private static Pattern PATTERN_ALPHABET = null;

    /** 半角大写英文的正则 */
    private static final String UPPER_ALPHABET = "^[A-Z]+$";
    private static Pattern PATTERN_UPPER_ALPHABET = null;

    /** 半角英文和数字的正则 */
    private static final String ALPHABET_NUMBER = "^[a-zA-Z0-9]+$";
    private static Pattern PATTERN_ALPHABET_NUMBER = null;

    /** 半角手机号的正则 */
    private static final String MOBILE = "^1[3|4|5|7|8][0-9]{9}$";
    private static Pattern PATTERN_MOBILE = null;

    /**含汉字的正则*/
    private static final String CHINESE = "[\u4e00-\u9fa5]";
    private static Pattern PATTERN_CHINESE = null;

    /**邮箱的正则*/
    private static final String EMAIL = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
    private static Pattern PATTERN_EMAIL = null;

    static {

        try {
            PATTERN_NUMBER = Pattern.compile(NUMBER);
            PATTERN_ALPHABET = Pattern.compile(ALPHABET);
            PATTERN_UPPER_ALPHABET = Pattern.compile(UPPER_ALPHABET);
            PATTERN_ALPHABET_NUMBER = Pattern.compile(ALPHABET_NUMBER);
            PATTERN_MOBILE = Pattern.compile(MOBILE);
            PATTERN_CHINESE = Pattern.compile(CHINESE);
            PATTERN_EMAIL = Pattern.compile(EMAIL);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * 根据正则表达式校验字符串
     * @param val
     * @param pattern
     * @return
     */
    private static boolean check(String val, Pattern pattern)  throws CommonRuntimeException{
        if (val == null)     return false;
        if ("".equals(val))     return true;
        m = pattern.matcher(val);
        return m.matches();
    }

    /**
     * 
     * 校验一个字符串是否是数字
     * @param val
     * @return
     */
    public static boolean checkIsNumeric(String val)  throws CommonRuntimeException{
    	if("".equals(val) || null==val) return false;
    	 return check(val, PATTERN_NUMBER);
    }

    /**
     * 
     * 校验一个字符串是否是英文
     * @param val
     * @return
     */
    public static boolean checkIsAlphabet(String val)  throws CommonRuntimeException{
    	if("".equals(val) || null==val) return false;
        return check(val, PATTERN_ALPHABET);
    }

    /**
     * 
     * 校验一个字符串是否全是大写英文
     * @param val
     * @return
     */
    public static boolean checkIsUpperAlphabet(String val)  throws CommonRuntimeException{
    	if("".equals(val) || null==val) return false;
        return check(val, PATTERN_UPPER_ALPHABET);
    }

    /**
     * 校验一个字符串是否是英文和字母
     * @param val
     * @return
     */
    public static boolean checkIsAlphabetAndNumber(String val)  throws CommonRuntimeException{
    	if("".equals(val) || null==val) return false;
        return check(val, PATTERN_ALPHABET_NUMBER);
    }

    /**
     * 
     * 校验一个字符串是否是手机号，数字+11位+以13、14、15、17或18开头
     * @param val
     * @return
     */
    public static boolean checkIsMobile(String val)  throws CommonRuntimeException{
    	if (val.length() != 11)    	return false;
        if (!checkIsNumeric(val))         return false;
        return check(val, PATTERN_MOBILE);
    }

    public static boolean checkIsEmail(String val)   throws CommonRuntimeException{
    	if("".equals(val) || null==val) return false;
        return check(val, PATTERN_EMAIL);
    }

    /**
     * 
     * 判断一个字符串中是否包含汉字
     * @param str
     * @return
     */
    public static boolean checkIsContainChinese(String val)   throws CommonRuntimeException{
        m = PATTERN_CHINESE.matcher(val);
        if (m.find())           return true;
        return false;
    }

    /**
     * 
     * 统计字符串中字母的个数
     * @param val
     * @return
     */
    public static int countAlphabet(String val)   throws CommonRuntimeException{
        int count = 0;
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * 统计字符串中数字的个数
     * @param val
     * @return
     */
    public static int countNumber(String val)   throws CommonRuntimeException{
        int count = 0;
        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);
            if (c >= '0' && c <= '9') {
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * 统计字符串中汉字个数
     * @param val
     * @return  无返回0，有返回具体个数
     */
    public static int countChinese(String val)   throws CommonRuntimeException{
        int count = 0;
        m = PATTERN_CHINESE.matcher(val);
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                count = count + 1;
            }
        }
        return count;
    }

    
}
