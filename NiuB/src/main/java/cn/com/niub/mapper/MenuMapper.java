package cn.com.niub.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuWithBLOBs;

public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int insert(MenuWithBLOBs record);
    
    int insertMenu(Menu record);

    int insertSelective(MenuWithBLOBs record);

    List<MenuWithBLOBs> selectByExampleWithBLOBs(MenuExample example);

    Page<Menu> selectByExamplePage(MenuExample example);
    
    Menu selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") MenuWithBLOBs record, @Param("example") MenuExample example);

    int updateByExampleWithBLOBs(@Param("record") MenuWithBLOBs record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);
}