package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.com.niub.dto.AuditContentDto;
import cn.com.niub.dto.FileDto;
import cn.com.niub.service.AuditContentService;
import cn.com.niub.service.FileService;
import cn.com.niub.utils.AbleStatus;
import cn.com.niub.utils.FileUtils;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.niub.domain.User;
import cn.com.niub.dto.OrderDto;
import cn.com.niub.service.OrderService;
import cn.com.niub.utils.PageUtils;
import cn.com.niub.utils.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping(value="/orderAudit")
public class OrderAuditController {

	@Autowired
	OrderService orderService;

	@Autowired
	AuditContentService auditContentService;

	@Autowired
	FileService fileService;
	
	//查询审核列表
	@RequestMapping(value="/orderAuditList/{status}")
	public String orderAuditList(Model model,HttpServletRequest request,OrderDto dto,@PathVariable("status") String status,
			String pageNum,String pageSize){
		
		if(null == dto){
			dto = new OrderDto();
		}
		//1、提交，2、初审通过，3、初审不通过，4、征信审核通过，5、征信审核不通过，6、中介合同提交，7、银行材料提交，8、银行审核通过，9、银行审核不通过
        dto.setStatus(status);
		//Pagination<OrderDto> page = PageUtils.newPagination(pageNum, pageSize);
		//page = orderService.findOrder(dto,page);
		//分页页码
		pageNum = StringUtils.isBlank(pageNum)?"1":pageNum;
		//列表行数
		pageSize = StringUtils.isBlank(pageSize)?"10":pageSize;

		//查询用户列表
		Page<OrderDto> page = orderService.findOrder(dto,Integer.valueOf(pageNum),Integer.valueOf(pageSize));

		model.addAttribute("status", status);
		model.addAttribute("page", page);
		model.addAttribute("dto", dto);
		return "admin/orderAudit/orderAuditList";
	}
	
	//跳转到审核页面
	@RequestMapping(value="/toOrderAudit")
	private String toOrderAudit(Model model, String id) {

		String path = "admin/orderAudit/orderAudit";
		OrderDto dto = new OrderDto();
		
		if(StringUtils.isNotBlank(id)){
			dto = orderService.findOrderAllById(id);
		}
		
		model.addAttribute("dto", dto);

		if("4".equals(dto.getStatus())){
			path = "admin/orderAudit/orderAuditContract";
		}

		return path;
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


	//保存审核信息
	@RequestMapping(value="/orderAuditContractSave")
	@ResponseBody
	private String orderAuditContractSave(HttpServletRequest request, OrderDto dto,@RequestParam("file") MultipartFile file) {

		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");

		String message=null;

		if(null != dto){
			OrderDto od = orderService.findOrderById(dto.getId());
			od.setUpdater(adminuser.getId());
			od.setStatus(dto.getStatus());
			od.setServiceFee(dto.getServiceFee());
			orderService.saveOrder(od);
			message = "1";
		}

		//删除现有合同
		fileService.deleteByMid(dto.getId());

		String path="";
		try {
			path=ResourceUtils.getURL("classpath:").getPath()+"static/img/contract/";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		File rootPathTest = new File(path);
		if (!rootPathTest.exists()) {
			rootPathTest.mkdirs();
		}

		//上传的附件
		String fujian = file.getOriginalFilename();
		String type = "contract";
		String url = "";
		String name = "";
		if (fujian != null && !"".equals(fujian) && FileUtils.extIsValid(fujian, "")) {
			FileDto fileDto = new FileDto();
			fujian = fujian.substring(fujian.lastIndexOf(File.separator) + 1, fujian.length());
			name = type + System.currentTimeMillis() + fujian.substring(fujian.indexOf("."), fujian.length());
			url = path + name;
			FileUtils.saveFile(file, url);
			fileDto.setFileUrl(url);
			fileDto.setFileName(name);
			fileDto.setType(type);
			fileDto.setDocmentName(fujian);
			fileDto.setMid(dto.getId());
			fileDto.setDelFlag(AbleStatus.usable_1.getCode());
			fileService.saveFile(fileDto);
		}

		return message;
	}

	//提交银行材料
	@RequestMapping(value="/submitBank")
	private String submitBank(HttpServletRequest request, String id) {

		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");

		OrderDto od = orderService.findOrderById(id);
		od.setUpdater(adminuser.getId());
		od.setStatus("7");
		orderService.saveOrder(od);

		return "redirect:/orderAudit/orderAuditList/6";
	}
	
}
