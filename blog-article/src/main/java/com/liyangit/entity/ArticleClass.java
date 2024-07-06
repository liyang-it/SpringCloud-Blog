package com.liyangit.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;


/**
 * <h2>博客文章分类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@TableName(value = "t_article_class")
public class ArticleClass extends BaseEntity {
	@NotBlank(message = "分类不能为空")
	private String className;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
}
