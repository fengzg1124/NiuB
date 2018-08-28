package cn.com.niub.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuWithBLOBs;
import cn.com.niub.dto.MenuDto;

public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int insert(MenuWithBLOBs record);
    
    int insertMenu(Menu record);

    int insertSelective(MenuWithBLOBs record);

    List<MenuWithBLOBs> selectByExampleWithBLOBs(MenuExample example);
    
    List<Menu> selectAll();

    Page<Menu> selectByExamplePage(MenuExample example);
    
    List<Menu> selectByMenu(MenuExample example);
    
    List<MenuDto> selectMenuAndRoleFlag();
    
    Menu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MenuWithBLOBs record, @Param("example") MenuExample example);
    
    int updateByMenuSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExampleWithBLOBs(@Param("record") MenuWithBLOBs record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);
}