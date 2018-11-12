package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.com.niub.domain.User;
import cn.com.niub.dto.CarDto;
import cn.com.niub.dto.ContactsDto;
import cn.com.niub.dto.JobDto;
import cn.com.niub.dto.OrderDto;
import cn.com.niub.dto.RoomDto;
import cn.com.niub.dto.SpouseDto;
import cn.com.niub.dto.SupplementaryDto;
import cn.com.niub.service.OrderService;
import cn.com.niub.utils.AbleStatus;
import cn.com.niub.utils.ControllerUtils;
import cn.com.niub.utils.PageUtils;
import cn.com.niub.utils.Pagination;

@Controller
@RequestMapping(value="/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@RequestMapping(value="/orderList")
	public String roleList(Model model,HttpServletRequest request,OrderDto dto,
			String pageNum,String pageSize){
		
		if(null == dto){
			dto = new OrderDto();
		}
		
		Pagination<OrderDto> page = PageUtils.newPagination(pageNum, pageSize);
		page = orderService.findOrder(dto,page);
		model.addAttribute("page", page);
		model.addAttribute("dto", dto);
		return "admin/order/orderList";
	}
	
	@RequestMapping(value="/toOrderAdd")
	private String toRoleAdd(Model model) {
		OrderDto dto = new OrderDto();
		
		model.addAttribute("dto", dto);
		return "admin/order/orderAdd";
	}
	
	@RequestMapping(value="/toOrderUpdate")
	private String toOrderUpdate(Model model, String id) {
		
		OrderDto dto = new OrderDto();
		
		if(StringUtils.isNotBlank(id)){
			dto = orderService.findOrderAllById(id);
		}
		
		model.addAttribute("dto", dto);
		return "admin/order/orderAdd";
	}
	
	@RequestMapping(value="/toOrderView")
	private String toOrderView(Model model, String id) {
		
		OrderDto dto = new OrderDto();
		
		if(StringUtils.isNotBlank(id)){
			dto = orderService.findOrderAllById(id);
		}
		
		model.addAttribute("dto", dto);
		return "admin/order/orderView";
	}
	
	@RequestMapping(value="/orderSave")
	@ResponseBody
	private String orderSave(Model model,HttpServletRequest request, OrderDto dto, RoomDto room, CarDto car,
			JobDto job, SupplementaryDto supplementary, SpouseDto spouse, ContactsDto contacts, String formData) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		dto.setDto(room, car, job, supplementary, spouse, contacts);
		
		String message=null;
		if(null != dto){
			if(StringUtils.isBlank(dto.getId())){
				dto.setId(ControllerUtils.getUUID());
				dto.setCreater(adminuser.getId());
				dto.setDelFlag(AbleStatus.usable_1.getCode());
				orderService.saveOrder(dto);
			}else{
				OrderDto od = orderService.findOrderById(dto.getId());
				dto.setCreater(od.getCreater());
				dto.setCreateTime(od.getCreateTime());
				dto.setUpdater(adminuser.getId());
				dto.setDelFlag(AbleStatus.usable_1.getCode());
				orderService.saveOrder(dto);
			}
			message = "1";
		}
		return message;
	}
	
	@RequestMapping(value="/orderDelete")
	private String orderDelete(Model model,HttpServletRequest request, String orderIds) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		String[] ids = orderIds.split(",");
		
		for(String i:ids){
			if(StringUtils.isNotBlank(i)){
				OrderDto od = orderService.findOrderById(i);
				od.setUpdater(adminuser.getId());
				od.setDelFlag(AbleStatus.disabled_0.getCode());
				orderService.saveOrder(od);
			}
		}
		
		return "redirect:/order/orderList";
	}
	
}
