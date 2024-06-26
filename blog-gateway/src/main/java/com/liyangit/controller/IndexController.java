package com.liyangit.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h2>主页控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RestController
public class IndexController {
	@RequestMapping(value = "/back")
	public String back() {
		return "Server Error!";
	}
}
