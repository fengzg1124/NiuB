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

import cn.com.niub.domain.Role;
import cn.com.niub.domain.RoleMenu;
import cn.com.niub.domain.User;
import cn.com.niub.dto.MenuDto;
import cn.com.niub.dto.RoleDto;
import cn.com.niub.dto.ZtreeDto;
import cn.com.niub.service.MenuService;
import cn.com.niub.service.RoleMenuService;
import cn.com.niub.service.RoleService;
import cn.com.niub.utils.AbleStatus;
import cn.com.niub.utils.ControllerUtils;

@Controller
@RequestMapping(value="/role")
public class RoleController {

	@Autowired
	RoleService roleService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private RoleMenuService roleMenuService;
	
	@RequestMapping(value="/roleList")
	public String roleList(Model model,HttpServletRequest request,String formData,RoleDto rol,
			String pageNum,String pageSize){
		
		RoleDto dto = new RoleDto();
		
		if(StringUtils.isNotBlank(formData)){
			dto = JSON.parseObject(formData,RoleDto.class);
		}
		
		//分页页码
		pageNum = StringUtils.isBlank(pageNum)?"1":pageNum;
		//列表行数
		pageSize = StringUtils.isBlank(pageSize)?"10":pageSize;
		
		Page<Role> roles = roleService.findRoles(dto,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
		
		model.addAttribute("dto", dto);
		model.addAttribute("page", roles);
		return "admin/system/role/roleList";
	}
	
	@RequestMapping(value="/toRoleAdd")
	private String toRoleAdd(Model model) {
		RoleDto dto = new RoleDto();
		model.addAttribute("dto", dto);
		return "admin/system/role/roleAdd";
	}
	
	
	@RequestMapping(value="/roleSave")
	@ResponseBody
	private String roleSave(HttpServletRequest request,HttpServletResponse response,String formData) {
		
		HttpSession session = request.getSession();
		User adminuser = (User) session.getAttribute("adminUser");
		Role role = new Role();
		String message=null;
		if(StringUtils.isNotBlank(formData)){
			role = JSON.parseObject(formData,Role.class);
			if(StringUtils.isBlank(role.getId())){
				role.setId(ControllerUtils.getUUID());
				role.setCreator(adminuser.getId());
				role.setCreateTime(new Date());
				role.setDelFlag(AbleStatus.usable_1.getCode());
				roleService.saveRole(role);
			}else{
				role.setUpdator(adminuser.getId());
				role.setUpdateTime(new Date());
				roleService.updateRoleById(role);
			}
			message = "1";
		}else{
			message = "-1";
		}
		return message;
	}
	//修改菜单
	@RequestMapping(value="/toRoleEdit")
	private String toRoleEdit(Model model,HttpServletRequest request,String roleIds) {
		
		Role role = new Role();
		
		if(StringUtils.isNotBlank(roleIds)){
			role = roleService.findRoleById(roleIds);
		}
		
		RoleDto dto;
		if(null!=role){
			dto = new RoleDto(role);
		}else{
			dto = new RoleDto();
		}
		model.addAttribute("dto", dto);
		return "admin/system/role/roleAdd";
	}
	
	//删除菜单
	@RequestMapping(value="/toRoleDelete")
	private String toRoleDelete(Model model,HttpServletRequest request,String roleIds) {
		
		List<String> ids = Arrays.asList(roleIds.split(","));  
		Role role = new Role();
		role.setDelFlag(AbleStatus.disabled_0.getCode());
		for(String id:ids){
			role.setId(id);
			roleService.updateRoleById(role);
		}
		
		return "redirect:/role/roleList";
	}
	
	@RequestMapping(value="/toAddMenuList")
	private String toAddMenuList(Model model,String id) {
		//查询菜单树
		List<MenuDto> menus = new ArrayList<>();
		menus = menuService.findRoleMenu();
		model.addAttribute("id", id);
		model.addAttribute("menus", menus);
		return "admin/system/role/addMenuList";
	}
	
	@RequestMapping(value="/roleMenuList")
	@ResponseBody
	private List<ZtreeDto> roleMenuList(Model model,String id) {
		//查询菜单树
		List<MenuDto> menus = new ArrayList<>();
		menus = menuService.findRoleMenu();
		List<ZtreeDto> trees = new ArrayList<>();
		
		for(MenuDto menu:menus){
			ZtreeDto tree = new ZtreeDto();
			tree.setId(menu.getId());
			tree.setName(menu.getMenuName());
			tree.setpId(menu.getParentId());
			if("1".equals(menu.getRoleFlag())){
				tree.setChecked("true");
				tree.setOpen("true");
			}
			trees.add(tree);
		}
		
		return trees;
	}
	
	@RequestMapping(value="/saveRoleMenu")
	@ResponseBody
	private String saveRoleMenu(HttpServletRequest request,String id,String ids) {
		
		String message="1";
		
		roleMenuService.deleteByRoleId(id);
		
		List<String> menuIds = Arrays.asList(ids.split(","));  
		
		for(String menuId:menuIds){
			RoleMenu rm = new RoleMenu();
			rm.setRoleId(id);
			rm.setMenuId(menuId);
			rm.setId(ControllerUtils.getUUID());
			roleMenuService.saveRoleMenu(rm);
		}
		
		return message;
	}
}
