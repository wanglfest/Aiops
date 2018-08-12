package com.sinosoft.exception.newexception.res;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sinosoft.enums.ENUM_EXCEPTION_TYPE;
import com.sinosoft.report.Status;
import com.sinosoft.utils.StringUtil;

public class BaseResponse<T> implements Serializable{
    private static final long serialVersionUID = 360558295891796059L;
	
	private Status status;
	
	private T dataBody;

	public BaseResponse() {}

	public BaseResponse(T dataBody) {
		this.dataBody = dataBody;
	}

	public BaseResponse(String code, String message) {
		this.status = new Status();
		this.status.setStatuscode(code);
		List<String> list = new ArrayList<String>();
		list.add(message);
		this.status.setStatusmessage(list);
	}

	public BaseResponse(String code, String message, T dataBody) {
		this.status = new Status();
		this.status.setStatuscode(code);
		List<String> list = new ArrayList<String>();
		list.add(message);
		this.status.setStatusmessage(list);
		this.dataBody = dataBody;
	}


	public static <T> BaseResponse<T> create(T t){
		return new BaseResponse<T>(t);
	}

	public static <T> BaseResponse<T> create(T t, ENUM_EXCEPTION_TYPE statusEnum){
		return new BaseResponse<T>(statusEnum.getCode(), statusEnum.getMessage(), t);
	}

	public static <T> BaseResponse<T> createSuccess(T t, String message){
		return new BaseResponse<T>(ENUM_EXCEPTION_TYPE.SUCCESS.getCode(), StringUtil.isNullOrEmpty(message) ? ENUM_EXCEPTION_TYPE.SUCCESS.getMessage() : message, t);
	}

	public static <T> BaseResponse<T> createFail(T t, String message){
		return new BaseResponse<T>(ENUM_EXCEPTION_TYPE.FAIL.getCode(), StringUtil.isNullOrEmpty(message) ? ENUM_EXCEPTION_TYPE.FAIL.getMessage() : message, t);
	}

	public static <T> BaseResponse<T> create(T t, ENUM_EXCEPTION_TYPE statusEnum, String message){

		return new BaseResponse<T>(statusEnum.getCode(), message, t);
	}


	public T getDataBody() {
		return dataBody;
	}

	public void setDataBody(T dataBody) {
		this.dataBody = dataBody;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	

}
