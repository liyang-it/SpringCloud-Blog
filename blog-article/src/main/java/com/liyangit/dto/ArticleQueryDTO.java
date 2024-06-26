package com.liyangit.dto;

/**
 * <h2>博客文章查询类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class ArticleQueryDTO extends BaseQueryDTO{
	private String className;
	private String title;
	
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
}
