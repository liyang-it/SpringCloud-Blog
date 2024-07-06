package com.liyangit.controller;

import com.liyangit.dto.ArticleEsQueryDTO;
import com.liyangit.result.ResponseData;
import com.liyangit.service.impl.ArticleEsServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>博客文章搜索控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RestController
@RequestMapping(value = "/search")
public class SearchController {
	
	private final ArticleEsServiceImpl service;
	
	public SearchController(ArticleEsServiceImpl service) {
		this.service = service;
	}
	
	
	@GetMapping(value = "/pageQuery")
	public ResponseData pageQuery(ArticleEsQueryDTO dto) {
		return service.pageQuery(dto);
	}
}
