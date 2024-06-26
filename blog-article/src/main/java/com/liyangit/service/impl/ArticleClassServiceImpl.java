package com.liyangit.service.impl;

import com.liyangit.dto.ArticleClassQueryDTO;
import com.liyangit.entity.ArticleClass;
import com.liyangit.mapper.ArticleClassMapper;
import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import com.liyangit.service.ArticleClassService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
	
	public ArticleClassServiceImpl(ArticleClassMapper articleClassMapper) {
		this.articleClassMapper = articleClassMapper;
	}
	
	@Override
	public ResponseData insertArticleClass(ArticleClass articleClass) {
		articleClass.setCreatedTime(LocalDateTime.now());
		articleClass.setDeleted(false);
		articleClass.setDeletedTime("-");
		try {
			articleClassMapper.insert(articleClass);
		}catch (DuplicateKeyException e){
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), "此分类已存在");
		}
		
		return ResponseData.success();
	}
	
	@Override
	public ResponseData updateArticleClass(ArticleClass articleClass) {
		if(null == articleClass.getId()){
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), ResultCode.ERROR_UPDATE.getMsg());
		}
		
		try {
			articleClass.setUpdatedTime(LocalDateTime.now());
			articleClassMapper.updateById(articleClass);
		}catch (DuplicateKeyException e){
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), "此分类已存在");
		}
		return ResponseData.success();
	}
	
	@Override
	public ResponseData deleteArticleClass(ArticleClass articleClass) {
		articleClass.setDeletedTime(LocalDateTime.now().toString());
		articleClass.setDeleted(true);
		return updateArticleClass(articleClass);
	}
	
	@Override
	public ResponseData pageQuery(ArticleClassQueryDTO dto) {
		return null;
	}
}
