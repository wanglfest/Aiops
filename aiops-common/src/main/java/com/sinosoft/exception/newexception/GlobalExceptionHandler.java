package com.sinosoft.exception.newexception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.sinosoft.enums.ENUM_EXCEPTION_TYPE;
import com.sinosoft.exception.newexception.res.BaseResponse;
import com.sinosoft.exception.newexception.res.NULLBody;
import com.sinosoft.report.Status;
/**  
 * 【controller异常捕捉器】
 * @author sinfosoft
 * @date 2017年6月5日 下午3:25:52 
 * @version V1.0  
 */
@ControllerAdvice  
@ResponseBody  
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.OK)
	public String handleMethodArgumentNotValidException(Exception e) {

		com.sinosoft.exception.newexception.res.BaseResponse<NULLBody> response = new BaseResponse<NULLBody>();
		Status status =new Status();
		response.setStatus(status);
		if (e instanceof RepeatException){
			status.setStatuscode(ENUM_EXCEPTION_TYPE.REPEAT_REQUEST.getCode());
			List<String> list = new ArrayList<String>();
			list.add(((RepeatException) e).getErrMsg());
			status.setStatusmessage(list);
		}else if (e instanceof CheckException){
			status.setStatuscode(ENUM_EXCEPTION_TYPE.VALIDATION_FAIL.getCode());
			List<String> list = new ArrayList<String>();
			list.add(((CheckException) e).getErrMsg());
			status.setStatusmessage(list);
        }else if (e instanceof RuleException){
        	status.setStatuscode(ENUM_EXCEPTION_TYPE.RULE_FAIL.getCode());
        	List<String> list = new ArrayList<String>();
        	list.add(((CheckException) e).getErrMsg());
        	status.setStatusmessage(list);
        }else if (e instanceof AskException){
        	status.setStatuscode(ENUM_EXCEPTION_TYPE.FAIL_RPC.getCode());
            List<String> list = new ArrayList<String>();
			list.add(((CheckException) e).getErrMsg());
			status.setStatusmessage(list);

        } else {
        	status.setStatuscode(ENUM_EXCEPTION_TYPE.FAIL.getCode());
        	List<String> list = new ArrayList<String>();
        	list.add(ENUM_EXCEPTION_TYPE.FAIL.getMessage());
        	status.setStatusmessage(list);
		}
		
		return JSON.toJSONString(response);
	}

}  