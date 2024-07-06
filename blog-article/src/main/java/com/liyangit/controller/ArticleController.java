package com.liyangit.controller;

import com.liyangit.entity.Article;
import com.liyangit.result.ResponseData;
import com.liyangit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <h2>博客文章控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RestController
@RequestMapping(value = "/article")
public class ArticleController {
	@Autowired
	private ArticleService service;
	
	@GetMapping(value = "/getArticleById")
	public ResponseData getArticleById(@RequestParam String id) {
		return service.getArticleById(id);
	}
	
	@PostMapping(value = "/insertArticle")
	public ResponseData insertArticle(@RequestHeader String username, @RequestBody @Valid Article article) {
		article.setCreatedBy(username);
		return service.insertArticle(article);
	}
	
	@PostMapping(value = "/updateArticle")
	public ResponseData updateArticle(@RequestHeader String username, @RequestBody @Valid Article article) {
		article.setUpdatedBy(username);
		return service.updateArticle(article);
	}
	
	@PostMapping(value = "/deleteArticle")
	public ResponseData deleteArticle(@RequestHeader String username, @RequestBody Article article) {
		article.setUpdatedBy(username);
		return service.deleteArticle(article);
	}
	
}
