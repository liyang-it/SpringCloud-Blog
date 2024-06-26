package com.liyangit.utils;

import java.util.Random;

/**
 * <h2>字符工具类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class CharUtil {
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int CHARS_LENGTH = CHARS.length();
	private static final Random random = new Random();
	
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(CHARS_LENGTH);
			sb.append(CHARS.charAt(index));
		}
		return sb.toString();
	}
}
