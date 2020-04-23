package com.lzy.springbootshiro.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 第三部：ShiroFilterBean  Shiro过滤对象
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        // 添加 Shiro 的内置过滤器
        /*
            anon: 无需认证就可以访问
            authc：必须认证了才可以访问
            user：必须拥有 记住我 才能用
            perms：拥有对某个资源的权限才能访问
            role：拥有某个权限才能访问
        */
        // 拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 授权, 正常情况下，没有授权会跳转到未授权页面
        filterMap.put("/user/update","perms[user:update]");
        filterMap.put("/user/add","perms[user:add]");


        filterMap.put("/user/*","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        // 设置登录的请求
        bean.setLoginUrl("/toLogin");

        // 未授权页面
        bean.setUnauthorizedUrl("/noAuth");

        return bean;
    }

    // 第二部：DafaultWebSecurityManager 默认web安全对象
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    // 第一步：创建 realm 对象，需要自定义
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }


    // 整合 ShiroDialect： 用来整合 shiro thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


}
