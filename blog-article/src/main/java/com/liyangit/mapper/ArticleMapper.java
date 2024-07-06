package com.liyangit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyangit.entity.Article;
import org.apache.ibatis.annotations.Param;

/**
 * <h2>博客文章信息Mapper</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface ArticleMapper extends BaseMapper<Article> {
	Article getArticleById(@Param("id") String id);
	int updateById(Article article);
}

