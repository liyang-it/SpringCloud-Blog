package com.liyangit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyangit.dto.AdminQueryDTO;
import com.liyangit.entity.Admin;
import com.liyangit.mapper.AdminMapper;
import com.liyangit.result.AdminResultCode;
import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import com.liyangit.service.AdminService;
import com.liyangit.utils.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * <h2>管理员服务实现层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Service
public class AdminServiceImpl implements AdminService {
	private final AdminMapper adminMapper;
	
	public AdminServiceImpl(AdminMapper adminMapper) {
		this.adminMapper = adminMapper;
	}
	
	@Override
	public ResponseData insertAdmin(Admin admin) {
		try {
			admin.setCreatedTime(LocalDateTime.now());
			admin.setDeleted(false);
			// 设置删除时间不为空，避免索引失效
			admin.setDeletedTime("-");
			adminMapper.insert(admin);
		} catch (DuplicateKeyException e) {
			return ResponseData.normal(AdminResultCode.ERROR_USERNAME_UNIQUE.getCode(), AdminResultCode.ERROR_USERNAME_UNIQUE.getMsg());
		}
		return ResponseData.success();
	}
	
	@Override
	public ResponseData updatedAdmin(Admin admin) {
		
		try {
			adminMapper.updateById(admin);
		} catch (DuplicateKeyException e) {
			return ResponseData.normal(AdminResultCode.ERROR_USERNAME_UNIQUE.getCode(), AdminResultCode.ERROR_USERNAME_UNIQUE.getMsg());
		}
		
		return ResponseData.success();
	}
	
	@Override
	public ResponseData deletedAdmin(Admin admin) {
		admin.setDeleted(true);
		admin.setDeletedTime(LocalDateTime.now().toString());
		updatedAdmin(admin);
		return ResponseData.success();
	}
	
	@Override
	public ResponseData getAdmin(Integer id) {
		return ResponseData.success(adminMapper.selectById(id));
	}
	
	@Override
	public ResponseData pageQuery(AdminQueryDTO dto) {
		Page<Admin> page = new Page<>(dto.getPage(), dto.getLimit());
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.like(StringUtils.isNotBlank(dto.getUsername()), "username", dto.getUsername());
		queryWrapper.eq(dto.getStatus() != null, "status", dto.getStatus());
		page = adminMapper.selectPage(page, queryWrapper);
		return ResponseData.success(page);
	}
	
	@Override
	public ResponseData login(Admin admin) {
		// 分布式session会话使用无状态jwt维护(不需要后台退出登录功能)，好处是简单方便，缺点是复杂的场景比较难实现，但是应用于当前系统足够了
		admin = adminMapper.getAdminByUserNameAndPassword(admin.getUsername(), admin.getPassword());
		if (null == admin) {
			return ResponseData.normal(ResultCode.ERROR_NOT_FOUND_USER.getCode(), ResultCode.ERROR_NOT_FOUND_USER.getMsg());
		}
		if (admin.getStatus()) {
			return ResponseData.normal(ResultCode.ERROR_ADMIN_DISABLE.getCode(), ResultCode.ERROR_ADMIN_DISABLE.getMsg());
		}
		
		// 生成token
		HashMap<String, String> result = new HashMap<>(2);
		result.put("username", admin.getUsername());
		result.put("token", TokenUtil.generateToken(admin.getUsername()));
		
		return ResponseData.success(result);
	}
}
