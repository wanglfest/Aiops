package com.sinosoft.exception;
/**
 * 多数情况下，创建自定义异常需要继承Exception，继承Exception的子类RuntimeException
 * @author genghbsh
 *
 */
public class CommonRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code ;  //异常对应的返回码
	private String msg;  //异常对应的描述信息
	
	public CommonRuntimeException() {
		super();
	}

	public CommonRuntimeException(String message) {
		super(message);
		msg = message;
	}

	public CommonRuntimeException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getcode() {
		return code;
	}

	public String getmsg() {
		return msg;
	}
}