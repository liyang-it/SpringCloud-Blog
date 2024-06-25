package com.liyangit.utils;

import java.util.UUID;

/**
 * <h2>uuid工具类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class UUIDUtil {
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
