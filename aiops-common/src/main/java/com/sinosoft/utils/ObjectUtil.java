/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

public class ObjectUtil {

    public static final BigDecimal ZERO = new BigDecimal("0");

    /** 
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     *  
     * @param obj 
     * @return 
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence)
            return ((CharSequence) obj).length() == 0;

        if (obj instanceof Collection)
            return ((Collection) obj).isEmpty();

        if (obj instanceof Map)
            return ((Map) obj).isEmpty();

        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }

    public static BigDecimal convertValue(BigDecimal decimal) {

        return decimal != null ? decimal : BigDecimal.valueOf(0);
    }

    public static Object getReflectValue(Object item, String name) {
        Method m;
        Object value = "";
        try {
            m = item.getClass().getMethod("get" + captureName(name));
            value = m.invoke(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 
    * @2016年2月20日
    * @user caoyk
    * @Title: captureName 
    * @Description: 将字符串name 转化为首字母大写
    * @param @param name
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static boolean isUpdateChnName(String basedField, Object target) {
        boolean flag = false;
        if (!ObjectUtil.isNullOrEmpty(basedField) && !ObjectUtil.getReflectValue(target, basedField).equals(ZERO)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 将DTO中的值按照参数传递给Entity，要求两边的字段要一致
     * @param a  取值的对象
     * @param b  存放值的对象
     * return  Obejct 
     */
    @SuppressWarnings("rawtypes")
    public static Object syncDtoToEntity(Object a, Object b) {
        try {
            Class A = a.getClass();
            Class B = b.getClass();
            Field[] fa = A.getDeclaredFields();
            for (int i = 0; i < fa.length; i++) {
                try {
                    Field f1 = fa[i];
                    f1.setAccessible(true);
                    Object val = f1.get(a);
                    Field[] fb = B.getDeclaredFields();
                    for (int j = 0; j < fb.length; j++) {
                        Field f2 = fb[j];
                        if (f2.getName().equalsIgnoreCase(f1.getName())) {
                            f2.setAccessible(true);
                            f2.set(b, val);
                        }
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return b;
    }

}
