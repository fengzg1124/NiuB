package cn.com.niub.mapper;

import cn.com.niub.domain.RoleMenu;
import cn.com.niub.domain.RoleMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuMapper {
    long countByExample(RoleMenuExample example);

    int deleteByExample(RoleMenuExample example);

    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);

    List<RoleMenu> selectByExample(RoleMenuExample example);

    int updateByExampleSelective(@Param("record") RoleMenu record, @Param("example") RoleMenuExample example);

    int updateByExample(@Param("record") RoleMenu record, @Param("example") RoleMenuExample example);
}