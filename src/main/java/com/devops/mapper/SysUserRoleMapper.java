package com.devops.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.devops.entity.SysUserRole;

/**
 *
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

	List<String> selectPermissionByUid(String uid);

}