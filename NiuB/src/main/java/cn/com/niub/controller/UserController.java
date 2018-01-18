package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.niub.domain.User;
import cn.com.niub.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {

	@Autowired
	UserService userService;
	@RequestMapping(value="/saveUser-{phid}")
	public String saveUser(Model model,User user,HttpServletRequest request,@PathVariable(name="phid")String phid){
		if(user!=null){
			user.setRemark(phid);
			userService.saveUser(user);
			phid = user.getPhoneNumber().toString().substring(user.getPhoneNumber().toString().length()-4, user.getPhoneNumber().toString().length()); 
			model.addAttribute("phid", phid);
			return "redirect:/index-"+phid;
		}else{
			return "redirect:/index-";
		}
	}
	@RequestMapping(value="/loanDetail-{phid}")
	public String loanDetail(Model model,User user,HttpServletRequest request,@PathVariable(name="phid")String phid){
		model.addAttribute("phid", phid);
		return "loan_Detail";
	}
}
