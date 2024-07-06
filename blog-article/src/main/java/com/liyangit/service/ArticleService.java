package com.liyangit.service;

import com.liyangit.entity.Article;
import com.liyangit.result.ResponseData;

/**
 * <h2>博客文章服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface ArticleService {
	ResponseData insertArticle(Article article);
	ResponseData updateArticle(Article article);
	ResponseData deleteArticle(Article article);
	ResponseData getArticleById(String id);
}
