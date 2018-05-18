package cn.com.niub.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.Page;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuExample.Criteria;
import cn.com.niub.service.MenuService;

@Controller
@RequestMapping(value="/menu")
public class MenuController {

	@Autowired
	private MenuService menuService;
	
	public String menuList(Model model,Menu menu,HttpServletRequest request){
		
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andMenuNameLike("%"+"%");
		
		Page<Menu> menus = menuService.findMenus(example, 1, 3);
		
		
		return "admin/system/menu/menuList";
	}
}
