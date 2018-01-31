package cn.com.niub.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//import cn.com.niub.domain.AdminUser;
import cn.com.niub.domain.AdminUserExample;
import cn.com.niub.domain.Log;
import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.domain.UserExample.Criteria;
import cn.com.niub.service.AdminUserService;
import cn.com.niub.service.LogService;
import cn.com.niub.service.UserService;
import cn.com.niub.utils.ControllerUtils;



@Controller
public class HomeController {

	@Autowired
	UserService userService;
	
	/*@Autowired
	AdminUserService adminUserService;*/
	
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
			log.setLog("注册失败，未输入用户信息");
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "用户信息填写不完整！");
			return "register";
		}
		
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andPhoneNumberEqualTo(user.getPhoneNumber());
		criteria.andStateEqualTo(user.getState());
		List<User> users = userService.findUsers(example);
		if(users.size()>0){
			log.setLog("注册失败，手机号码已存在"+user.getPhoneNumber());
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "手机号码已存在！");
			return "register";
		}
		
		//ip限制
		String ip=ControllerUtils.getIp(request);
		UserExample examplei = new UserExample();
		Criteria criteriai = examplei.createCriteria();
		criteriai.andIpEqualTo(ip);//ip
		
		Calendar theCa = Calendar.getInstance();
		theCa.setTime(new Date());
		theCa.add(theCa.DATE, -30);
		criteriai.andCreateTimeGreaterThanOrEqualTo(theCa.getTime());//申请时间30天以内
		
		criteriai.andStateEqualTo(1);//注册标记
		List<User> usersi = userService.findUsers(examplei);
		if(usersi.size()>0){
			log.setLog("注册失败，30天内重复注册！");
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "您在近期已经注册过账号，不能重复注册！");
			return "register";
		}
		
		
		user.setId(ControllerUtils.getUUID());
		user.setIp(ip);
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
	
	//用户退出
	@RequestMapping(value="/userExit")
	public String userExit(Model model,HttpServletRequest request,HttpServletResponse response){
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("userExit");
		
		HttpSession session = request.getSession();
		User userc = (User) session.getAttribute("user");
		
		log.setLog("退出登录，用户名："+userc.getUserName()+",手机号："+userc.getPhoneNumber());
		log.setEndTime(new Date());
		logService.saveLog(log);
		
		session.setAttribute("user", null);
		
		return "index";
	}
	
	
	//管理员登录
	@RequestMapping(value="/adminLogin")
	public String adminLogin(Model model,User user,HttpServletRequest request){

		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("adminLogin");
		
		if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhoneNumber())){
			log.setLog("登录失败");
			model.addAttribute("mes", "登录失败,登录信息填写不完整！");
			return "admin/adminLogin";
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
			return "admin/adminLogin";
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("adminUser", users.get(0));
		
		model.addAttribute("phid", users.get(0).getId());
		model.addAttribute("mes", users.get(0).getUserName()+",登录成功");
		model.addAttribute("userName", users.get(0).getUserName());
		
		log.setUserId(user.getId());
		log.setLog("登录成功,登录ip："+ControllerUtils.getIp(request));
		log.setEndTime(new Date());
		logService.saveLog(log);
		
		//查询用户信息
		UserExample examplec = new UserExample();
		Criteria criteriac = examplec.createCriteria();
		criteriac.andHierarchyIdLike("%"+users.get(0).getId()+"%");
		List<Integer> st = new ArrayList<Integer>();
		st.add(1);
		st.add(2);
		criteriac.andStateIn(st);
		List<User> userc = userService.findUsers(examplec);
		if(!"ee3653fe07cc4513b41319090a2516be".equals(users.get(0).getId())){
			for(User us:userc){
				if(us.getState()==1){
					us.setUserName(us.getUserName().substring(0,1));
					us.setPhoneNumber("***********");
				}
			}
		}
		model.addAttribute("list", userc);
		
		return "admin/frames/sysframe_index";
	}
	
	/*//管理员注册
	@RequestMapping(value="/adminRegister")
	public String adminRegister(Model model,AdminUser adminUser,HttpServletRequest request){

		
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("adminRegister");
		
		if(StringUtils.isBlank(adminUser.getUserName()) || StringUtils.isBlank(adminUser.getPhoneNumber())|| StringUtils.isBlank(adminUser.getPassword())){
			log.setLog("失败，未输入用户信息");
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "用户信息填写不完整！");
			return "admin/adminRegister";
		}
		
		
		List<AdminUser> users = adminUserService.findUserByStatePhoneNumber(adminUser);
		if(users.size()>0){
			log.setLog("失败，手机号码已存在"+adminUser.getPhoneNumber());
			log.setEndTime(new Date());
			logService.saveLog(log);
			model.addAttribute("mes", "手机号码已存在！");
			return "admin/adminRegister";
		}
		
		adminUser.setId(ControllerUtils.getUUID());
		String phid = (String) request.getSession().getAttribute("Tphid");
		adminUser.setParentId(phid);
		AdminUser userp = adminUserService.findUserById(phid);
		if(null != userp){
			if(StringUtils.isNotBlank(userp.getHierarchyId())){
				adminUser.setHierarchyId(userp.getHierarchyId()+"-"+phid);
			}else{
				adminUser.setHierarchyId(phid);
			}
		}
		adminUser.setCreateTime(new Date());
		adminUser.setUpdateTime(new Date());
		
		
		adminUserService.saveUser(adminUser);
		
		User user = new User();
		BeanUtils.copyProperties(adminUser, user);
		user.setState(1);
		userService.saveUser(user);
		
		HttpSession session = request.getSession();
		session.setAttribute("adminUser", adminUser);
		
		model.addAttribute("phid", adminUser.getId());
		model.addAttribute("mes", adminUser.getUserName()+",注册成功");
		model.addAttribute("userName", adminUser.getUserName());
		
		log.setLog("注册成功，用户名："+adminUser.getUserName()+",手机号："+adminUser.getPhoneNumber()+",注册ip："+ControllerUtils.getIp(request));
		log.setEndTime(new Date());
		logService.saveLog(log);
	
		return "admin/frames/sysframe_index";
	}*/
	
	//管理员退出
	@RequestMapping(value="/adminUserExit")
	public String adminUserExit(Model model,HttpServletRequest request,HttpServletResponse response){
		//记录日志
		Log log = new Log();
		log.setId(ControllerUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("adminUserExit");
		
		HttpSession session = request.getSession();
		User userc = (User) session.getAttribute("adminUser");
		
		log.setLog("退出登录，用户名："+userc.getUserName()+",手机号："+userc.getPhoneNumber());
		log.setEndTime(new Date());
		logService.saveLog(log);
		
		session.setAttribute("adminUser", null);
		
		return "admin/adminLogin";
	}
}
