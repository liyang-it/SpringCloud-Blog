package com.liyangit.dto;

/**
 * <h2>管理员查询类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class AdminQueryDTO extends BaseQueryDTO{
	private String username;
	private Boolean status;
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
