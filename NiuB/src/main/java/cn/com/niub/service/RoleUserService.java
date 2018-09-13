package cn.com.niub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.niub.domain.RoleUser;
import cn.com.niub.jpa.RoleUserRepository;
import cn.com.niub.mapper.RoleUserMapper;

@Service
public class RoleUserService {

	@Autowired
	RoleUserMapper roleUserMapper;
	@Autowired
	RoleUserRepository roleUserRepository;
	
	//保存用户权限
	public void saveRoleUser(RoleUser roleUser){
		roleUserRepository.save(roleUser);
	}
	//根据用户id删除用户权限
	@Transactional
	public void deleteRoleUserByUserId(String userId){
		roleUserRepository.deleteByUserId(userId);
	}
	
	public List<RoleUser> findRoleUserByUserId(String userId){
		return roleUserRepository.findRoleUserByUserId(userId);
	}
}
