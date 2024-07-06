package com.liyangit.repository;

import com.liyangit.entity.ArticleEsEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * <h2>博客文章es操作库</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Repository
public interface ArticleEsRepository extends ElasticsearchRepository<ArticleEsEntity, String> {
}
