package com.soft1851.springboot.aop.controller;

import com.soft1851.springboot.aop.annotation.AuthToken;
import com.soft1851.springboot.aop.annotation.ControllerWebLog;
import com.soft1851.springboot.aop.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

@RestController
@Slf4j
public class UserController {
    @Resource
    private UserMapper userMapper;

    /**
     * 无需任何校验，不用加注解
     * @param name
     * @return
     */
    @GetMapping("hello")
    @ControllerWebLog(name = "getHello", isSaved = true)
    public String hello(String name){
        log.info("hello()方法无需鉴权，也无需认证，当前用户名："+ name);
        return "hello方法访问成功";
    }

    /**
     * 需要认证，此时该方法需要加注解，但是不需要传角色
     * @param
     * @return
     */
    @GetMapping("user")
    @AuthToken
    @ControllerWebLog(name = "getUser", isSaved = true)
    public String user(String id) throws SQLException {
        String name = userMapper.selectAdminById(id).getName();
        log.info("user()方法需要认证，当前用户名："+ name);
        return  "user()方法访问成功";
    }

    /**
     * 需要鉴权，此时该方法需要加注解，需要传角色
     * 角色可以穿多个
     * 如果需要更复杂的逻辑操作，建议使用Spring Security、Apache Shiro等安全框架
     * @param
     * @return
     */
    @GetMapping("admin")
    @AuthToken(role_name = {"0","1"})
    @ControllerWebLog(name = "getAdmin", isSaved = true)
    public String admin(String id) throws SQLException {
        String name = userMapper.selectAdminById(id).getName();
        log.info("admin()方法需要鉴权，当前用户名："+ name);
        return "admin()方法访问成功";
    }
}
