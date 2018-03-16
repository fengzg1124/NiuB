package cn.com.niub.mapper;

import cn.com.niub.domain.RoleUser;
import cn.com.niub.domain.RoleUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleUserMapper {
    long countByExample(RoleUserExample example);

    int deleteByExample(RoleUserExample example);

    int insert(RoleUser record);

    int insertSelective(RoleUser record);

    List<RoleUser> selectByExample(RoleUserExample example);

    int updateByExampleSelective(@Param("record") RoleUser record, @Param("example") RoleUserExample example);

    int updateByExample(@Param("record") RoleUser record, @Param("example") RoleUserExample example);
}