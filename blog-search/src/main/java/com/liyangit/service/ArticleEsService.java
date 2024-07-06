package com.liyangit.service;

import com.liyangit.dto.ArticleEsQueryDTO;
import com.liyangit.entity.ArticleEsEntity;
import com.liyangit.result.ResponseData;

/**
 * <h2>博客文章es服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface ArticleEsService {
	
	//保存和修改
	void save(ArticleEsEntity article);
	
	//查询id
	ArticleEsEntity findById(String id);
	
	//删除指定ID数据
	void   deleteById(String id);
	
	// 查询总数
	long count();
	
	// 根据id查询数据是否存在
	boolean existsById(String id);
	
	ResponseData pageQuery(ArticleEsQueryDTO dto);

}
