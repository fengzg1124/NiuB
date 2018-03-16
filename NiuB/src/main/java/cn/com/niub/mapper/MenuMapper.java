package cn.com.niub.mapper;

import cn.com.niub.domain.Menu;
import cn.com.niub.domain.MenuExample;
import cn.com.niub.domain.MenuWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int insert(MenuWithBLOBs record);

    int insertSelective(MenuWithBLOBs record);

    List<MenuWithBLOBs> selectByExampleWithBLOBs(MenuExample example);

    List<Menu> selectByExample(MenuExample example);

    int updateByExampleSelective(@Param("record") MenuWithBLOBs record, @Param("example") MenuExample example);

    int updateByExampleWithBLOBs(@Param("record") MenuWithBLOBs record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);
}