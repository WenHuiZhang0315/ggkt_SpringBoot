package com.atguigu.ggkt.exception;

import com.atguigu.ggkt.Result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author shkstart
 * @create 2022-10-11 11:32
 */
@ControllerAdvice
public class GlobalExceptionHandler {

//    全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail(null);
    }
}
