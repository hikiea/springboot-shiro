package com.lzy.springbootshiro.Service;

import com.lzy.springbootshiro.mapper.UserMapper;
import com.lzy.springbootshiro.pojo.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryByName(String username){
        return userMapper.queryByUsername(username);
    }

}
