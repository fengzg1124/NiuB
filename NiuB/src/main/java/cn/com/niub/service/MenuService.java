package cn.com.niub.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuExample.Criteria;
import cn.com.niub.dto.MenuDto;
import cn.com.niub.mapper.MenuMapper;
import cn.com.niub.utils.AbleStatus;

@Service
public class MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	//按照条件查询
	public Page<Menu> findMenus(MenuDto dto,int pageNo,int pageSize){
		PageHelper.startPage(pageNo, pageSize);
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(dto.getMenuName())){
			criteria.andMenuNameLike("%"+dto.getMenuName()+"%");
		}
		if(StringUtils.isNotBlank(dto.getMenuUrl())){
			criteria.andMenuUrlLike("%"+dto.getMenuUrl()+"%");
		}
		if(StringUtils.isNotBlank(dto.getMark())){
			criteria.andMarkEqualTo(dto.getMark());
		}
		criteria.andDelFlagEqualTo(AbleStatus.usable_1.getCode());
		return menuMapper.selectByExamplePage(example);
	}
	//按主键id查找
	public Menu findMenuById(String id){
		Menu menu = menuMapper.selectByPrimaryKey(id);
		return menu;
	}
	//查询全部
	public List<Menu> findAll(){
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		//未删除
		criteria.andDelFlagEqualTo(AbleStatus.usable_1.getCode());
		criteria.andFlagEqualTo(AbleStatus.usable_1.getCode());
		return menuMapper.selectByMenu(example);
	}
	
	//新增菜单
	public void saveMenu(Menu menu){
		menuMapper.insertMenu(menu);
	}
	
	//根据id修改菜单
	public void updateMenu(Menu menu,String id){
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		menuMapper.updateByMenuSelective(menu,example);
	}
	//根据id修改菜单(批量)
	public void updateMenus(Menu menu,List<String> ids){
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		menuMapper.updateByMenuSelective(menu,example);
	}
	//根据id删除菜单
	public void deleteMenu(String id){
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		menuMapper.deleteByExample(example);
	}
	//根据id删除菜单(批量)
	public void deleteMenus(List<String> ids){
		MenuExample example = new MenuExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(ids);
		menuMapper.deleteByExample(example);
	}
	
	//查询全部
	public List<MenuDto> findRoleMenu(){
		return menuMapper.selectMenuAndRoleFlag();
	}
	//查询用户关联的菜单
	public List<MenuDto> findByUserId(String userId){
		return menuMapper.selectByUserId(userId);
	}
}
