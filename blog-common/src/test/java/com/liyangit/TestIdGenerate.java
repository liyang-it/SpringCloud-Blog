package com.liyangit;

import com.liyangit.incrementer.DefaultIdentifierGenerator;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <h2></h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class TestIdGenerate {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
		for (int i = 0, size = 100; i < size; i++) {
			executor.execute(() -> System.out.println("id生成：" + DefaultIdentifierGenerator.getInstance().nextId(null).toString()));
		}
		System.out.println("主线程执行结束");
	}
}
