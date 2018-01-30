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
import cn.com.niub.utils.ControllerUtils;



@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	@Autowired
	LogService logService;
	
	//首页
	@RequestMapping(value="/")
	public String home(){
		return "redirect:/index-";
	}
	//打开首页
	@RequestMapping(value="/index-{tid}")
	public String index(Model model,@PathVariable(name="tid")String tid,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		session.setAttribute("Tphid", "");
		//session.setAttribute("user", "");
		
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("index-");
		log.setLog("打开首页，推广用户："+tid+",打开ip："+ControllerUtils.getIp(request));
		
		//保存推广信息
		if(StringUtils.isNotBlank(tid)){
			session.setAttribute("Tphid", tid);
		}
		
		model.addAttribute("phid", "");
		
		User userc = (User) session.getAttribute("user");
		if(null != userc){
			model.addAttribute("phid", userc.getId());
			model.addAttribute("userName", userc.getUserName());
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
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("login");
		
		if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhoneNumber())){
			log.setLog("登录失败");
			model.addAttribute("mes", "登录失败,登录信息填写不完整！");
			return "login";
		}
		
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		criteria.andPasswordEqualTo(user.getPassword());
		criteria.andStateEqualTo(1);
		List<User> users = userService.findUsers(example);
		if(users.size()!=1){
			log.setLog("登录失败");
			model.addAttribute("mes", "登录失败,手机号或密码错误！");
			return "login";
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("user", users.get(0));
		
		model.addAttribute("phid", users.get(0).getId());
		model.addAttribute("mes", users.get(0).getUserName()+",登录成功");
		model.addAttribute("userName", users.get(0).getUserName());
		
		log.setUserId(user.getId());
		log.setLog("登录成功,登录ip："+ControllerUtils.getIp(request));
		log.setEndTime(new Date());
		logService.saveLog(log);
		return "index";
	}
	
	@RequestMapping(value="/register")
	public String register(Model model,User user,HttpServletRequest request){
			
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("register");
		
		if(StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPhoneNumber())|| StringUtils.isBlank(user.getPassword())){
			log.setLog("失败，未输入用户信息");
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "用户信息填写不完整！");
			return "register";
		}
		
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		List<User> users = userService.findUsers(example);
		if(users.size()>0){
			log.setLog("失败，手机号码已存在"+user.getPhoneNumber());
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "手机号码已存在！");
			return "register";
		}
		
		user.setId(ControllerUtils.getUUID());
		String phid = (String) request.getSession().getAttribute("Tphid");
		user.setParentId(phid);
		User userp = userService.findUserById(phid);
		if(null != userp){
			if(StringUtils.isNotBlank(userp.getHierarchyId())){
				user.setHierarchyId(userp.getHierarchyId()+"-"+phid);
			}else{
				user.setHierarchyId(phid);
			}
		}
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		
		userService.saveUser(user);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		model.addAttribute("phid", user.getId());
		model.addAttribute("mes", user.getUserName()+",注册成功");
		model.addAttribute("userName", user.getUserName());
		
		log.setLog("注册成功，用户名："+user.getUserName()+",手机号："+user.getPhoneNumber()+",注册ip："+ControllerUtils.getIp(request));
		log.setEndTime(new Date());
		logService.saveLog(log);
		return "index";
	}
	
	//管理员登录
	@RequestMapping(value="/userExit")
	public String userExit(Model model,HttpServletRequest request,HttpServletResponse response){
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("register");
		
		HttpSession session = request.getSession();
		User userc = (User) session.getAttribute("user");
		
		log.setLog("退出登录，用户名："+userc.getUserName()+",手机号："+userc.getPhoneNumber());
		log.setEndTime(new Date());
		logService.saveLog(log);
		
		session.setAttribute("user", "");
		
		return "index";
	}
		
	//管理员登录
	@RequestMapping(value="/adminLogin")
	public String adminLogin(Model model,HttpServletRequest request,HttpServletResponse response){
		return "admin/frames/sysframe_index";
	}
	
	//管理员注册
	@RequestMapping(value="/adminRegister")
	public String adminRegister(Model model,HttpServletRequest request,HttpServletResponse response){
		return "admin/register";
	}
}
