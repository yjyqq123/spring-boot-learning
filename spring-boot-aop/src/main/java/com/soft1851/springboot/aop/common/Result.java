package com.soft1851.springboot.aop.common;

import lombok.Data;


@Data
public class Result<T> {
    /**
     *  success 是否成功返回结果 成功true 失败false
     */
    private boolean success;
    /**
     *  data 返回的结果数据
     */
    private T data;

    public static <T> Result<T> set(boolean success,T data){
        Result<T> result=new Result<>();
        result.setSuccess(success);
        result.setData(data);
        return  result;
    }
}
