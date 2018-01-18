package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/adminUser")
public class AdminUserController {

	@RequestMapping(value="/userLogin")
	public String userLogin(Model model,HttpServletRequest request,HttpServletResponse response){
		return "admin/index";
	}
}
