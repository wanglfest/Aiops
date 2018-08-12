/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils.xml;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

public class XStreamMapConverter extends AbstractCollectionConverter {

    /**
    *
    * 方法功能描述
    *
    * @param cMapper
    * 参数
    */
    public XStreamMapConverter(Mapper cMapper) {
        super(cMapper);
    }

    /**
    *
    * 方法功能描述
    *
    * @param cType
    * 参数
    * @return boolean
    */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(Class cType) {
        return cType.equals(HashMap.class) || cType.equals(Hashtable.class) || cType.getName().equals("java.util.LinkedHashMap")
                || cType.getName().equals("sun.font.AttributeMap");
    }

    /**
    *
    * 方法功能描述
    *
    * @param cSource
    * 参数
    * @param cWriter
    * 参数
    * @param cContext
    * 参数
    */
    @SuppressWarnings("rawtypes")
    @Override
    public void marshal(Object cSource, HierarchicalStreamWriter cWriter, MarshallingContext cContext) {
        Map tMap = (Map) cSource;
        for (Iterator tIterator = tMap.entrySet().iterator(); tIterator.hasNext();) {
            Entry tEntry = (Entry) tIterator.next();
            cWriter.startNode(tEntry.getKey() == null ? "null" : tEntry.getKey().toString());
            cWriter.setValue(tEntry.getValue() == null ? "" : tEntry.getValue().toString());
            cWriter.endNode();
        }
    }

    /**
    *
    * 方法功能描述
    *
    * @param cReader
    * 参数
    * @param cContext
    * 参数
    */
    @SuppressWarnings("rawtypes")
    @Override
    public Object unmarshal(HierarchicalStreamReader cReader, UnmarshallingContext cContext) {
        Map tMap = (Map) createCollection(cContext.getRequiredType());
        populateMap(cReader, cContext, tMap);
        return tMap;
    }

    /**
    *
    * 方法功能描述
    *
    * @param cReader
    * 参数
    * @param cContext
    * 参数
    * @param map
    * 参数
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void populateMap(HierarchicalStreamReader cReader, UnmarshallingContext cContext, Map cMap) {
        while (cReader.hasMoreChildren()) {
            cReader.moveDown();
            Object tKey = cReader.getNodeName();
            Object tValue = cReader.getValue();
            cMap.put(tKey, tValue);
            cReader.moveUp();
        }
    }

}
