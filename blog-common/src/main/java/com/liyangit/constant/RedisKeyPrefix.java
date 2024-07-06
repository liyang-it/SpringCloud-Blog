package com.liyangit.constant;

/**
 * <h2>redis键前缀</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public enum RedisKeyPrefix {
	MAIN("spring_cloud_blog:"),
	ARTICLE("spring_cloud_blog:article:"),
	;
	
	
	private String prefix;
	
	RedisKeyPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
