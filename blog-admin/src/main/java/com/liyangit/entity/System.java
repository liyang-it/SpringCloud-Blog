package com.liyangit.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <h2>系统配置信息</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@TableName(value = "t_system")
public class System {
	@NotNull(message = "无效数据")
	private Integer id;
	
	@NotBlank(message = "字典编码不能为空")
	private String code;
	
	@NotBlank(message = "字典标签不能为空")
	private String label;
	
	@NotBlank(message = "字典值不能为空")
	private String value;
	
	private String remark;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
