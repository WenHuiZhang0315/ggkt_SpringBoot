package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.Result.Result;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author ZRH
 * @since 2022-10-10
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping("/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //    查询所有讲师
//    http://localhost:8301/admin/vod/teacher/findAll
    @ApiOperation("查询所有讲师")
    @GetMapping("findAll")
    public Result findAllTeacher(){
        List<Teacher> list = teacherService.list();
        return Result.ok(list);
    }

//    逻辑删除讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("remove/{id}")
    public Result removeTeacher(@ApiParam(name="id",value = "ID",required = true)
                                     @PathVariable Long id){
        boolean isSuccess =teacherService.removeById(id);
        if(isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

//    条件查询带分页
    @ApiOperation("条件分页查询")
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result findPage(@PathVariable long current,//当前页
                           @PathVariable long limit,//每页记录数
                           @RequestBody(required = false) TeacherQueryVo teacherQueryVo){//条件部分
//@RequestBody表示接收数据，用json格式，只能与PostMapping结合使用
        Page<Teacher> pageParm = new Page<>(current, limit);
//        判断TeacherQueryVo对象是否为空
        if(teacherQueryVo == null){//查询全部值
            IPage<Teacher> page = teacherService.page(pageParm, null);
            return Result.ok(page);
        }else{
//            获取条件值进行非空判断
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();

//            进行非空判断封装
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(name)){
              wrapper.like("name",name);
            }
            if(!StringUtils.isEmpty(level)){
              wrapper.eq("level",level);
            }
            if(!StringUtils.isEmpty(joinDateBegin)){
              wrapper.ge("join_date",joinDateBegin);//大区等于
            }
            if(!StringUtils.isEmpty(joinDateEnd)){
              wrapper.le("join_date",joinDateEnd);//小于等于
            }

//            调用方法分页查询
            IPage<Teacher> page = teacherService.page(pageParm, wrapper);
            return Result.ok(page);
        }
    }

//    添加讲师
    @ApiOperation("添加讲师")
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.save(teacher);
        if(isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }

//    修改--根据id查询
    @ApiOperation("根据id查询")
    @GetMapping("getTeacher/{id}")
    public Result getTeacher(@PathVariable Long id){
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

//    修改--最终修改
    @ApiOperation("最终修改")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher){
        boolean isSuccess = teacherService.updateById(teacher);
        if(isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }


//    批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("removeBatch")
    public Result removeBacth(@RequestBody List<Long> idList){
        boolean isSuccess = teacherService.removeByIds(idList);
        if(isSuccess){
            return Result.ok(null);
        }else {
            return Result.fail(null);
        }
    }
}

