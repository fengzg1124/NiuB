package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.niub.domain.Role;
import cn.com.niub.dto.RoleDto;
import cn.com.niub.jpa.RoleRepository;
import cn.com.niub.mapper.RoleMapper;
import cn.com.niub.utils.AbleStatus;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private RoleRepository roleRepository;
	
	//按照条件查询
	public Page<Role> findRoles(RoleDto dto,int pageNo,int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		Role role = new Role(dto);
		role.setDelFlag(AbleStatus.usable_1.getCode());
		return roleMapper.selectByRolePage(role);
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
	
	//根据id修改权限
	public void updateRoleById(Role role){
		roleMapper.updateByPrimaryKeySelective(role);
	}
}
