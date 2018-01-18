package cn.com.niub.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.mapper.UserMapper;
import cn.com.niub.utils.ServiceUtils;


@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	public List<User> findUsers(UserExample example){
		List<User> users = userMapper.selectByExample(example);
		return users;
	}
	
	public void saveUser(User user){
		user.setId(ServiceUtils.getUUID());
		userMapper.insert(user);
	}
}
