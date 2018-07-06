package cn.com.niub.controller;

import java.util.Date;

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
import com.github.pagehelper.Page;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuExample.Criteria;
import cn.com.niub.domain.User;
import cn.com.niub.dto.MenuDto;
import cn.com.niub.service.MenuService;

@Controller
@RequestMapping(value="/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value="/menuList")
	public String menuList(Model model,HttpServletRequest request,String formData,
			String pageNum,String pageSize){
		
		MenuDto dto = new MenuDto();
		
		if(StringUtils.isNotBlank(formData)){
			dto = JSON.parseObject(formData,MenuDto.class);
		}
		
		//分页页码
		pageNum = StringUtils.isBlank(pageNum)?"1":pageNum;
		//列表行数
		pageSize = StringUtils.isBlank(pageSize)?"10":pageSize;
		
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andMenuNameLike("%"+"%");
		
		Page<Menu> menus = menuService.findMenus(example,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", menus);
		return "admin/system/menu/menuList";
	}
	
	@RequestMapping(value="/toMenuAdd")
	private String toMenuAdd(Model model) {
		MenuDto dto = new MenuDto();
		model.addAttribute("dto", dto);
		return "admin/system/menu/menuAdd";
	}
	
	
	@RequestMapping(value="/menuSave")
	@ResponseBody
	private String menuSave(Model model,HttpServletRequest request,HttpServletResponse response,String formData) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		
		Menu menu = new Menu();
		
		if(StringUtils.isNotBlank(formData)){
			menu = JSON.parseObject(formData,Menu.class);
			menu.setCreatedBy(adminuser.getId());
			menu.setCreatedTime(new Date());
			menu.setDelFlag(1);
			menu.setFlag(1);
			menuService.saveMenu(menu);
			model.addAttribute("message", "1");
		}else{
			model.addAttribute("message", "-1");
		}
		return "-1";
	}
}
