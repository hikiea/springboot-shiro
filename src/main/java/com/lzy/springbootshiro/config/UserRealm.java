package com.lzy.springbootshiro.config;

import com.lzy.springbootshiro.Service.UserService;
import com.lzy.springbootshiro.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

// 自定义 UserRealm ，extends AuthorizingRealm
public class UserRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了 授权=>doGetAuthorizationInfo");

        /* 授权操作 */
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");

        // 拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        User currentUser = (User) subject.getPrincipal();  // 拿到User对象

        // 设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());

        // return info
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证 => doGetAuthenticationInfo");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
       // 连接真实的数据库
        User user = userService.queryByName(userToken.getUsername());
        if (user==null){    // 没有这个人
           return null;   // UnknownAccountException
        }

        Subject currentSubject = SecurityUtils.getSubject();
        Session session = currentSubject.getSession();
        session.setAttribute("loginUser",user);

        // 密码认证，shiro做~    第一个参数是传参，把参数return到授权操作
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }
}
