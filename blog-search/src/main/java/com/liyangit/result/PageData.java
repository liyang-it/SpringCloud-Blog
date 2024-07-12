package com.liyangit.result;

import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

import java.util.List;

/**
 * <h2>ElasticSearch分页返回对象数据</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class PageData<T> {
	private Integer page;
	private Integer size;
	private Long total;
	private Integer totalPages;
	private List<T> data;
	
	public PageData(AggregatedPage<T> aggregatedPage) {
		this.page = aggregatedPage.getNumber() + 1;
		this.size = aggregatedPage.getSize();
		this.total = aggregatedPage.getTotalElements();
		this.totalPages = aggregatedPage.getTotalPages();
		this.data = aggregatedPage.getContent();
	}
	
	public Integer getPage() {
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
	
	public Integer getTotalPages() {
		return totalPages;
	}
	
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	
}
