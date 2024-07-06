package com.liyangit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liyangit.constant.RedisKeyPrefix;
import com.liyangit.entity.System;
import com.liyangit.mapper.SystemMapper;
import com.liyangit.result.ResponseData;
import com.liyangit.result.ResultCode;
import com.liyangit.service.SystemService;
import com.liyangit.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <h2>系统配置实现层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
@Service
public class SystemServiceImpl implements SystemService {
	private final SystemMapper mapper;
	private final RedisUtils redisUtils;
	private final String redisKeyPrefix = RedisKeyPrefix.MAIN.getPrefix() + "system:";
	public SystemServiceImpl(SystemMapper mapper, RedisUtils redisUtils) {
		this.mapper = mapper;
		this.redisUtils = redisUtils;
	}
	
	@Override
	public ResponseData listQuery(String code) {
		QueryWrapper<System> queryWrapper = null;
		String key;
		
		if (StringUtils.isBlank(code)) {
			key = redisKeyPrefix + "all";
		} else {
			// 带查询条件
			key = redisKeyPrefix + code;
			queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("code", code);
		}
		
		if (redisUtils.hasKey(key)) {
			return ResponseData.success(redisUtils.lGet(key, 0, -1));
		}
		List<System> list = mapper.selectList(queryWrapper);
		
		redisUtils.lSet(key, list);
		return ResponseData.success(list);
	}
	
	@Override
	public ResponseData update(System system) {
		if(null == system.getId()){
			return ResponseData.normal(ResultCode.ERROR_UPDATE.getCode(), ResultCode.ERROR_UPDATE.getMsg());
		}
		mapper.updateById(system);
		
		// 删除对应code的缓存
		redisUtils.del(redisKeyPrefix + system.getCode());
		return ResponseData.success();
	}
}
