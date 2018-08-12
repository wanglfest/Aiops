/**
 * @Copyright ®2015 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 电子商务云服务平台
 * 创建日期 : 2017年3月13日
 * 修改历史 : 
 *     1. [2017年3月13日]创建文件 by chihouliang
 */
package com.sinosoft.utils;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * 【对象、xml互转工具类】
 * @author chihouliang
 * @date 2017年3月13日 上午9:32:30 
 * @version V1.0
 */
public class XStreamUtil {
    /**
    *
    */
    private static final String DELIMITER = ".";
    /**
    * xstream xml转换引擎
    */
    private XStream xstream;

    /**
    * 创建XStreamComponent实例
    *
    * @return XStreamComponent
    */
    public static XStreamUtil newInstance() {
        XStreamUtil tXmlComponent = new XStreamUtil();
        return tXmlComponent;
    }

    /**
    * XStreamComponent构造函数
    */
    public XStreamUtil() {
        this.xstream = new XStream(new DomDriver());
        xstream.setMode(XStream.NO_REFERENCES);
    }

    /**
    * 将对象序列化为xml string
    *
    * @param cObj
    * 参数
    * @return String
    */
    public String toXML(Object cObj) {
        String xml = this.xstream.toXML(cObj);

        if (!StringUtil.isBlank(xml)) {
            xml = xml.replace("__", "_");
        }

        return xml;
    }

    /**
    * 将xml string反序列化为object
    *
    * @param cXml
    * 参数
    * @return Object
    */
    public Object fromXML(String cXml) {
        int tIndex = cXml.indexOf("?>");
        if (tIndex >= 0) {
            cXml = cXml.substring(tIndex + 2);
            return this.xstream.fromXML(cXml);
        } else {
            return this.xstream.fromXML(cXml);
        }
    }

    /**
    * 激活xstream注解引擎
    *
    * @param cType
    * Class
    */
    public void processAnnotations(Class<?> cType) {
        xstream.processAnnotations(cType);
    }

    /**
    * 激活xstream注解引擎
    *
    * @param cTypes
    * Class[]
    */
    @SuppressWarnings("rawtypes")
    public void processAnnotations(Class[] cTypes) {
        this.xstream.processAnnotations(cTypes);
        //xstream.aliasSystemAttribute(null, "class");
    }

    /**
    * 激活xstream注解引擎
    * 去除class属性
    * @param cTypes
    * Class[]
    */
    @SuppressWarnings("rawtypes")
    public void processAnnotationsNoClass(Class[] cTypes) {
        this.xstream.processAnnotations(cTypes);
        xstream.aliasAttribute(null, "class");
    }

    /**
    * alias Class
    *
    * @param cName
    * 参数
    * @param cType
    * 参数
    */
    public void alias(String cName, Class<?> cType) {
        this.xstream.alias(cName, cType);
    }

    /**
    * alias Class
    *
    * @param cTypes
    * Class[]
    */
    public void alias(Class<?>[] cTypes) {
        for (Class<?> tType : cTypes) {
            String tClassName = tType.getName();
            try {
                String[] tClassNames = StringUtils.split(tClassName, XStreamUtil.DELIMITER);
                this.xstream.alias(tClassNames[tClassNames.length - 1], tType);
            } catch (Exception ex) {
                this.xstream.alias(tClassName, tType);
            }
            // xstream.aliasSystemAttribute(null, "class");
        }
    }

    /**
    * alias Response model of Webservice
    *
    * @param cTypes
    * 参数
    */
    //    public void aliasResponse(Class<?>[] cTypes) {
    //    this.alias("Response", Response.class);
    //    this.alias("TransResult", TransResult.class);
    //    this.alias(cTypes);
    //    }
    /**
    * 注册xstream's converter
    * @param cConverter
    * 参数
    */
    public void registerConverter(Converter cConverter) {
        this.xstream.registerConverter(cConverter);
    }

    /**
    * 设置bean属性为xml的属性
    * @param cDefinedIn
    * 参数
    * @param cFieldName
    * 参数
    */
    public void useAttributeFor(Class<?> cDefinedIn, String cFieldName) {
        this.xstream.useAttributeFor(cDefinedIn, cFieldName);
    }
}
