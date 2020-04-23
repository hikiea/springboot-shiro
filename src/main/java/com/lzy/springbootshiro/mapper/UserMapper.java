package com.lzy.springbootshiro.mapper;


import com.lzy.springbootshiro.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    User queryByUsername(String username);

}
