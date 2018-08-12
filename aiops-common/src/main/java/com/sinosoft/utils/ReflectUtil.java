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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 反射工具类.为方便可以端使用,通常返回目标对象.
 * 
 * @author guqc_ebiz 2016-05-04
 * @version 1.0
 */
public class ReflectUtil {

    private static final Object[] emptyAry = new Object[] {};

    private static final Method getClassMethod;
    static {
        try {
            getClassMethod = Object.class.getDeclaredMethod("getClass");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static <T> T mapToBean(Map<String, Object> propMap, Class<? extends T> beanClazz) {
        try {
            T bean = beanClazz.newInstance();
            if (propMap != null && propMap.size() > 0) {
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] beanPds = beanInfo.getPropertyDescriptors();
                for (int i = 0; i < beanPds.length; i++) {
                    Method writeMethod = beanPds[i].getWriteMethod();
                    if (writeMethod == null) {
                        continue;
                    }
                    Object mapV = propMap.get(beanPds[i].getName());
                    if (mapV == null) {
                        continue;
                    }
                    Class[] ps = writeMethod.getParameterTypes();
                    if (ps == null || ps.length != 1) {
                        continue;
                    }
                    writeMethod.invoke(bean, convertToDestType(mapV, (ps[0])));
                }
            }
            return bean;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Map<String, Object> beanToMap(Object bean) {
        if (bean == null) {
            return null;
        }
        try {
            Map<String, Object> propMap = new HashMap<String, Object>();
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            PropertyDescriptor[] beanPds = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < beanPds.length; i++) {
                Method readMethod = beanPds[i].getReadMethod();
                if (readMethod == null || readMethod.equals(getClassMethod)) {
                    continue;
                }
                propMap.put(beanPds[i].getName(), readMethod.invoke(bean, emptyAry));
            }
            return propMap;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Field getField(final Object object, final String fieldName) {
        if (object == null) {
            return null;
        }
        if (StringUtils.isBlank(fieldName)) {
            return null;
        }
        Field field = null;
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                field = superClass.getDeclaredField(fieldName);
                if (field != null) {
                    break;
                }
            } catch (NoSuchFieldException e) {
                continue;
            }
        }
        return field;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(final Object object, final Field field) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
        try {
            return (T) field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T getFieldValue(final Object object, final String fieldName) {
        if (fieldName.indexOf('.') >= 0) {
            Object subObject = getFieldValue(object, fieldName.substring(0, fieldName.indexOf('.')));
            return getFieldValue(subObject, fieldName.substring(fieldName.indexOf('.') + 1));
        } else {
            if (object == null) {
                return null;
            }
            if (object instanceof Map) {
                return (T) ((Map) object).get(fieldName);
            } else {
                Field field = getField(object, fieldName);
                if (field == null) {
                    throw new RuntimeException("there is no field named " + fieldName + " in class-" + object.getClass().getName());
                }
                return getFieldValue(object, field);
            }
        }
    }

    public static Object setFieldValue(final Object object, final Field field, final Object value) {
        if (!Modifier.isPublic(field.getModifiers())) {
            field.setAccessible(true);
        }
        try {
            Object preValue = field.get(object);
            field.set(object, convertToDestType(value, field.getType()));
            return preValue;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object setFieldValue(final Object object, final String fieldName, final Object value) {
        if (object instanceof Map) {
            return ((Map) object).put(fieldName, value);
        } else {
            Field field = getField(object, fieldName);
            if (field == null) {
                throw new RuntimeException("there is no field named " + fieldName + " in class-" + object.getClass().getName());
            }
            return setFieldValue(object, field, value);
        }
    }

    /**
     * 把source中的内容Copy到指定target
     * 
     * @param src
     * @param target
     */
    public static <T> T copyProperties(Object source, T target) {
        return copyProperties(source, target, null);
    }

    /**
     * 把src中的内容Copy到指定target，但是忽略指定的属性
     * 
     * @param source
     *            源对象
     * @param target
     *            目标对象
     * @param ignoreProperties
     *            指定忽略的属性
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T copyProperties(Object source, T target, String[] ignoreProperties) {
        if (null == source || null == target) {
            return null;
        }
        try {
            BeanInfo sourceInfo = Introspector.getBeanInfo(source.getClass());
            Map targetDescrs = new HashMap();
            // init
            {
                BeanInfo targetInfo = Introspector.getBeanInfo(target.getClass());
                PropertyDescriptor[] targetPds = targetInfo.getPropertyDescriptors();
                for (int i = 0; i < targetPds.length; i++) {
                    targetDescrs.put(targetPds[i].getName(), targetPds[i]);
                }
            }
            PropertyDescriptor[] pds = sourceInfo.getPropertyDescriptors();
            Object[] params = new Object[1];
            for (int i = 0; i < pds.length; i++) {
                String property = pds[i].getName();
                if (ArrayUtils.contains(ignoreProperties, property)) {
                    continue;
                }
                PropertyDescriptor targetPD = (PropertyDescriptor) targetDescrs.get(property);
                if (targetPD == null) {
                    continue;
                }
                Method readMethod = pds[i].getReadMethod();
                if (readMethod == null) {
                    continue;
                }
                Method writeMethod = targetPD.getWriteMethod();
                if (writeMethod == null) {
                    continue;
                }
                Type[] ps = writeMethod.getGenericParameterTypes();
                if (ps == null || ps.length != 1) {
                    continue;
                }
                Object propSrc = readMethod.invoke(source, emptyAry);
                if (ps[0] instanceof ParameterizedType) {
                    ParameterizedType writeType = (ParameterizedType) (ps[0]);
                    if (!readMethod.getGenericReturnType().equals(writeType)) {
                        throw new RuntimeException("Property:" + property + " have different generic type in source and target!");
                    }
                    params[0] = convertToDestType(propSrc, (Class) (writeType.getRawType()));
                } else {
                    params[0] = convertToDestType(propSrc, (Class) (ps[0]));
                }
                writeMethod.invoke(target, params);
            }
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将对象的所有null字段，赋值为empty(比如String为"").
     * 
     * @param obj
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T fieldNullToEmpty(T obj) {
        try {
            BeanInfo sourceInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] pds = sourceInfo.getPropertyDescriptors();
            for (int i = 0; i < pds.length; i++) {
                Method readMethod = pds[i].getReadMethod();
                Class propClazz = pds[i].getPropertyType();
                if (propClazz.isPrimitive()) {
                    continue;
                }
                Object propPre = readMethod.invoke(obj, emptyAry);
                if (propPre != null) {
                    continue;
                }
                Method writeMethod = pds[i].getWriteMethod();
                if (propClazz == String.class) {
                    writeMethod.invoke(obj, "");
                } else if (Number.class.isAssignableFrom(propClazz)) {
                    writeMethod.invoke(obj, propClazz.getConstructor(String.class).newInstance("0"));
                } else if (!propClazz.isInterface()) {/*
                                                       * if propClazz is abstract
                                                       * class,this will throw an
                                                       * InstantiationException
                                                       */
                    writeMethod.invoke(obj, propClazz.newInstance());
                } else {
                    if (List.class.isAssignableFrom(propClazz)) {
                        writeMethod.invoke(obj, new ArrayList());
                    } else if (Set.class.isAssignableFrom(propClazz)) {
                        writeMethod.invoke(obj, new HashSet());
                    } else if (Map.class.isAssignableFrom(propClazz)) {
                        writeMethod.invoke(obj, new HashMap());
                    }
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 自动转换类型，目前只自动转换数值类型
     * 
     * @param srcTypeValue
     *            原类型的对象
     * @param destType
     *            类型
     * @return Object 指定类型的对象
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object convertToDestType(Object srcTypeValue, Class destType) {
        if (srcTypeValue == null) {
            return null;
        }
        if (destType.isAssignableFrom(srcTypeValue.getClass())) {
            return srcTypeValue;
        }
        if (Date.class.isAssignableFrom(destType)) {
            if (srcTypeValue instanceof String) {
                String srcStrValue = (String) srcTypeValue;
                if (srcStrValue.trim().equals("")) {
                    return null;
                }
                return DateUtil.getDate(srcStrValue);
            }
            if (srcTypeValue instanceof Integer) {
                return new Date(1000 * Long.valueOf(srcTypeValue.toString()));
            } else {
                return new Date(Long.valueOf(srcTypeValue.toString()));
            }
        } else if (destType == String.class) {
            return String.valueOf(srcTypeValue);
        } else {
            if (srcTypeValue instanceof String) {
                String srcStrValue = (String) srcTypeValue;
                if (srcStrValue.trim().equals("")) {
                    return null;
                }
                if (destType == BigDecimal.class) {
                    return new BigDecimal(srcTypeValue.toString());
                }
                if (destType == BigInteger.class) {
                    return new BigInteger(srcTypeValue.toString());
                }
                if (destType == Long.class || destType == Long.TYPE) {
                    return new Long(srcStrValue.toString());
                }
                if (destType == Integer.class || destType == Integer.TYPE) {
                    return new Integer(srcStrValue.toString());
                }
                if (destType == Short.class || destType == Short.TYPE) {
                    return new Short(srcStrValue.toString());
                }
                if (destType == Float.class || destType == Float.TYPE) {
                    return new Float(srcStrValue.toString());
                }
                if (destType == Double.class || destType == Double.TYPE) {
                    return new Double(srcStrValue.toString());
                }
                if (destType == Byte.class || destType == Byte.TYPE) {
                    return new Byte(srcStrValue.toString());
                }
            } else {
                Number numValue = (Number) srcTypeValue;
                if (destType == BigDecimal.class) {
                    return new BigDecimal(srcTypeValue.toString());
                }
                if (destType == BigInteger.class) {
                    return new BigInteger(srcTypeValue.toString());
                }
                if (destType == Long.class || destType == Long.TYPE) {
                    return new Long(numValue.longValue());
                }
                if (destType == Integer.class || destType == Integer.TYPE) {
                    return new Integer(numValue.intValue());
                }
                if (destType == Short.class || destType == Short.TYPE) {
                    return new Short(numValue.shortValue());
                }
                if (destType == Float.class || destType == Float.TYPE) {
                    return new Float(numValue.floatValue());
                }
                if (destType == Double.class || destType == Double.TYPE) {
                    return new Double(numValue.doubleValue());
                }
                if (destType == Byte.class || destType == Byte.TYPE) {
                    return new Byte(numValue.byteValue());
                }
            }
        }
        throw new RuntimeException(srcTypeValue + " can't convert to " + destType.getName());
    }
}
