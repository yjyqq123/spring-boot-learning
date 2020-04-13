package com.soft1851.springboot.aop.config;


import com.soft1851.springboot.aop.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;


@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandleConfig {
    /**
     * 顶级异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handlerException(Exception e){
        //annotation抛出的异常
        if(e instanceof UndeclaredThrowableException) {
            e = (Exception) ((UndeclaredThrowableException)e).getUndeclaredThrowable();
        }
        log.error("[handleException]: ",e);
        return Result.set(false,e.getMessage());
    }
}
