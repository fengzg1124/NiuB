package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/order")
public class OrderController {

	
	@RequestMapping(value="/orderList")
	public String roleList(Model model,HttpServletRequest request,
			String pageNum,String pageSize){
		
		return "admin/order/orderList";
	}
	
	
}
