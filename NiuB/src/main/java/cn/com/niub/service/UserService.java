package cn.com.niub.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.mapper.UserMapper;


@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	//按条件查找
	public List<User> findUsers(UserExample example){
		List<User> users = userMapper.selectByExample(example);
		return users;
	}
	//按条件查找 分页
	public Page<User> findUsersPage(UserExample example,int pageNo,int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		Page<User> users = (Page<User>)userMapper.selectByExample(example);
		return users;
	}
	//按主键id查找
	public User findUserById(String id){
		User user = userMapper.selectByPrimaryKey(id);
		return user;
	}
	//新增用户
	public void saveUser(User user){
		userMapper.insert(user);
	}
}
