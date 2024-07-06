package com.liyangit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * <h2>异步任务配置</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	private final static Logger log = LoggerFactory.getLogger(AsyncConfig.class);
	
	@Bean
	public Executor asyncExecutor() {
		log.info("********** 开始初始化异步任务线程池 **********");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 核心线程数
		executor.setCorePoolSize(30);
		// 最大线程数
		executor.setMaxPoolSize(30);
		
		executor.setQueueCapacity(200);
		// 线程名称前缀
		executor.setThreadNamePrefix("BlogAsync-");
		executor.initialize();
		log.info("********** 初始化异步任务线程池完成 **********");
		return executor;
	}
}
