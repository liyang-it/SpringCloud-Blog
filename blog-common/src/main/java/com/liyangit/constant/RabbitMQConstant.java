package com.liyangit.constant;

/**
 * <h2>RabbitMQ路由键常量值</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public enum RabbitMQConstant {
	SYNC_ARTICLE__TO_ES_ROUTER_KEY("spring_blog_sync_article_to_es"),
	SYNC_ARTICLE__TO_ES_EX_CHANGE("spring_cloud_blog_sync_article_to_es_exchange"),
	SYNC_ARTICLE__TO_ES_ROUTER_KEY_DEAD("spring_blog_sync_article_to_es_dead"),
	SYNC_ARTICLE__TO_ES_EX_CHANGE_DEAD("spring_cloud_blog_sync_article_to_es_exchange_dead"),
	
	
	;
	private String value;
	RabbitMQConstant(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
