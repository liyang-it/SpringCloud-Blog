package com.liyangit.controller;

import com.liyangit.entity.Admin;
import com.liyangit.result.ResponseData;
import com.liyangit.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h2>页面控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RestController
@RequestMapping(value = "/page")
public class PageController {
	
	@Autowired
	private AdminService service;
	
	@PostMapping(value = "/login")
	public ResponseData login(@RequestBody Admin admin){
		return service.login(admin);
	}
	
	@GetMapping(value = "/loginStatus")
	public ResponseData info(@RequestHeader String username){
		return ResponseData.success(String.format("当前登录的用户为：%s", username));
	}
	
}
