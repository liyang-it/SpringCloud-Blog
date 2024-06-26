package com.liyangit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liyangit.dto.AdminQueryDTO;
import com.liyangit.entity.Admin;
import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;

/**
 * <h2>管理员服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface AdminService{
	ResponseData insertAdmin(Admin admin);
	ResponseData updatedAdmin(Admin admin);
	ResponseData deletedAdmin(Admin admin);
	ResponseData getAdmin(Integer id);
	ResponseData pageQuery(AdminQueryDTO dto);
	ResponseData login(Admin admin);
}
