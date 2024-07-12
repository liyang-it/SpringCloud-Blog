package com.liyangit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * <h2>消息队列消费重试配置</h2>
 * <p>
 * 重试逻辑：<br>
 * 1、实现消费者拦截器的容器工厂，增加 重试类 来设置重试参数<br>
 * 2、消息在被消费者获取后，重试最大次数后，将会过期，设置死信队列，将重试处理不了的消息 了 路由到了死信队列中 。死信队列的消息需要人工干预处理<br>
 * 3、需要手动创建 死信交换机，死信交换机同时绑定正常队列、死信队列
 * </p>
 *
 * @author LiYang
 */
@Configuration
public class RabbitRetryConfig {
	private final static Logger log = LoggerFactory.getLogger(RabbitRetryConfig.class);
	
	private final CachingConnectionFactory connectionFactory;
	private final RabbitProperties properties;
	
	public RabbitRetryConfig(CachingConnectionFactory connectionFactory, RabbitProperties properties) {
		this.connectionFactory = connectionFactory;
		this.properties = properties;
	}
	
	/**
	 * RabbitMQ消息监听配置
	 * @return {@link SimpleRabbitListenerContainerFactory}
	 */
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
		SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
		containerFactory.setConnectionFactory(connectionFactory);
		
		// 创建的并发消费者的数量。默认值为 1设置并发消费者的数量。
		// 并发消费者是同时处理消息的消费者的数量。默认情况下，SimpleMessageListenerContainer只创建一个消费者。
		// 通过调用setConcurrentConsumers方法，你可以增加并发消费者的数量，以提高消息处理的并发性。
		// 例如，如果你将setConcurrentConsumers(5)调用应用于SimpleMessageListenerContainer实例，那么它将创建5个并发消费者来处理消息
		// 请注意，增加并发消费者的数量可能会增加系统的负载和资源消耗。你需要根据你的应用程序的需求和系统资源来决定合适的并发消费者数量
		// 如果你同时发送的消息数量少于或等于 concurrentConsumers 条，那么只有 concurrentConsumers 个消费者会处理消息，而其他的消费者将处于空闲状态。可以通过增加并发消费者的数量，以提高消息处理的并发性
		containerFactory.setConcurrentConsumers(1);
		
		// 设置消费者数量上限；默认为“并发消费者”。消费者将按需添加。不能小于concurrentConsumers
		containerFactory.setMaxConcurrentConsumers(5);
		
		// 自动应答
		containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
		
		containerFactory.setChannelTransacted(true);
		
		containerFactory.setAdviceChain(RetryInterceptorBuilder.stateless().recoverer(new RejectAndDontRequeueRecoverer()).retryOperations(rabbitRetryTemplate()).build());
		return containerFactory;
	}
	
	@Bean
	public RetryTemplate rabbitRetryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		
		// 设置监听（不是必须）
		retryTemplate.registerListener(new RetryListener() {
			@Override
			public <T, E extends Throwable> boolean open(RetryContext retryContext, RetryCallback<T, E> retryCallback) {
				// 执行之前调用 （返回false时会终止执行）
				return true;
			}
			
			@Override
			public <T, E extends Throwable> void close(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
				// 重试结束的时候调用 （最后一次重试 ）
			}
			
			@Override
			public <T, E extends Throwable> void onError(RetryContext retryContext, RetryCallback<T, E> retryCallback, Throwable throwable) {
				//  异常 都会调用
				log.error("-----第{}次 消费重试", retryContext.getRetryCount());
			}
		});
		
		// 个性化处理异常和重试 （不是必须）
        /* Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        //设置重试异常和是否重试
        retryableExceptions.put(AmqpException.class, true);
        //设置重试次数和要重试的异常
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(5,retryableExceptions);*/
		
		retryTemplate.setBackOffPolicy(backOffPolicyByProperties());
		retryTemplate.setRetryPolicy(retryPolicyByProperties());
		return retryTemplate;
	}
	
	// 重试回退
	@Bean
	public ExponentialBackOffPolicy backOffPolicyByProperties() {
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		long maxInterval = properties.getListener().getSimple().getRetry().getMaxInterval().toMillis();
		long initialInterval = properties.getListener().getSimple().getRetry().getInitialInterval().toMillis();
		double multiplier = properties.getListener().getSimple().getRetry().getMultiplier();
		// 重试间隔, 设置为两秒
		backOffPolicy.setInitialInterval(initialInterval * 2);
		
		// 重试最大间隔，设置为 5秒
		backOffPolicy.setMaxInterval(maxInterval * 5);
		
		// 重试间隔乘法策略
		backOffPolicy.setMultiplier(multiplier);
		
		return backOffPolicy;
	}
	
	// 重试策略
	@Bean
	public SimpleRetryPolicy retryPolicyByProperties() {
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		int maxAttempts = properties.getListener().getSimple().getRetry().getMaxAttempts();
		// 设置最大重试次数， 使用默认值
		retryPolicy.setMaxAttempts(maxAttempts);
		return retryPolicy;
	}
	
}
