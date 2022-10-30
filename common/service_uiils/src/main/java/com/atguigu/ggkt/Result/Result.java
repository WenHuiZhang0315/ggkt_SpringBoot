package com.atguigu.ggkt.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shkstart
 * @create 2022-10-10 22:11
 * 返回统一结果类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;//状态码

    private String message;//返回成功信息

    private T data;//返回数据

//    成功的方法,没有data
//    public static<T> Result<T> ok(){
//        Result<T> result = new Result<>();
//        result.setCode(200);
//        result.setMessage("成功");
//        return result;
//    }
//
////    失败的方法
//    public static<T> Result<T> fail(){
//        Result<T> result = new Result<>();
//        result.setCode(201);
//        result.setMessage("失败");
//        return result;
//    }
//    成功的方法,有data
    public static<T> Result<T> ok(T data){
        Result<T> result = new Result<>();
        if(data!=null){
            result.setData(data);
        }
        result.setCode(20000);
        result.setMessage("成功");
        return result;
    }

//    失败的方法
    public static<T> Result<T> fail(T data){
        Result<T> result = new Result<>();
        if(data!=null){
            result.setData(data);
        }
        result.setCode(20001);
        result.setMessage("失败");
        return result;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result<T> code(String message){
        this.setMessage(message);
        return this;
    }


}
