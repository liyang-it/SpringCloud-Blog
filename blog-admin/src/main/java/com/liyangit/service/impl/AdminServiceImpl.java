package com.liyangit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liyangit.dto.AdminQueryDTO;
import com.liyangit.entity.Admin;
import com.liyangit.incrementer.DefaultIdentifierGenerator;
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
	
	public boolean verifyPasswordRules(String password) {
		// 校验密码复杂度，密码要包含大小写字母,数字,且长度6-15个字符。
		String regex = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$#!%*?&]{6,15}";
		return !password.matches(regex);
	}
	
	@Override
	public ResponseData insertAdmin(Admin admin) {
		if(verifyPasswordRules(admin.getPassword())){
			return ResponseData.normal(ResultCode.ERROR_INSERT.getCode(), "密码必须包含大小写字母,数字,且长度6-15个字符");
		}
		try {
			admin.setId(DefaultIdentifierGenerator.getInstance().nextId(null).toString());
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
	public ResponseData updateAdmin(Admin admin) {
		
		if(StringUtils.isBlank(admin.getId())){
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), ResultCode.ERROR_UPDATE.getMsg());
		}
		
		if(StringUtils.isNotBlank(admin.getPassword()) && verifyPasswordRules(admin.getPassword())){
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), "密码必须包含大小写字母,数字,且长度6-15个字符");
		}
		try {
			adminMapper.updateById(admin);
		} catch (DuplicateKeyException e) {
			return ResponseData.normal(AdminResultCode.ERROR_USERNAME_UNIQUE.getCode(), AdminResultCode.ERROR_USERNAME_UNIQUE.getMsg());
		}
		
		return ResponseData.success();
	}
	
	@Override
	public ResponseData deleteAdmin(Admin admin) {
		admin.setDeleted(true);
		admin.setDeletedTime(LocalDateTime.now().toString());
		return updateAdmin(admin);
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
