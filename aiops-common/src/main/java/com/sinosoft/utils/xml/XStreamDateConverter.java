/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils.xml;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.utils.StringUtil;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

public class XStreamDateConverter extends AbstractSingleValueConverter {
    /**
    * 定义日期格式
    */
    private static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
    * 当传入的Class对象是Date类型是，返回true
    * @param cType Date类型的Class对象
    * @return true为Date类型
    */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(Class cType) {
        return cType.equals(Date.class);
    }

    /**
    * 获取HierarchicalStreamReader对象中时间，并转换为Date对象。使用yyyy-MM-dd格式
    * @param cDate HierarchicalStreamReader对象
    * @return 转换后的Object对象(Date类型)
    */
    @Override
    public Object fromString(String cDate) {
        // 这里将字符串转换成日期
        try {
            if (StringUtil.isBlank(cDate))
                return null;
            return DEFAULT_DATEFORMAT.parseObject(cDate);
        } catch (ParseException e) {
            throw new ConversionException("Cannot parse date " + cDate);
        }
    }

    /**
    * 把时间转换成字符串，并写入HierarchicalStreamWriter对象中。日期使用yyyy-MM-dd格式进行转换成字符串
    * @param cDate Date类型的时间对象
    */
    @Override
    public String toString(Object cDate) {
        // 这里将日期转换成字符串
        return DEFAULT_DATEFORMAT.format((Date) cDate);
    }
}
