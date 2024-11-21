package com.liyangit.dto;

/**
 * <h2>通用查询参数</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

public class BaseQueryDTO {
	private Integer page;
	private Integer limit;
	
	public Integer getPage() {
		if(page == null){
			return 1;
		}
		return page;
	}
	
	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getLimit() {
		if(limit == null){
			return 10;
		}
		return limit;
	}
	
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
