package com.liyangit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>RabbitMQ 发送消息配置</h2>
 * <p>
 *
 * </p>
 *
 * @author Evan <1922802352@qq.com>
 * @since 2023年07月04日 12:18
 */
@Configuration
public class RabbitMQConfig {
	private final static Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);
	
	/**
	 * 创建自定义 RabbitTemplate 操作类，如果不需要开启消息确认等配置，直接使用Spring默认的 RabbitTemplate也可以<br>
	 * 参考官方: <a href=https://docs.spring.io/spring-amqp/docs/current/reference/html/#amqp-template>RabbitTemplate</a>
	 *
	 * @param connectionFactory {@link ConnectionFactory}
	 * @return RabbitTemplate
	 */
	@Bean
	public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate();
		rabbitTemplate.setConnectionFactory(connectionFactory);
		
		// 设置开启 Mandatory，才能触发回调函数，无论消息推送结果怎么样 都强制调用回调函数
		rabbitTemplate.setMandatory(true);
		
		// 发送消息到交换机异常会进入这个回调
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			log.info("ConfirmCallback  是否已发送到交换机: {}", ack);
			log.info("ConfirmCallback  相关数据: {}", correlationData);
			log.info("ConfirmCallback  原因: {}", cause);
		});
		
		// 发送消息到交换机成功，但是分发到具体路由队列失败会进入这个回调
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			log.info("ReturnCallback 消息:  {}", message);
			log.info("ReturnCallback 回应码: {}", replyCode);
			log.info("ReturnCallback 回应信息: {}", replyText);
			log.info("ReturnCallback 交换机: {}", exchange);
			log.info("ReturnCallback 路由键:  {}", routingKey);
		});
		
		return rabbitTemplate;
	}
}
