package com.liyangit;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * <h2>uuid测试</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@SpringBootTest
public class TestUUID {
	@Test
	public void test(){
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
