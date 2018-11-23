package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.com.niub.dto.AuditContentDto;
import cn.com.niub.service.AuditContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.niub.domain.User;
import cn.com.niub.dto.OrderDto;
import cn.com.niub.service.OrderService;
import cn.com.niub.utils.PageUtils;
import cn.com.niub.utils.Pagination;

@Controller
@RequestMapping(value="/orderAudit")
public class OrderAuditController {

	@Autowired
	OrderService orderService;

	@Autowired
	AuditContentService auditContentService;
	
	//查询审核列表
	@RequestMapping(value="/orderAuditList{status}")
	public String orderAuditList(Model model,HttpServletRequest request,OrderDto dto,@PathVariable("status") String status,
			String pageNum,String pageSize){
		
		if(null == dto){
			dto = new OrderDto();
		}
		//1、提交，2、征信审核通过，3、征信审核不通过，4、材料审核通过，5、材料审核不通过，6、银行审核通过，7、银行审核不通过
        dto.setStatus(status);
		Pagination<OrderDto> page = PageUtils.newPagination(pageNum, pageSize);
		page = orderService.findOrder(dto,page);
		model.addAttribute("page", page);
		model.addAttribute("dto", dto);
		return "admin/orderAudit/orderAuditList";
	}
	
	//跳转到审核页面
	@RequestMapping(value="/toOrderAudit")
	private String toOrderAudit(Model model, String id) {
		
		OrderDto dto = new OrderDto();
		
		if(StringUtils.isNotBlank(id)){
			dto = orderService.findOrderAllById(id);
		}
		
		model.addAttribute("dto", dto);
		return "admin/orderAudit/orderAudit";
	}
	
	//保存审核信息
	@RequestMapping(value="/orderAuditSave")
	@ResponseBody
	private String orderAuditSave(Model model,HttpServletRequest request, OrderDto dto) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		String message=null;
		if(null != dto){
			OrderDto od = orderService.findOrderById(dto.getId());
            od.setUpdater(adminuser.getId());
            od.setStatus(dto.getStatus());
            orderService.saveOrder(od);

            AuditContentDto auditContentDto = new AuditContentDto();
            auditContentDto.setOrderId(od.getId());
            auditContentDto.setStatus(od.getStatus());
            auditContentDto.setContent(dto.getAuditOpinion());
            auditContentDto.setCreater(adminuser.getId());
            auditContentService.saveAuditContent(auditContentDto);
			message = "1";
		}
		return message;
	}
	
}
