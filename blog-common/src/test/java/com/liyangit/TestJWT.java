package com.liyangit;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.liyangit.utils.TokenUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <h2>jwt测试</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@SpringBootTest
public class TestJWT {
	public String secret = "LiYang";
	
	@Test
	public void test() {
		
		// 生成 token
		
		
		JSONObject json = new JSONObject();
		json.put("userId", "lasdsad");
		json.put("username", "liyang");
		
		Calendar instance = Calendar.getInstance();
		
		// 20秒后令牌token失效
		instance.add(Calendar.DATE, 7);
		
		String token = JWT.create().withPayload(json).withClaim("userId", json.getString("userId")).withClaim("username", json.getString("username")).withClaim("json", json).withExpiresAt(instance.getTime()) // 指定令牌的过期时间
				.sign(Algorithm.HMAC256(secret));//签名
		
		System.out.printf("生成的token: \r\n%s\n", token);
		
		
		// 校验 token
		JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
		
		// 异常:com.auth0.jwt.exceptions.SignatureVerificationException 表示token无效
		// 异常: com.auth0.jwt.exceptions.TokenExpiredException 表示token已过期
		DecodedJWT verify = jwtVerifier.verify(token);
		System.out.printf("解密token获取到的 userId: %s \n", verify.getClaim("userId"));
		System.out.printf("解密token获取到的 username: %s \n", verify.getClaim("username"));
		System.out.printf("解密token获取到的 json: %s \n", JSONObject.parseObject(verify.getClaim("json").toString()).getString("username"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("令牌过期时间：" + format.format(verify.getExpiresAt()));
		
	}
	
	@Test
	public void test2() {
		String token = TokenUtil.generateToken("liyang");
		System.out.println(token);
		System.out.println(TokenUtil.decodedToken(token).getClaim("username"));
		
	}
	
}
