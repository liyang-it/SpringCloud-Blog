package com.liyangit;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * <h2>后台管理启动类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.liyangit.mapper")
public class BlogAdminApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(BlogAdminApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(BlogAdminApplication.class, args);
		LOGGER.info("*************** Blog Admin Start Success *************** ");
	}
}
