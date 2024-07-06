package com.liyangit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyangit.entity.ArticleEsEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <h2>博客文章信息Mapper</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface ArticleEsMapper extends BaseMapper<ArticleEsEntity> {
	ArticleEsEntity getArticleById(@Param("id") String id);
}

