package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/adminUser")
public class AdminUserController {

	
	@RequestMapping(value="/dashboard5")
	public String dashboard() {
		
		return "admin/frames/dashboard_5";
	}
}
