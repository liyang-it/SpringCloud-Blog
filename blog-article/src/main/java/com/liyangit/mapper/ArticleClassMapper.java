package com.liyangit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyangit.entity.ArticleClass;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <h2>博客文章分类Mapper</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface ArticleClassMapper extends BaseMapper<ArticleClass> {
	
	@Select(" SELECT id, class_name, created_by, created_time, updated_by, updated_time FROM `t_article_class` where deleted = 0 ")
	List<ArticleClass> listQuery(@Param("className") String className);
}
