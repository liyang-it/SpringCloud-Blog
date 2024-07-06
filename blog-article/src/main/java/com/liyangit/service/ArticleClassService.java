package com.liyangit.service;

import com.liyangit.dto.ArticleClassQueryDTO;
import com.liyangit.entity.ArticleClass;
import com.liyangit.result.ResponseData;

/**
 * <h2>博客文章分类服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface ArticleClassService {
	ResponseData insertArticleClass(ArticleClass articleClass);
	ResponseData updateArticleClass(ArticleClass articleClass);
	ResponseData deleteArticleClass(ArticleClass articleClass);
	ResponseData pageQuery(ArticleClassQueryDTO dto);
	ResponseData listQuery(ArticleClassQueryDTO dto);
}
