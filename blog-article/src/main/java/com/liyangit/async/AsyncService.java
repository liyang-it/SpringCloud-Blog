package com.liyangit.async;

import com.alibaba.fastjson2.JSONObject;
import com.liyangit.constant.RabbitMQConstant;
import com.liyangit.constant.RedisKeyPrefix;
import com.liyangit.entity.Article;
import com.liyangit.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <h2>异步任务服务类</h2>
 * <p>
 * 当前类中的方法不允许互相调用，否则异步将失效变为同步执行
 * </p>
 *
 * @author LiYang
 */
@Service
public class AsyncService {
	
	private final static Logger log = LoggerFactory.getLogger(AsyncService.class);
	private final RabbitTemplate template;
	private final RedisUtils redisUtils;
	
	public AsyncService(RabbitTemplate template, RedisUtils redisUtils) {
		this.template = template;
		this.redisUtils = redisUtils;
	}
	
	public void syncArticleToCache(String controls, Article article) {
		
		if ("update".equalsIgnoreCase(controls) || "delete".equalsIgnoreCase(controls)) {
			// 修改文章和删除文章 都删除缓存
			redisUtils.del(RedisKeyPrefix.ARTICLE.getPrefix() + article.getId());
		}
		
		
		log.info("同步博客文章数据到ElasticSearch");
		// 这里要注意，可能会出现 数据库事务还没有提交，但是消息已经发送到达消息队列，此时消息队列查询数据是查不到的，因为当前方法是异步并不在事务当中
		// 需要自己做补偿机制
		// 方案1 重试，每间隔一秒重试一次
		// 方案2  使用延迟任务
		// 方案3 将整个文章数据对象传递过去(如果文章数据量过大传递非常消费带宽和性能)
		// 方案4 使用redis，数据库保存完之后往redis中存储，消息队列中取redis，不存在则重试
		// 这边采用的方法为：消费端设置消费重试，超过最大重试次数之后 将消息加入到死信队列，在死信队列中将消费失败记录保存在数据库当中
		JSONObject rabbitMQData = new JSONObject();
		rabbitMQData.put("controls", controls);
		rabbitMQData.put("messageId", article.getId());
		rabbitMQData.put("createdTime", LocalDateTime.now());
		
		template.convertAndSend(RabbitMQConstant.SYNC_ARTICLE__TO_ES_EX_CHANGE.getValue(), RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY.getValue(), rabbitMQData.toString());
	}
}
