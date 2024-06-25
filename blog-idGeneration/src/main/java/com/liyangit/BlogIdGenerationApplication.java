package com.liyangit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h2>分布式唯一ID服务端启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@SpringBootApplication
@EnableEurekaClient
public class BlogIdGenerationApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogIdGenerationApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BlogIdGenerationApplication.class, args);
		LOGGER.info("*************** Blog IdGeneration Start Success *************** ");
	}
}
