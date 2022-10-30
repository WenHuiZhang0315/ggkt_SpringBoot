package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.model.user.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ZRH
 * @since 2022-10-27
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserInfoByUserName(String username);

    Map<String, Object> getUserInfo(String username);
}
