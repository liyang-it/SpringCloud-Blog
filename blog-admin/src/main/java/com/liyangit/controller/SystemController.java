package com.liyangit.controller;

import com.liyangit.entity.System;
import com.liyangit.result.ResponseData;
import com.liyangit.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h2>系统配置控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RequestMapping(value = "/system")
@RestController
public class SystemController {
	@Autowired
	private SystemService systemService;
	
	@GetMapping(value = "/listQuery")
	public ResponseData listQuery(String code) {
		return systemService.listQuery(code);
	}
	
	@PostMapping(value = "/update")
	public ResponseData update(@RequestBody System system) {
		return systemService.update(system);
	}
	
}
