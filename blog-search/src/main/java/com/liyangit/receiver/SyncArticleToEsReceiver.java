package com.liyangit.receiver;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.liyangit.entity.ArticleEsEntity;
import com.liyangit.entity.QueueConsumptionFailureEntity;
import com.liyangit.mapper.ArticleEsMapper;
import com.liyangit.mapper.QueueConsumptionFailureMapper;
import com.liyangit.service.impl.ArticleEsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * <h2>监听 同步es 消息</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@Component
public class SyncArticleToEsReceiver {
	
	private final static Logger log = LoggerFactory.getLogger(SyncArticleToEsReceiver.class);
	
	// 正常队列
	private final String SYNC_ARTICLE__TO_ES_ROUTER_KEY = "spring_blog_sync_article_to_es";
	
	// 死信队列
	private final String SYNC_ARTICLE__TO_ES_ROUTER_KEY_DEAD = "spring_blog_sync_article_to_es_dead";
	
	@Autowired
	private ArticleEsServiceImpl service;
	
	@Autowired
	private ArticleEsMapper articleMapper;
	
	@Autowired
	private QueueConsumptionFailureMapper consumptionFailureMapper;
	
	/**
	 * 同步es消息监听
	 */
	@RabbitHandler
	@RabbitListener(queues = SYNC_ARTICLE__TO_ES_ROUTER_KEY)
	public void listener(Message message) {
		String id = null;
		try {
			// 消息数据
			JSONObject json = JSON.parseObject(message.getBody());
			
			log.info("********** 消息数据：[{}],   开始同步ES ********** ", json);
			
			id = json.getString("messageId");
			
			String controls = json.getString("controls");
			
			log.info("********** 消息数据ID：[{}],   开始同步ES ********** ", id);
			
			if ("insert".equalsIgnoreCase(controls) || "update".equalsIgnoreCase(controls)) {
				ArticleEsEntity article = articleMapper.getArticleById(id);
				Assert.isTrue(article != null, "根据ID查询数据不存在");
				// 保存es
				service.save(article);
			} else {
				// 删除es数据
				
				service.deleteById(id);
			}
			log.info("********** 消息数据ID：[{}],   同步ES 成功 ********** ", id);
		} catch (Exception e) {
			log.error("********** 消息数据ID：[{}],   同步ES失败, 原因：[{}] ********** ", id, e.getMessage());
			// 重新抛出异常，触发重试(即使不抛出也会触发重试，抛异常是为了记录异常日志)
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	/**
	 * 同步es消息消费失败，重试多次依然失败，进入到死信队列处理
	 */
	@RabbitHandler
	@RabbitListener(queues = SYNC_ARTICLE__TO_ES_ROUTER_KEY_DEAD)
	public void listener2(Message message) {
		String id = null;
		try {
			// 消息数据
			JSONObject json = JSON.parseObject(message.getBody());
			
			log.info("********** 消息数据：[{}],   同步ES 重试多次依旧失败,将失败记录保存进数据库 ********** ", json);
			
			id = json.getString("messageId");
			
			QueueConsumptionFailureEntity entity = new QueueConsumptionFailureEntity();
			entity.setMessageId(id);
			entity.setMessage(json.toString());
			entity.setQueueName(message.getMessageProperties().getHeader("x-first-death-queue"));
			entity.setStatus(false);
			entity.setCreatedTime(LocalDateTime.now());
			consumptionFailureMapper.insert(entity);
			log.info("********** 消息数据ID：[{}],   同步ES 重试多次依旧失败,将失败记录保存进数据库 ********** ", id);
		} catch (Exception e) {
			log.error("********** 消息数据ID：[{}],   将同步ES消费失败记录保存进数据库失败，原因：[{}] ********** ", id, e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}
}
