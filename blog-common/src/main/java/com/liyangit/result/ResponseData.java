package com.liyangit.result;

import com.alibaba.fastjson2.JSON;

/**
 * <h2>通用请求结果JSON数据</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class ResponseData {
	private int code;
	private String msg;
	private Object data;
	
	
	private ResponseData() {
	}
	
	private ResponseData(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public static ResponseData normal(int code, String msg) {
		return new ResponseData(code, msg, null);
	}
	
	
	public static ResponseData ServiceException() {
		return new ResponseData(ResultCode.ERROR_SERVICE.getCode(), ResultCode.ERROR_SERVICE.getMsg(), null);
	}
	
	public static ResponseData success() {
		return new ResponseData(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), null);
	}
	
	public static ResponseData success(Object data) {
		return new ResponseData(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
