package com.sinosoft.exception.newexception.res;

/**  
 * 【Function:空对象,用在泛型中,表示没有额外的请求参数或者返回参数】
 * @author sinfosoft
 * @date 2017年6月5日 下午3:25:52 
 * @version V1.0  
 */
public class NULLBody {
    public NULLBody() {}

    public static NULLBody create(){
        return new NULLBody();
    }
}
