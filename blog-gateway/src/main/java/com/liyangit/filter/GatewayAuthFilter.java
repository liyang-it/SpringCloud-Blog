package com.liyangit.filter;

import com.alibaba.fastjson2.JSONObject;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import com.liyangit.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * <h2>网关全局过滤器 - 用于鉴权</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Order(1)
@Configuration
public class GatewayAuthFilter implements GlobalFilter, Ordered {
	private final static Logger log = LoggerFactory.getLogger(GatewayAuthFilter.class);
	private final ArrayList<String> skipAuthUrls = new ArrayList<>();
	
	public GatewayAuthFilter() {
		// 不需要鉴权的 url集合
		skipAuthUrls.add("/admin/page/login");
	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		ServerHttpRequest request = exchange.getRequest();
		
		String url = request.getURI().getPath();
		
		log.info("******** 网关开始鉴权，请求路径：[{}] ********", url);
		
		// 跳过不需要验证的路径
		if (skipAuthUrls.contains(url)) {
			log.info("******** 网关鉴权结束，请求路径：[{}] 不需要鉴权 ********", url);
			return chain.filter(exchange);
		}
		
		String token = request.getHeaders().getFirst("auth");
		
		try {
			DecodedJWT decodedJWT = TokenUtil.decodedToken(token);
			if (null == decodedJWT) {
				return unauthorizedResponse(exchange, ResultCode.ERROR_TOKEN_VERIFICATION.getCode(), ResultCode.ERROR_TOKEN_VERIFICATION.getMsg());
			}
			JSONObject json = JSONObject.parse(decodedJWT.getClaim("json").toString());
			String username = json.getString("username");
			
			log.info("******** 网关鉴权通过，请求路径：[{}] 用户名：[{}] ********", url, username);
			
			// 鉴权通过后，将用户名信息设置到 request当中，用于后面的服务通过 @RequestHeader 注解获取用户名信息
			ServerHttpRequest.Builder requestBuilder = request.mutate();
			requestBuilder.headers(k -> k.set("username", username));
			exchange.mutate().request(request).build();
			
		} catch (TokenExpiredException tokenExpiredException) {
			return unauthorizedResponse(exchange, ResultCode.ERROR_TOKEN_EXPIRED.getCode(), ResultCode.ERROR_TOKEN_EXPIRED.getMsg());
		} catch (Exception e) {
			return unauthorizedResponse(exchange, ResultCode.ERROR_TOKEN_VERIFICATION.getCode(), ResultCode.ERROR_TOKEN_VERIFICATION.getMsg());
		}
		log.info("******** 网关鉴权结束，请求路径：[{}] 鉴权通过 ********", url);
		return chain.filter(exchange);
	}
	
	private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, int code, String msg) {
		log.error("[鉴权异常处理]请求路径:{}, 原因：{}", exchange.getRequest().getPath(), msg);
		exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		
		// 通用的响应实体类，使用自己定义的通用返回类就行
		DataBuffer dataBuffer = exchange.getResponse().bufferFactory().wrap(ResponseData.normal(code, msg).toString().getBytes(StandardCharsets.UTF_8));
		return exchange.getResponse().writeWith(Mono.just(dataBuffer));
	}
	
	
	@Override
	public int getOrder() {
		return 0;
	}
}
