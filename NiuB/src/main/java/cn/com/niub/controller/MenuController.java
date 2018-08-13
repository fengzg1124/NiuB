package cn.com.niub.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import com.github.pagehelper.Page;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuExample.Criteria;
import cn.com.niub.domain.User;
import cn.com.niub.dto.MenuDto;
import cn.com.niub.service.MenuService;
import cn.com.niub.utils.ControllerUtils;

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
		
		Page<Menu> menus = menuService.findMenus(dto,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", menus);
		return "admin/system/menu/menuList";
	}
	
	@RequestMapping(value="/toMenuAdd")
	private String toMenuAdd(Model model) {
		
		List<Menu> menus = new ArrayList<>();
		menus = menuService.findAll();
		
		MenuDto dto = new MenuDto();
		model.addAttribute("dto", dto);
		
		model.addAttribute("menus", menus);
		return "admin/system/menu/menuAdd";
	}
	
	
	@RequestMapping(value="/menuSave")
	@ResponseBody
	private String menuSave(HttpServletRequest request,HttpServletResponse response,String formData) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		Menu menu = new Menu();
		String message=null;
		if(StringUtils.isNotBlank(formData)){
			menu = JSON.parseObject(formData,Menu.class);
			if(StringUtils.isBlank(menu.getId())){
				menu.setId(ControllerUtils.getUUID());
				menu.setCreatedBy(adminuser.getId());
				menu.setCreatedTime(new Date());
				menu.setDelFlag(1);
				menu.setFlag(1);
				menuService.saveMenu(menu);
			}else{
				menu.setUpdateBy(adminuser.getId());
				menu.setUpdateDate(new Date());
				menuService.updateMenu(menu, menu.getId());
			}
			message = "1";
		}else{
			message = "-1";
		}
		return message;
	}
	//修改菜单
	@RequestMapping(value="/toMenuEdit")
	private String toMenuEdit(Model model,HttpServletRequest request,String menuIds) {
		
		List<Menu> menus = new ArrayList<>();
		menus = menuService.findAll();
		Menu menu = new Menu();
		
		if(StringUtils.isNotBlank(menuIds)){
			menu = menuService.findMenuById(menuIds);
		}
		
		MenuDto dto;
		if(null!=menu){
			dto = new MenuDto(menu);
			if(StringUtils.isNotBlank(dto.getParentId())){
				dto.setParentName(menuService.findMenuById(dto.getParentId()).getMenuName());
			}
		}else{
			dto = new MenuDto();
		}
		model.addAttribute("dto", dto);
		
		model.addAttribute("menus", menus);
		return "admin/system/menu/menuAdd";
	}
	
	//删除菜单
	@RequestMapping(value="/toMenuDelete")
	private String toMenuDelete(Model model,HttpServletRequest request,String menuIds) {
		
		List<String> ids = Arrays.asList(menuIds.split(","));  
		Menu menu = new Menu();
		menu.setDelFlag(0);
		
		menuService.updateMenus(menu,ids);
		
		return "redirect:/menu/menuList";
	}
}
