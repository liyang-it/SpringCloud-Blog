package com.liyangit.result;

/**
 * <h2>通用状态码</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public enum ResultCode {
	SUCCESS(200, "请求成功"),
	ERROR_NOT_FOUND(404, "无法识别的请求,请求路径不存在"),
	ERROR_SERVICE_NOT_FOUND(500, "服务器迷路啦，30秒后再重试一下. O(∩_∩)O "),
	ERROR_SERVICE_RESPONSE_STATUS(501, "内部服务器异常，请联系管理员 "),
	ERROR_SERVICE(503, ""),
	ERROR_TOKEN_EXPIRED(10002, "登录信息已过期"),
	ERROR_TOKEN_VERIFICATION(10003, "登录会话无效"),
	ERROR_NOT_FOUND_USER(10004, "用户名或密码错误"),
	ERROR_ADMIN_DISABLE(10004, "用户已被禁用"),
	ERROR_INSERT(20001, "数据新增失败"),
	ERROR_UPDATE(20002, "数据更新失败"),
	ERROR_DELETE(20003, "数据删除失败"),
	FEIGN_ERROR(30001, "服务调用失败"),
	FEIGN_REQUEST_ERROR(30002, "服务调用请求失败")
	;
	
	
	private int code;
	private String msg;
	ResultCode(int code, String msg) {
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
