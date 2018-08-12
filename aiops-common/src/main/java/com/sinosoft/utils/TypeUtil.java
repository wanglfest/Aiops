/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class TypeUtil {

    public static String baseTypeToStr(Object fieldVObj) {
        if (fieldVObj == null || fieldVObj instanceof String) {
            return (String) fieldVObj;
        } else if (fieldVObj instanceof Date) {
            return DateUtil.getDateStr((Date) fieldVObj, "yyyy-MM-dd HH:mm:ss");
        } else {
            return fieldVObj.toString();
        }
    }

    public static Object[] toSpecifyType(Object[] src, Class<?>[] specClazz) {
        Object[] tgt = new Object[specClazz.length];
        for (int i = 0; i < specClazz.length; i++) {
            if (src[i] == null) {
                tgt[i] = null;
            } else {
                if (specClazz[i].isAssignableFrom(src[i].getClass())) {
                    tgt[i] = src[i];
                } else {
                    tgt[i] = strToBaseType(src[i].toString(), specClazz[i]);
                }
            }
        }
        return tgt;
    }

    @SuppressWarnings("unchecked")
    public static <T> T strToBaseType(String str, Class<?> baseTypeClazz) {
        try {
            Object baseTypeObj = null;
            if (baseTypeClazz == String.class) {
                baseTypeObj = str;
            } else if (StringUtils.isBlank(str)) {
                baseTypeObj = null;
            } else if (Number.class.isAssignableFrom(baseTypeClazz)) {
                baseTypeObj = baseTypeClazz.getConstructor(String.class).newInstance(str);
            } else if (baseTypeClazz == Date.class) {
                baseTypeObj = DateUtil.getDate(str);
            } else if (baseTypeClazz == Integer.TYPE) {
                baseTypeObj = Integer.parseInt(str);
            } else if (baseTypeClazz == Byte.TYPE) {
                baseTypeObj = Byte.parseByte(str);
            } else if (baseTypeClazz == Float.TYPE) {
                baseTypeObj = Float.parseFloat(str);
            } else if (baseTypeClazz == Double.TYPE) {
                baseTypeObj = Double.parseDouble(str);
            } else if (baseTypeClazz == Short.TYPE) {
                baseTypeObj = Short.parseShort(str);
            } else {
                throw new RuntimeException("不支持的数据类型:" + baseTypeClazz.getName());
            }
            return (T) baseTypeObj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static List<String[]> beanListToAryList(String[] fieldNames, List<?> beanList) {
        if (beanList == null || beanList.size() <= 0) {
            return null;
        }
        try {
            List<String[]> aryList = new ArrayList<String[]>(beanList.size());
            BeanInfo targetInfo = Introspector.getBeanInfo(beanList.get(0).getClass());
            PropertyDescriptor[] targetPds = targetInfo.getPropertyDescriptors();
            Map<String, PropertyDescriptor> targetDescrs = new HashMap<String, PropertyDescriptor>();
            for (int i = 0; i < targetPds.length; i++) {
                targetDescrs.put(targetPds[i].getName(), targetPds[i]);
            }
            for (Object bean : beanList) {
                String[] ary = new String[fieldNames.length];
                for (int i = 0; i < fieldNames.length; i++) {
                    String propName = fieldNames[i];
                    PropertyDescriptor targetPd = targetDescrs.get(propName);
                    Method readMethod = targetPd.getReadMethod();
                    ary[i] = baseTypeToStr(readMethod.invoke(bean));
                }
                aryList.add(ary);
            }
            return aryList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T> List<T> aryListToBeanList(String[] propNames, List<String[]> strAryList, Class<T> beanClazz) {
        try {
            List<T> rtnList = new ArrayList<T>();
            BeanInfo targetInfo = Introspector.getBeanInfo(beanClazz);
            PropertyDescriptor[] targetPds = targetInfo.getPropertyDescriptors();
            Map<String, PropertyDescriptor> targetDescrs = new HashMap<String, PropertyDescriptor>();
            for (int i = 0; i < targetPds.length; i++) {
                targetDescrs.put(targetPds[i].getName(), targetPds[i]);
            }
            for (String[] strAry : strAryList) {
                T rtnObj = beanClazz.newInstance();
                for (int i = 0; i < propNames.length; i++) {
                    String propName = propNames[i];
                    PropertyDescriptor targetPd = targetDescrs.get(propName);
                    Method writeMethod = targetPd.getWriteMethod();
                    Class[] ps = writeMethod.getParameterTypes();
                    Object propValue = strToBaseType(strAry[i], ps[0]);
                    writeMethod.invoke(rtnObj, propValue);
                }
                rtnList.add(rtnObj);
            }
            return rtnList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T aryToBean(String[] propNames, String[] strAry, Class<T> beanClazz) {
        try {
            Object rtnObj = beanClazz.newInstance();
            BeanInfo targetInfo = Introspector.getBeanInfo(beanClazz);
            PropertyDescriptor[] targetPds = targetInfo.getPropertyDescriptors();
            Map<String, PropertyDescriptor> targetDescrs = new HashMap<String, PropertyDescriptor>();
            for (int i = 0; i < targetPds.length; i++) {
                targetDescrs.put(targetPds[i].getName(), targetPds[i]);
            }
            for (int i = 0; i < propNames.length; i++) {
                String propName = propNames[i];
                PropertyDescriptor targetPd = targetDescrs.get(propName);
                Method writeMethod = targetPd.getWriteMethod();
                Class[] ps = writeMethod.getParameterTypes();
                Object propValue = strToBaseType(strAry[i], ps[0]);
                writeMethod.invoke(rtnObj, propValue);
            }
            return (T) rtnObj;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
