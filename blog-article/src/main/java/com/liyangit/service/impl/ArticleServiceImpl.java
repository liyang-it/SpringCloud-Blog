package com.liyangit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyangit.async.AsyncService;
import com.liyangit.constant.RedisKeyPrefix;
import com.liyangit.dto.ArticleQueryDTO;
import com.liyangit.entity.Article;
import com.liyangit.entity.ArticleContent;
import com.liyangit.incrementer.DefaultIdentifierGenerator;
import com.liyangit.mapper.ArticleContentMapper;
import com.liyangit.mapper.ArticleMapper;
import com.liyangit.result.ResponseData;
import com.liyangit.service.ArticleService;
import com.liyangit.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <h2>博客文章服务实现层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	private final AsyncService asyncService;
	private final ArticleMapper mapper;
	private final ArticleContentMapper contentMapper;
	private final RedisUtils redisUtils;
	
	
	public ArticleServiceImpl(AsyncService asyncService, ArticleMapper mapper, ArticleContentMapper contentMapper, RedisUtils redisUtils) {
		this.asyncService = asyncService;
		this.mapper = mapper;
		this.contentMapper = contentMapper;
		this.redisUtils = redisUtils;
	}

	@Override
	public ResponseData pageQuery(ArticleQueryDTO dto) {
		IPage<Article> iPage = new Page<>(dto.getPage(), dto.getLimit());
		QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("deleted", 0);
		queryWrapper.like(StringUtils.isNotBlank(dto.getClassName()), "class_name", dto.getClassName());
		queryWrapper.like(StringUtils.isNotBlank(dto.getTitle()), "title", dto.getTitle());
		iPage = mapper.selectPage(iPage, queryWrapper);
		return ResponseData.success(iPage);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseData insertArticle(Article article) {
		article.setId(DefaultIdentifierGenerator.getInstance().nextId(null).toString());
		article.setCreatedTime(LocalDateTime.now());
		article.setDeleted(false);
		mapper.insert(article);
		
		// 保存内容
		ArticleContent content = new ArticleContent();
		content.setArticleId(article.getId());
		content.setContent(article.getContent());
		contentMapper.insert(content);
		
		// 发送 消息给mq
		asyncService.syncArticleToCache("insert", article);
		return ResponseData.success(article.getId());
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseData updateArticle(Article article) {
		article.setUpdatedTime(LocalDateTime.now());
		mapper.updateById(article);
		
		// 保存内容
		ArticleContent content = new ArticleContent();
		content.setArticleId(article.getId());
		content.setContent(article.getContent());
		contentMapper.updateById(content);
		
		// 发送 消息给mq
		asyncService.syncArticleToCache("update", article);
		return ResponseData.success(article.getId());
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseData deleteArticle(Article article) {
		article.setDeleted(true);
		article.setDeletedTime(LocalDateTime.now().toString());
		mapper.updateById(article);
		
		// 发送 消息给mq
		asyncService.syncArticleToCache("delete", article);
		return ResponseData.success();
	}
	
	@Override
	public ResponseData getArticleById(String id) {
		String key = RedisKeyPrefix.ARTICLE.getPrefix() + id;
		if (redisUtils.hasKey(key)) {
			return ResponseData.success(redisUtils.get(key));
		} else {
			Article article = mapper.getArticleById(id);
			if (null == article) {
				// 将空一起存入缓存
				redisUtils.set(key, null, 60 * 60 * 24);
			} else {
				redisUtils.set(key, article);
			}
			return ResponseData.success(article);
		}
	}
}
