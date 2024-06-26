package com.liyangit.result;

/**
 * <h2>后台管理状态码消息管理</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public enum AdminResultCode{
	ERROR_USERNAME_UNIQUE(10001, "用户名已存在"),
	
	;
	
	private int code;
	private String msg;
	AdminResultCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
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
}
