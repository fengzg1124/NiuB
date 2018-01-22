package cn.com.niub.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.niub.domain.Log;
import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.service.LogService;
import cn.com.niub.service.UserService;
import cn.com.niub.utils.ServiceUtils;



@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@Autowired
	LogService logService;
	
	//打开首页
	@RequestMapping(value="/index-{phid}")
	public String index(Model model,@PathVariable(name="phid")String phid,HttpServletRequest request){
		
		//记录日志
		Log log = new Log();
		log.setId(ServiceUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("index-");
		log.setLog("打开首页，推广用户："+phid);
		
		HttpSession session = request.getSession();
		
		//未登录保存推广信息
		if(null == session.getAttribute("phid") && StringUtils.isNotBlank(phid)){
			session.setAttribute("phid", phid);
		}
		
		//登录或注册成功
		if(null != session.getAttribute("user")){
			model.addAttribute("phid", phid);
		}
		
		log.setEndTime(new Date());
		logService.saveLog(log);
		return "index";
	}
	
	//跳转到登录页
	@RequestMapping(value="/toLogin")
	public String toLogin(){
		return "login";
	}
	
	//跳转到注册页
	@RequestMapping(value="/toRegister")
	public String toRegister(){
		return "register";
	}
	
	//官网登录
	@RequestMapping(value="/login")
	public String login(Model model,User user,HttpServletRequest request){
		
		//记录日志
		Log log = new Log();
		log.setId(ServiceUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("login");
		log.setUserId(user.getId());
		
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		criteria.andPasswordEqualTo(user.getPassword());
		List<User> users = userService.findUsers(example);
		if(users.size()<0){
			log.setLog("登录失败");
			model.addAttribute("mes", "登录失败,手机号或密码错误！");
			return "login";
		}else{
			user = users.get(0);
		}
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		log.setLog("登录成功");
		log.setEndTime(new Date());
		logService.saveLog(log);
		return "redirect:/index-"+user.getId();
	}
	
	@RequestMapping(value="/register")
	public String register(Model model,User user,HttpServletRequest request,@PathVariable(name="phid")String phid){
			
		//记录日志
		Log log = new Log();
		log.setId(ServiceUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("register");
		log.setUserId(user.getId());
		
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		List<User> users = userService.findUsers(example);
		if(users.size()>0){
			log.setLog("失败，手机号码已存在"+user.getPhoneNumber());
			model.addAttribute("mes", "手机号码已存在！");
			return "register";
		}
		
		user.setId(ServiceUtils.getUUID());
		user.setParentId(phid);
		User userp = userService.findUserById(phid);
		if(StringUtils.isNotBlank(userp.getHierarchyId())){
			user.setHierarchyId(userp.getHierarchyId()+"-"+phid);
		}else{
			user.setHierarchyId(phid);
		}
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setState(1);
		
		userService.saveUser(user);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		log.setLog("注册成功，用户名："+user.getUserName()+",手机号："+user.getPhoneNumber());
		log.setEndTime(new Date());
		logService.saveLog(log);
		return "redirect:/index-"+user.getId();
	}
	
	//管理员登录
	@RequestMapping(value="/userLogin")
	public String userLogin(Model model,HttpServletRequest request,HttpServletResponse response){
		return "admin/index";
	}
	
}
