package cn.com.niub.mapper;

import cn.com.niub.domain.User;
import cn.com.niub.domain.UserExample;
import cn.com.niub.dto.UserDto;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);
    
    Page<User> selectByExamplePage(UserExample example);

    User selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}