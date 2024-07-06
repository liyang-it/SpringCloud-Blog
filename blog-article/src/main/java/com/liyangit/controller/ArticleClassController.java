package com.liyangit.controller;

import com.liyangit.dto.ArticleClassQueryDTO;
import com.liyangit.entity.ArticleClass;
import com.liyangit.result.ResponseData;
import com.liyangit.service.ArticleClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h2>博客文章分类控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RestController
@RequestMapping(value = "/articleClass")
public class ArticleClassController {
	@Autowired
	private ArticleClassService service;
	
	
	@GetMapping(value = "/listQuery")
	public ResponseData listQuery(ArticleClassQueryDTO dto) {
		return service.listQuery(dto);
	}
	
	@PostMapping(value = "/insertArticleClass")
	public ResponseData insertArticleClass(@RequestHeader String username, @RequestBody @Valid ArticleClass admin) {
		admin.setCreatedBy(username);
		return service.insertArticleClass(admin);
	}
	
	@PostMapping(value = "/updateArticleClass")
	public ResponseData updateArticleClass(@RequestHeader String username, @RequestBody @Valid ArticleClass admin) {
		admin.setUpdatedBy(username);
		return service.updateArticleClass(admin);
	}
	
	@PostMapping(value = "/deleteArticleClass")
	public ResponseData deleteArticleClass(@RequestHeader String username, @RequestBody ArticleClass admin) {
		admin.setUpdatedBy(username);
		return service.deleteArticleClass(admin);
	}
	
}
