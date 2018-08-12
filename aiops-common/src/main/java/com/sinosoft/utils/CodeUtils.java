package com.sinosoft.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 随机码生成工具
 * @author vampire
 * @date 2016年6月15日
 *
 */
public final class CodeUtils {
	private static final Random RANDOM = new Random(System.currentTimeMillis());
	private static final String CHARS = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";
	
	/**
	 * 获取随机数字字符串
	 * @param length 字符串长度
	 * @return
	 */
	public static final String getRandomNumber(int length){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++){
			sb.append(RANDOM.nextInt(10));
		}
		return sb.toString();
	}
	
	/**
	 * 获取随机数字大写字母字符串（为了防止混淆，没有0,1,I,L,O）
	 * @param length 字符串长度
	 * @return
	 */
	public static final String getRandomChar(int length){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++){
			sb.append(CHARS.charAt(RANDOM.nextInt(31)));
		}
		return sb.toString();
	}
	
	/**
	 * 获取随机数字大小写字母字符串（为了防止混淆，没有0,1,I,L,O,i,l,o）
	 * @param length 字符串长度
	 * @return
	 */
	public static final String getRandomCaseSensitiveChar(int length){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++){
			sb.append(CHARS.charAt(RANDOM.nextInt(54)));
		}
		return sb.toString();
	}
	
	/**
	 * 生成32位大写uuid字符串
	 * @return
	 */
	public static final String id(){
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
}
