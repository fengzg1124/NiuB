package cn.com.niub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.niub.domain.RoleMenu;
import cn.com.niub.jpa.RoleMenuRepository;

@Service
public class RoleMenuService {

	@Autowired
	RoleMenuRepository roleMenuRepository;
	
	public List<RoleMenu> findAll(){
		return roleMenuRepository.findAll();
	}
	
	public void saveRoleMenu(RoleMenu roleMenu){
		roleMenuRepository.saveAndFlush(roleMenu);
	}
	
	@Transactional
	public void deleteByRoleId(String roleId){
		roleMenuRepository.deleteByRoleId(roleId);
	}
}
