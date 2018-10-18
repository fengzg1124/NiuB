package cn.com.niub.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;

import cn.com.niub.domain.User;
import cn.com.niub.dto.OrderDto;
import cn.com.niub.service.CarService;
import cn.com.niub.service.ContactsService;
import cn.com.niub.service.JobService;
import cn.com.niub.service.OrderService;
import cn.com.niub.service.RoomService;
import cn.com.niub.service.SpouseService;
import cn.com.niub.service.SupplementaryService;
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
	
	@RequestMapping(value="/orderSave")
	@ResponseBody
	private String orderSave(HttpServletRequest request,HttpServletResponse response,OrderDto dto) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		String message=null;
		if(null != dto){
			if(StringUtils.isBlank(dto.getId())){
				dto.setId(ControllerUtils.getUUID());
				dto.setCreater(adminuser.getId());
				dto.setCreateTime(new Date());
				dto.setDelFlag(AbleStatus.usable_1.getCode());
				orderService.saveOrder(dto);
			}else{
				dto.setUpdater(adminuser.getId());
				dto.setUpdateTime(new Date());
				orderService.saveOrder(dto);
			}
			message = "1";
		}else{
			message = "-1";
		}
		return message;
	}
}
