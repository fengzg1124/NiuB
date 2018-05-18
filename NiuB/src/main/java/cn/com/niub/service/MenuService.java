package cn.com.niub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.mapper.MenuMapper;

@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	//按照条件查询
	public Page<Menu> findMenus(MenuExample example,int pageNo,int pageSize){
		 PageHelper.startPage(pageNo, pageSize);
		 return menuMapper.selectByExamplePage(example);
	}
	//按主键id查找
	public Menu findMenuById(String id){
		Menu menu = menuMapper.selectByPrimaryKey(id);
		return menu;
	}
	//新增菜单
	public void saveMenu(Menu menu){
		menuMapper.insertMenu(menu);
	}
}
