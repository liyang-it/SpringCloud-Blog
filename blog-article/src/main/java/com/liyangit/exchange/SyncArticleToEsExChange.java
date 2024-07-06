package com.liyangit.exchange;

import com.liyangit.constant.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>用于同步博客数据到ElasticSearch 任务消息队列</h2>
 * <p>
 *  这里要注意，这个配置类系统启动 加载bean会自动创建对应的队列和交换机(不会覆盖创建),如果修改了队列交换机信息，在启动前要把对应的队列和交换机删除，等待启动后重新创建</br>
 *  其他地方如果监听了队列消息消费，当前服务和监听服务如果同时启动 可能会出现 队列和交换机信息不一致问题，最好的办法是：如果更改了队列交换机信息，启动当前服务前 删除对应的队列交换机，在启动，等待当前服务启动完成 发送一条测试消息 没问题后  在启动监听消费服务
 *
 * </p>
 *
 * @author LiYang
 */
@Configuration
@SuppressWarnings("all")
public class SyncArticleToEsExChange {
	
	
	/**
	 * 创建一个 同步es数据任务队列
	 *
	 * @return 同步es数据任务队列
	 */
	@Bean
	public Queue syncArticleToEsQueue() {
		return QueueBuilder.durable(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY.getValue())
				// 该队列消息消费重试失败 流入到指定死信交换机
				.deadLetterExchange(RabbitMQConstant.SYNC_ARTICLE__TO_ES_EX_CHANGE_DEAD.getValue())
				// 指定死信路由
				.deadLetterRoutingKey(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY_DEAD.getValue())
				.build();
	}
	
	/**
	 * 创建一个主题性正常交换机, 用于绑定 同步数据队列
	 *
	 * @return {@link TopicExchange}
	 */
	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(RabbitMQConstant.SYNC_ARTICLE__TO_ES_EX_CHANGE.getValue());
	}
	
	
	// 死信队列, 同步数据队列重试多次依旧异常进入这个队列
	@Bean
	public Queue deadLetterQueue() {
		return QueueBuilder.durable(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY_DEAD.getValue()).build();
	}
	
	// 死信交换机
	@Bean
	public TopicExchange deadLetterExchange() {
		return new TopicExchange(RabbitMQConstant.SYNC_ARTICLE__TO_ES_EX_CHANGE_DEAD.getValue());
	}
	
	
	/**
	 * 将 正常的队列 与正常交换机绑定，并且绑定的键为 {@link RabbitMQConstant#SYNC_ARTICLE__TO_ES_ROUTER_KEY}, 这样路由键匹配就会分发到该队列
	 *
	 * @return {@link Binding}
	 */
	@Bean
	public Binding bindingExchangeSyncArticleToEsQueue() {
		return BindingBuilder.bind(syncArticleToEsQueue()).to(exchange()).with(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY.getValue());
	}
	
	
	// 死信交换机绑定死信队列
	@Bean
	public Binding bindingDeadLetterExchange() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(RabbitMQConstant.SYNC_ARTICLE__TO_ES_ROUTER_KEY_DEAD.getValue());
	}
	
}
