package com.soft1851.springboot.aop.aspect;

import com.soft1851.springboot.aop.annotation.AuthToken;
import com.soft1851.springboot.aop.common.ResponseBean;
import com.soft1851.springboot.aop.common.ResultCode;
import com.soft1851.springboot.aop.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class AuthTokenAspect {

    @Resource
    private UserMapper userMapper;

    @Pointcut("@annotation(authToken)")
    public void doAuthToken(AuthToken authToken){

    }

    @Around(value = "doAuthToken(authToken)",argNames = "pjp,authToken")
    public Object doAround(ProceedingJoinPoint pjp,AuthToken authToken) throws Throwable{
        ServletRequestAttributes sra=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert sra !=null;
        HttpServletRequest request = sra.getRequest();
        //取得注解中的role_name的值
        String[] roleNames = authToken.role_name();
        if(roleNames.length<=1){
            //只需要认证（登录）
            String id = request.getHeader("id");
            //如果id为空，证明用户没有登录
            if(userMapper.selectAdminById(id) !=null){
                return pjp.proceed();
            }
            return ResponseBean.failure(ResultCode.USER_NOT_SIGN_IN);
        }else {
            //验证身份
            String role = userMapper.selectAdminById(request.getHeader("id")).getRole();
            log.info(role);
            //遍历roleNames数组，匹配role
//            for(String roleName : roleNames){
                if("1".equals(role)){
                    //身份匹配成功，调用目标方法
                    return pjp.proceed();
                }
//            }
            return  ResponseBean.failure(ResultCode.USER_NO_AUTH);
        }
        //取得请求头中的值

    }
}
