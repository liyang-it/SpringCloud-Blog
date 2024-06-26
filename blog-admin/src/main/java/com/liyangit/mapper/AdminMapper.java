package com.liyangit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liyangit.entity.Admin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <h2>管理员Mapper接口</h2>
 * <p>
 *
 * </p>
 *
 * @author LiYang
 */
public interface AdminMapper extends BaseMapper<Admin> {
	@Select("SELECT id, username, status FROM `t_admin` where username = #{username} and password = #{password} and deleted = 0")
	Admin getAdminByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
}
