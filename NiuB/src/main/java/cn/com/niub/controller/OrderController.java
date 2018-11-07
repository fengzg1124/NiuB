package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		//房
		RoomDto room = new RoomDto();
		//车
		CarDto car = new CarDto();
		//职业
		JobDto job = new JobDto();
		//补充材料
		SupplementaryDto supplementary = new SupplementaryDto();
		//配偶信息
		SpouseDto spouse = new SpouseDto();
		//联系人
		ContactsDto contacts = new ContactsDto();
		
		dto.setRoom(room);
		dto.setCar(car);
		dto.setJob(job);
		dto.setSupplementary(supplementary);
		dto.setSpouse(spouse);
		dto.setContacts(contacts);
		
		model.addAttribute("dto", dto);
		return "admin/order/orderAdd";
	}
	
	@RequestMapping(value="/orderSave")
	//@ResponseBody
	private String orderSave(HttpServletRequest request,HttpServletResponse response, OrderDto dtoo, String formData) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		OrderDto dto = new OrderDto();
		if(StringUtils.isNotBlank(formData)){
			dto = JSON.parseObject(formData,OrderDto.class);
		}else{
			return "-1";
		}
		
		//房
		RoomDto roomd = new RoomDto();
		if(StringUtils.isNotBlank(formData)){
			roomd = JSON.parseObject(formData,RoomDto.class);
		}
		//车
		CarDto card = new CarDto();
		if(StringUtils.isNotBlank(formData)){
			card = JSON.parseObject(formData,CarDto.class);
		}
		//职业
		JobDto jobd = new JobDto();
		if(StringUtils.isNotBlank(formData)){
			jobd = JSON.parseObject(formData,JobDto.class);
		}
		//补充材料
		SupplementaryDto supplementaryd = new SupplementaryDto();
		if(StringUtils.isNotBlank(formData)){
			supplementaryd = JSON.parseObject(formData,SupplementaryDto.class);
		}
		//配偶信息
		SpouseDto spoused = new SpouseDto();
		if(StringUtils.isNotBlank(formData)){
			spoused = JSON.parseObject(formData,SpouseDto.class);
		}
		//联系人
		ContactsDto contactsd = new ContactsDto();
		if(StringUtils.isNotBlank(formData)){
			contactsd = JSON.parseObject(formData,ContactsDto.class);
		}
		
		dto.setRoom(roomd);
		dto.setCar(card);
		dto.setJob(jobd);
		dto.setSupplementary(supplementaryd);
		dto.setSpouse(spoused);
		dto.setContacts(contactsd);
		
		String message=null;
		if(null != dto){
			if(StringUtils.isBlank(dto.getId())){
				dto.setId(ControllerUtils.getUUID());
				dto.setCreater(adminuser.getId());
				dto.setDelFlag(AbleStatus.usable_1.getCode());
				orderService.saveOrder(dto);
			}else{
				dto.setUpdater(adminuser.getId());
				orderService.saveOrder(dto);
			}
			message = "1";
		}else{
			message = "-1";
		}
		return message;
		//return "redirect:/order/orderList";
	}
}
