package com.liyangit.exception;

import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 接口的异常切面类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseData handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
		log.error("=====handleMethodArgumentNotValidException:{}", message);
		return ResponseData.normal(ResultCode.ERROR_SERVICE.getCode(), message);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseData handleException(Exception e) {
		log.error("=====handleException:{}", e.getMessage());
		return ResponseData.normal(ResultCode.ERROR_SERVICE.getCode(), e.getMessage());
	}
}
