package com.liyangit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h2>网关启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@SpringBootApplication
@EnableEurekaClient
public class BlogGatewayApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogGatewayApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BlogGatewayApplication.class, args);
		LOGGER.info("*************** Blog Gateway Start Success *************** ");
	}
}
