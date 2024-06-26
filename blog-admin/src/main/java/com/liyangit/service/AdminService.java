package com.liyangit.service;

import com.liyangit.dto.AdminQueryDTO;
import com.liyangit.entity.Admin;
import com.liyangit.result.ResponseData;

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
	ResponseData updateAdmin(Admin admin);
	ResponseData deleteAdmin(Admin admin);
	ResponseData getAdmin(Integer id);
	ResponseData pageQuery(AdminQueryDTO dto);
	ResponseData login(Admin admin);
}
