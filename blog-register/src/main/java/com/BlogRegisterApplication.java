package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <h2>注册中心服务端启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@SpringBootApplication
@EnableEurekaServer
public class BlogRegisterApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogRegisterApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BlogRegisterApplication.class, args);
		LOGGER.info("*************** Blog Register Center Start Success *************** ");
	}
}
