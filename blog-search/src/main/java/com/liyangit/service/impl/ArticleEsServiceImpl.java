package com.liyangit.service.impl;

import com.liyangit.dto.ArticleEsQueryDTO;
import com.liyangit.entity.ArticleEsEntity;
import com.liyangit.repository.ArticleEsRepository;
import com.liyangit.result.ResponseData;
import com.liyangit.service.ArticleEsService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * <h2>博客文章es服务实现层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Service
public class ArticleEsServiceImpl implements ArticleEsService {
	private final ArticleEsRepository repository;
	private final ElasticsearchRestTemplate template;
	
	public ArticleEsServiceImpl(ArticleEsRepository repository, ElasticsearchRestTemplate template) {
		this.repository = repository;
		this.template = template;
	}
	
	@Override
	public void save(ArticleEsEntity article) {
		repository.save(article);
	}
	
	@Override
	public ArticleEsEntity findById(String id) {
		// 存在则返回, 不存在则返回默认null
		return repository.findById(id).orElse(new ArticleEsEntity());
	}
	
	@Override
	public void deleteById(String id) {
		repository.deleteById(id);
	}
	
	@Override
	public long count() {
		return repository.count();
	}
	
	@Override
	public boolean existsById(String id) {
		return repository.existsById(id);
	}
	
	@Override
	public ResponseData pageQuery(ArticleEsQueryDTO dto) {
		if (dto.getPage() == null) {
			dto.setPage(0);
		}
		
		if (dto.getLimit() == null || dto.getLimit() == 0) {
			dto.setLimit(10);
		}
		
		
		// 构建查询条件
		NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
		
		BoolQueryBuilder filter = QueryBuilders.boolQuery();
		
		// 分页
		queryBuilder.withPageable(PageRequest.of(dto.getPage(), dto.getLimit()));
		
		// 如果使用了关键字查询，那么直接or查询,不在独立条件查询
		if (StringUtils.isNotBlank(dto.getKeyword())) {
			// 使用multi_match_query来搜索多个字段
			queryBuilder.withQuery(new MultiMatchQueryBuilder(dto.getKeyword(), "className", "title", "content").type(MultiMatchQueryBuilder.Type.BEST_FIELDS));
			
		} else {
			// 添加基本分词查询
			if (StringUtils.isNotBlank(dto.getId())) {
				filter.must(QueryBuilders.termQuery("id", dto.getId()));
			}
			
			// 模糊查询, 如果使用 wildcardQuery 则需要自己手动将  "*" 关键字增加到值当中，例如查询 包含 李， *李*
			if (StringUtils.isNotBlank(dto.getClassName())) {
				filter.must(QueryBuilders.matchQuery("className", dto.getClassName()));
			}
			
			if (StringUtils.isNotBlank(dto.getTitle())) {
				filter.must(QueryBuilders.matchQuery("title", dto.getTitle()));
			}
			
			if (StringUtils.isNotBlank(dto.getContent())) {
				filter.must(QueryBuilders.matchQuery("content", dto.getContent()));
			}
		}
		
		// 将过滤条件放入查询
		queryBuilder.withFilter(filter);
		
		// 排序
		queryBuilder.withSort(SortBuilders.fieldSort("createdTime").order(SortOrder.DESC));
		queryBuilder.withSort(SortBuilders.fieldSort("updatedTime").order(SortOrder.DESC));
		
		NativeSearchQuery nativeSearchQuery = queryBuilder.build();

		nativeSearchQuery.addFields("id", "className", "title", "content", "createdBy", "createdTime", "updatedBy", "updatedTime");
		
		// 使用ElasticsearchRestTemplate进行复杂查询
		AggregatedPage<ArticleEsEntity> page = template.queryForPage(nativeSearchQuery, ArticleEsEntity.class);
		
		return ResponseData.success(page);
	}
}
