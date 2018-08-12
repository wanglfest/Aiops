package com.sinosoft.check;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sinosoft.utils.StringUtil;

public class CheckRequsetXmlParam{
	private static Logger logger=LoggerFactory.getLogger(CheckRequsetXmlParam.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> void checkRequestXMl(T request) throws Exception {
	    logger.info("=========数据校验开始！");
		if(request!=null){
			Class<T> clazz = (Class<T>) request.getClass();
			if(clazz.isAnnotationPresent(CheckParam.class)){
				Method[] methods = clazz.getMethods();
				if (methods != null) {
					for (Method method : methods) {
						if (method.isAnnotationPresent(CheckParam.class)) {
							CheckParam param = method.getAnnotation(CheckParam.class);
							if (method.getName().startsWith("get")) {
								try {
									Object returnValue = method.invoke(request);
									if (returnValue == null) {
										logger.info(param.msg()+"不能为空！");
										throw new Exception(param.msg()+"不能为空！");
									}
									if (method.getReturnType() == String.class) {
										String value = (String) returnValue;
										if (StringUtil.isBlank(value)) {
											logger.info(param.msg()+"不能为空！");
											throw new Exception(param.msg()+"不能为空！");
										}
									}
									if (method.getReturnType() == List.class) {
										Object obj=method.invoke(request);
										List list=(List) obj;
										if(list==null || list.size()==0){
											logger.info(param.msg()+"不能为空！");
											throw new Exception(param.msg()+"不能为空！");
										}
										if(list!=null && list.size()>0){
											Iterator iterator=list.iterator();
											while(iterator.hasNext()){
												Object listObj=iterator.next();
												if(listObj.getClass().isAnnotationPresent(CheckParam.class)){
													checkRequestXMl(listObj);
												}
											}
										}
									}
								} catch (IllegalAccessException
										| IllegalArgumentException
										| InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
						if(method.getReturnType().isAnnotationPresent(CheckParam.class)){
							try {
								Object obj=method.invoke(request);
								if(obj!=null){
									checkRequestXMl(obj);
								}else {
									CheckParam param=method.getReturnType().getAnnotation(CheckParam.class);
									logger.info(param.msg()+"不能为空！");
									throw new Exception(param.msg()+"不能为空！");
								}
							} catch (IllegalAccessException | IllegalArgumentException
									| InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		logger.info("=========数据校验完毕！");
	}
}
