package com.liyangit.utils;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;

/**
 * <h2>token工具类</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public class TokenUtil {
	private static final String secret = "LiYang";
	
	/**
	 * 生成用户token信息
	 *
	 * @param username 用户名
	 * @return
	 */
	public static String generateToken(String username) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", username);
		Calendar instance = Calendar.getInstance();
		
		// 7天后令牌token失效
		instance.add(Calendar.DATE, 20);
	
		return JWT.create()
				// json信息
				.withClaim("json", jsonObject)
				// 指定令牌的过期时间
				.withExpiresAt(instance.getTime())
				.sign(Algorithm.HMAC256(secret));
	}
	
	/**
	 * token解密
	 *
	 * @param token
	 * @throws com.auth0.jwt.exceptions.SignatureVerificationException 表示token无效
	 * @throws com.auth0.jwt.exceptions.TokenExpiredException          表示token已过期
	 */
	public static DecodedJWT decodedToken(String token) {
		// 校验 token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
		
		// 异常:com.auth0.jwt.exceptions.SignatureVerificationException 表示token无效
		// 异常: com.auth0.jwt.exceptions.TokenExpiredException 表示token已过期
		return jwtVerifier.verify(token);
		
	}
	
}
