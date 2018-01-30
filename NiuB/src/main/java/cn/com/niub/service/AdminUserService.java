package cn.com.niub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.AdminUser;
import cn.com.niub.domain.AdminUserExample;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.AdminUserExample.Criteria;
import cn.com.niub.mapper.AdminUserMapper;


@Service
public class AdminUserService {

	@Autowired
	private AdminUserMapper adminUserMapper;
	//按条件查找
	public List<AdminUser> findUserByLogin(AdminUser user){
		AdminUserExample example = new AdminUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		criteria.andPasswordEqualTo(user.getPassword());
		criteria.andStateEqualTo(3);
		List<AdminUser> users = adminUserMapper.selectByExample(example);
		return users;
	}
	
	//按条件查找
	public List<AdminUser> findUserByHierarchyId(AdminUser user){
		AdminUserExample example = new AdminUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andHierarchyIdLike(user.getId());
		List<AdminUser> users = adminUserMapper.selectByExample(example);
		return users;
	}
	public List<AdminUser> findUserByStatePhoneNumber(AdminUser user){
		AdminUserExample example = new AdminUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		criteria.andStateEqualTo(user.getState());
		List<AdminUser> users = adminUserMapper.selectByExample(example);
		return users;
	}
	
	//按主键id查找
	public AdminUser findUserById(String id){
		AdminUser user = adminUserMapper.selectByPrimaryKey(id);
		return user;
	}
	//新增用户
	public void saveUser(AdminUser user){
		adminUserMapper.insert(user);
	}
}
