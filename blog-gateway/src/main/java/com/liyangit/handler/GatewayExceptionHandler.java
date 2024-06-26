package com.liyangit.handler;

import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * <h2>网关统一全局异常处理</h2>
 * <p>
 * 如果在配置文件中配置了 fallbackUri: forward:/back 熔断失败页面转发那么这么全局处理器会失效，二选一
 * </p>
 *
 * @author LiYang
 */
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GatewayExceptionHandler.class);
	
	/**
	 * 设置webflux模型响应
	 *
	 * @param response ServerHttpResponse
	 * @param code     状态码
	 * @param msg      消息
	 * @return Mono<Void>
	 */
	public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, int code, String msg) {
		response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		// 通用的响应实体类，使用自己定义的通用返回类就行
		DataBuffer dataBuffer = response.bufferFactory().wrap(ResponseData.normal(code, msg).toString().getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Mono.just(dataBuffer));
	}
	
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		ServerHttpResponse response = exchange.getResponse();
		
		// 首先检查响应是否已经提交（即是否已经发送给客户端）。如果已提交，则直接返回包含异常的Mono。
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		
		log.error("************ 网关异常处理，请求路径:{},异常信息:{} ************ ", exchange.getRequest().getPath(), ex.getMessage());
		
		if (ex instanceof NotFoundException) {
			return webFluxResponseWriter(response, ResultCode.ERROR_SERVICE_NOT_FOUND.getCode(), ResultCode.ERROR_SERVICE_NOT_FOUND.getMsg());
		} else if (ex instanceof ResponseStatusException) {
			ResponseStatusException responseStatusException = (ResponseStatusException) ex;
			return webFluxResponseWriter(response, ResultCode.ERROR_SERVICE.getCode(), responseStatusException.getMessage());
		} else {
			return webFluxResponseWriter(response, ResultCode.ERROR_SERVICE_RESPONSE_STATUS.getCode(), ResultCode.ERROR_SERVICE_RESPONSE_STATUS.getMsg());
		}
	}
}

