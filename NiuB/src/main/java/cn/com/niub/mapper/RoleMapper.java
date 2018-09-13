package cn.com.niub.mapper;

import cn.com.niub.domain.Role;
import cn.com.niub.domain.RoleExample;
import cn.com.niub.dto.RoleDto;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);
    
    Page<Role> selectByRolePage(Role role);

    Role selectByPrimaryKey(String id);
    
    Page<RoleDto> selectRoleAndUserFlag(@Param(value="userId")String userId);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}