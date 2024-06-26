package com.liyangit.controller;

import com.liyangit.dto.AdminQueryDTO;
import com.liyangit.entity.Admin;
import com.liyangit.result.ResponseData;
import com.liyangit.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <h2>管理员控制层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@GetMapping(value = "/pageQuery")
	public ResponseData pageQuery(AdminQueryDTO dto) {
		return adminService.pageQuery(dto);
	}
	
	@PostMapping(value = "/insertAdmin")
	public ResponseData insertAdmin(@RequestHeader String username, @RequestBody Admin admin) {
		admin.setCreatedBy(username);
		return adminService.insertAdmin(admin);
	}
	
	@PostMapping(value = "/updatedAdmin")
	public ResponseData updatedAdmin(@RequestHeader String username, @RequestBody Admin admin) {
		admin.setUpdatedBy(username);
		return adminService.updatedAdmin(admin);
	}
	
	@PostMapping(value = "/deletedAdmin")
	public ResponseData deletedAdmin(@RequestHeader String username, @RequestBody Admin admin) {
		admin.setUpdatedBy(username);
		return adminService.deletedAdmin(admin);
	}
	
}
