package com.liyangit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h2>配置中心服务端启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class BlogConfigApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogConfigApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BlogConfigApplication.class, args);
		LOGGER.info("*************** Blog Config Start Success *************** ");
	}
}
