package cn.com.niub.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.niub.domain.Log;
import cn.com.niub.domain.User;
import cn.com.niub.service.LogService;
import cn.com.niub.service.UserService;
import cn.com.niub.utils.ServiceUtils;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	LogService logService;
	
	@RequestMapping(value="/loanDetail-{type}")
	public String loanDetail(Model model,HttpServletRequest request,@PathVariable(name="type")String type){
		model.addAttribute("type", type);
		User user = (User) request.getSession().getAttribute("user");
		if(null != user){
			model.addAttribute("user", user);
		}
		return "loan_Detail";
	}
	
	@RequestMapping(value="/saveLoanUser-{type}")
	public String saveLoanUser(Model model,User user, HttpServletRequest request,@PathVariable(name="type")String type){
		
		//记录日志
		Log log = new Log();
		log.setId(ServiceUtils.getUUID());
		log.setStartTime(new Date());
		log.setType("saveLoanUser");
		
		user.setId(ServiceUtils.getUUID());
		String phid = (String) request.getSession().getAttribute("phid");
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
		model.addAttribute("mes", "提交成功，感谢您相信猎多金，财富经理将尽快联系您！");
		
		log.setLog("贷款用户信息提交成功，用户名："+user.getUserName()+",手机号："+user.getPhoneNumber());
		log.setEndTime(new Date());
		logService.saveLog(log);
		
		User userc = (User) request.getSession().getAttribute("user");
		if(null != userc){
			model.addAttribute("phid", userc.getId());
		}
		return "index";
	}
}
