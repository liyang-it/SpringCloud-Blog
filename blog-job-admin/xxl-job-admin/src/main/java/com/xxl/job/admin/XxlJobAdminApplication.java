package com.xxl.job.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication
public class XxlJobAdminApplication {
	private final static Logger LOGGER = LoggerFactory.getLogger(XxlJobAdminApplication.class);

	public static void main(String[] args) {
        SpringApplication.run(XxlJobAdminApplication.class, args);
		LOGGER.info("*************** 定时任务调度中心启动成功 *************** ");
	}

}
