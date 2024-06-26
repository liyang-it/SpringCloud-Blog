package com.liyangit.dto;

/**
 * <h2>博客文章分类查询类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class ArticleClassQueryDTO extends BaseQueryDTO{
	private String className;
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
}
