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
	
	@RequestMapping(value="/loanDetail-{phid}")
	public String loanDetail(Model model,User user,HttpServletRequest request,@PathVariable(name="phid")String phid){
		model.addAttribute("phid", phid);
		return "loan_Detail";
	}
}
