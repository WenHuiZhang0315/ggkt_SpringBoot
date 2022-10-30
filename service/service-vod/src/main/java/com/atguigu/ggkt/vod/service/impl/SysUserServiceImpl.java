package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.user.SysUser;
import com.atguigu.ggkt.vod.mapper.SysUserMapper;
import com.atguigu.ggkt.vod.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ZRH
 * @since 2022-10-27
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getUserInfoByUserName(String username) {
        QueryWrapper<SysUser>wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
//    根据用户名称查询出基本信息
        SysUser userName = this.getUserInfoByUserName(username);
        Map<String,Object> result = new HashMap<>();
        result.put("username",username);
        result.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles","[\"admin\"]");
        return null;
    }
}
