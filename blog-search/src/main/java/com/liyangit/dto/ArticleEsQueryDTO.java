package com.liyangit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * <h2>文章数据查询es</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class ArticleEsQueryDTO {
	
	public String content;
	private String id;
	private String className;
	private String title;
	private String createdBy;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdTimeBegin;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdTimeEnd;
	
	// 关键字 - 同时多条件查询多个值，等同于数据库 OR 查询，(可同时查询 title、content)
	private String keyword;
	
	
	// 排序方式(ASC、DESC) 默认 DESC
	private String order;
	
	// 排序类型， 1 按创建时间排序，2 按修改时间排序
	private Integer oderType;
	
	private Integer page;
	
	private Integer limit;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public LocalDateTime getCreatedTimeBegin() {
		return createdTimeBegin;
	}
	
	public void setCreatedTimeBegin(LocalDateTime createdTimeBegin) {
		this.createdTimeBegin = createdTimeBegin;
	}
	
	public LocalDateTime getCreatedTimeEnd() {
		return createdTimeEnd;
	}
	
	public void setCreatedTimeEnd(LocalDateTime createdTimeEnd) {
		this.createdTimeEnd = createdTimeEnd;
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getLimit() {
		return limit;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
	
	public Integer getOderType() {
		return oderType;
	}
	
	public void setOderType(Integer oderType) {
		this.oderType = oderType;
	}
	
	
}
