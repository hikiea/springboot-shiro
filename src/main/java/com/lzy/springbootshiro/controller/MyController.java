package com.lzy.springbootshiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model){
        model.addAttribute("msg","hello,Shiro!");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add(){
        return "user/add";
    }

    @RequestMapping("/user/update")
    public String update(){
        return "user/update";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/login")
    public String login(String username,String password,Model model){
        // 获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try{
            subject.login(token); // 执行登录的方法，如果没有异常就ok
            return "index";
        }catch (UnknownAccountException e){    //用户名不存在
            model.addAttribute("msg","用户名不存在");
            return "login";
        }catch (IncorrectCredentialsException e){   // 密码错误
            model.addAttribute("msg","密码错误");
            return "login";
        }
    }

    @RequestMapping("/noAuth")
    @ResponseBody
    public String unauthorized(){
        return "未经授权无法访问此页面";
    }


    @RequestMapping("/logout")
    public String logOut(Model model){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        model.addAttribute("msg","已经退出登录");
        return "login";
    }
}
