package cn.com.niub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.Role;
import cn.com.niub.domain.RoleExample;
import cn.com.niub.mapper.RoleMapper;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	//按照条件查询
	public List<Role> findRoles(RoleExample example){
		 List<Role> roles = roleMapper.selectByExample(example);
		 return roles;
	}
	//按主键id查找
	public Role findRoleById(String id){
		Role role = roleMapper.selectByPrimaryKey(id);
		return role;
	}
	//新增权限
	public void saveRole(Role role){
		roleMapper.insert(role);
	}
}
