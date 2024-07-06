package com.liyangit;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h2>博客文章搜索服务启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.liyangit.mapper")
public class BlogSearchApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogSearchApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BlogSearchApplication.class, args);
		LOGGER.info("*************** Blog Article Start Success *************** ");
	}
}
