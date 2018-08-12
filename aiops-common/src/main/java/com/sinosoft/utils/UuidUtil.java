/**
 * @Copyright ®2015 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 电子商务云服务平台
 * 创建日期 : 2017年3月15日
 * 修改历史 : 
 *     1. [2017年3月15日]创建文件 by chihouliang
 */
package com.sinosoft.utils;

import java.util.UUID;

/**  
 * 【类或接口功能描述】
 * @author chihouliang
 * @date 2017年3月15日 上午11:20:14 
 * @version V1.0  
 */
public class UuidUtil {

    /**
     * 获取uuid，去掉字符中间横线，并且转换大写
     * @return 获取uuid
     */
    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "").toUpperCase();
        return uuid;
    }

}
