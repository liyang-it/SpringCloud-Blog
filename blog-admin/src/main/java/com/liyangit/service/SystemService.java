package com.liyangit.service;

import com.liyangit.entity.System;
import com.liyangit.result.ResponseData;

/**
 * <h2>系统配置服务层</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface SystemService {
	ResponseData listQuery(String code);
	ResponseData update(System system);
}
