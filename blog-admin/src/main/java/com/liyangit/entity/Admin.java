package com.liyangit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <h2>管理员信息表</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName(value = "t_admin")
public class Admin extends BaseEntity {
	@NotBlank(message = "用户名不能为空 并且长度最大限制30个字符")
	private String username;
	@NotBlank(message = "密码不能为空 并且长度最大限制30个字符")
	private String password;
	@NotNull(message = "请选择状态")
	private Boolean status;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
