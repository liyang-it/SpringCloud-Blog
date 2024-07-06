package com.liyangit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <h2>博客文章内容</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@TableName(value = "t_article_content")
public class ArticleContent {
	@TableId(type = IdType.INPUT)
	private String articleId;
	
	private String content;
	
	public String getArticleId() {
		return articleId;
	}
	
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
}
