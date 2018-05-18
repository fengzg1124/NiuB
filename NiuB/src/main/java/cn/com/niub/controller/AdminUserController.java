package cn.com.niub.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;

import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.dto.UserDto;
import cn.com.niub.service.UserService;
import cn.com.niub.utils.PageInfo;

@Controller
@RequestMapping(value="/adminUser")
public class AdminUserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/userList")
	public String getUserList(Model model,HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		
		//查询用户列表
		Page<User> users = getUserList(adminuser.getId(),1,3);
		model.addAttribute("Page", users);
		return "admin/system/user/userList";
	}
	
	//查询用户列表
	public Page<User> getUserList(String userid,int pageNo,int pageSize){
		//查询用户信息
		UserExample examplec = new UserExample();
		Criteria criteriac = examplec.createCriteria();
		criteriac.andHierarchyIdLike("%"+userid+"%");
		List<Integer> st = new ArrayList<Integer>();
		st.add(1);
		st.add(2);
		criteriac.andStateIn(st);
		//List<User> userc = userService.findUsers(examplec);
		Page<User> users = userService.findUsersPage(examplec,pageNo,pageSize);
		if(!"ee3653fe07cc4513b41319090a2516be".equals(userid)){
			for(User us:users){
				if(us.getState()==1){
					us.setUserName(us.getUserName().substring(0,1));
					us.setPhoneNumber("***********");
				}
			}
		}
		return users;
	}
}
