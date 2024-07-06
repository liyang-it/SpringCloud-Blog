package com.liyangit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;

/**
 * <h2>博客文章信息</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@TableName(value = "t_article")
public class Article extends BaseEntity{
	@NotBlank(message = "分类不能为空")
	private String className;
	
	@NotBlank(message = "标题不能为空")
	private String title;
	
	@TableField(exist = false)
	public String content;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
}
