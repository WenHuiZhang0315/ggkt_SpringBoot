package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.Result.Result;
import com.atguigu.ggkt.model.user.SysUser;
import com.atguigu.ggkt.util.JwtHelper;
import com.atguigu.ggkt.vo.acl.LoginVo;
import com.atguigu.ggkt.vod.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shkstart
 * @create 2022-10-11 14:19
 */
@RestController
@RequestMapping("/admin/vod/user")
public class UserLoginController {

    @Autowired
    private SysUserService sysUserService;
    //    登录
    @PostMapping("login")
    public Result login(@RequestBody LoginVo LoginVo) {
//        根据用户名称查询
        SysUser sysUser = sysUserService.getUserInfoByUserName(LoginVo.getUsername());
//        如果查询为空
        if (sysUser==null){
            throw new RuntimeException("没有该用户");
        }
//        判断密码是否一致
        String password = LoginVo.getPassword();
//        String encrypt = MD5.encrypt(password);
        if(!sysUser.getPassword().equals(password)){
            throw new RuntimeException("密码有误");
        }
//        判断用户是否可用
        if(sysUser.getStatus().intValue() == 0){
            throw new RuntimeException("用户已被禁用");
        }
//        根据username和userid生成token字符串通过map返回
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        return Result.ok(map);
    }

    //    info
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
//        获取token
        String token = request.getHeader("token");
//        获取用户名称
        String username = JwtHelper.getUsername(token);
//        根据用户名称获取信息(基本信息和权限信息)
        Map<String,Object> map = sysUserService.getUserInfo(username);
//        通过map返回
        return Result.ok(map);
    }


}
