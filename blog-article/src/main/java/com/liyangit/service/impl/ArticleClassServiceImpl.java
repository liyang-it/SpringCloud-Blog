package com.liyangit.service.impl;

import com.liyangit.constant.RedisKeyPrefix;
import com.liyangit.dto.ArticleClassQueryDTO;
import com.liyangit.entity.ArticleClass;
import com.liyangit.incrementer.DefaultIdentifierGenerator;
import com.liyangit.mapper.ArticleClassMapper;
import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import com.liyangit.service.ArticleClassService;
import com.liyangit.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <h2>博客文章分类服务实现层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Service
public class ArticleClassServiceImpl implements ArticleClassService {
	
	private final ArticleClassMapper articleClassMapper;
	private final RedisUtils redisUtils;
	private final String redisKeyPrefix = RedisKeyPrefix.MAIN.getPrefix() + "class_name_list";
	
	
	public ArticleClassServiceImpl(ArticleClassMapper articleClassMapper, RedisUtils redisUtils) {
		this.articleClassMapper = articleClassMapper;
		this.redisUtils = redisUtils;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseData insertArticleClass(ArticleClass articleClass) {
		
		articleClass.setId(DefaultIdentifierGenerator.getInstance().nextId(null).toString());
		articleClass.setCreatedTime(LocalDateTime.now());
		articleClass.setDeleted(false);
		articleClass.setDeletedTime("-");
		try {
			articleClassMapper.insert(articleClass);
			redisUtils.del(redisKeyPrefix);
		} catch (DuplicateKeyException e) {
			return ResponseData.normal(ResultCode.ERROR_INSERT.getCode(), "此分类已存在");
		}
		
		return ResponseData.success();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseData updateArticleClass(ArticleClass articleClass) {
		if (StringUtils.isBlank(articleClass.getId())) {
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), ResultCode.ERROR_UPDATE.getMsg());
		}
		
		try {
			articleClass.setUpdatedTime(LocalDateTime.now());
			articleClassMapper.updateById(articleClass);
			redisUtils.del(redisKeyPrefix);
		} catch (DuplicateKeyException e) {
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), "此分类已存在");
		}
		return ResponseData.success();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseData deleteArticleClass(ArticleClass articleClass) {
		articleClass.setDeletedTime(LocalDateTime.now().toString());
		articleClass.setDeleted(true);
		return updateArticleClass(articleClass);
	}
	
	@Override
	public ResponseData pageQuery(ArticleClassQueryDTO dto) {
		return null;
	}
	
	@Override
	public ResponseData listQuery(ArticleClassQueryDTO dto) {
		if (StringUtils.isNotBlank(dto.getClassName())) {
			// 有查询条件 不走缓存
			return ResponseData.success(listQuery(dto.getClassName()));
		} else {
			Object o;
			if (redisUtils.hasKey(redisKeyPrefix)) {
				o = redisUtils.lGet(redisKeyPrefix, 0, -1);
			} else {
				o = listQuery(dto.getClassName());
				redisUtils.lSet(redisKeyPrefix, o);
			}
			return ResponseData.success(o);
		}
	}
	
	private List<ArticleClass> listQuery(String className) {
		return articleClassMapper.listQuery(className);
	}
}
