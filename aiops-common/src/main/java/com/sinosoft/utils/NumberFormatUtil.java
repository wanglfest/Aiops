/**
 * @Copyright ®2017 Sinosoft Co. Ltd. All rights reserved.<br/>
 * 项目名称 : 交银康联智能运营平台
 * 创建日期 : 2017年5月25日
 * 修改历史 : 
 *     1. [2017年5月25日]创建文件 by sinosoft
 */
package com.sinosoft.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**  
 * 【数字格式胡工具】
 * @author sinfosoft
 * @date 2017年7月19日 上午11:26:18 
 * @version V1.0  
*/
public abstract class NumberFormatUtil {

    /**
     * 金额分组转换，国际格式
     * @param num
     * @return
     */
    public static String formatAmount(BigDecimal money) {
        if (money == null) {
            return null;
        }
        if (money.compareTo(new BigDecimal(0)) == 0) {
            return "0";
        }
        double data = money.doubleValue();
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(data);
    }

    /**
     * 数值转百分数
     * @param decima
     * @return
     */
    public static String decimaTransPercent(BigDecimal decima) {
        if (decima == null) {
            return null;
        }
        DecimalFormat df = new DecimalFormat("0.00%");
        return df.format(decima);
    }

}
