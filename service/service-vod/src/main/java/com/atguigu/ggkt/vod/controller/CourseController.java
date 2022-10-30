package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.Result.Result;
import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.service.CourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ZRH
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/admin/vod/course")
public class CourseController {

    @Autowired
    private CourseService courseService;


//    删除课程接口
    @ApiOperation("删除课程接口")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        courseService.removeCourseId(id);
        return Result.ok(null);
    }
//    课程添加
    @ApiOperation("课程添加")
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo){
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }


//    点播课程列表
    @ApiOperation("点播课程")
    @GetMapping("{page}/{limit}")
    public Result courseList(@PathVariable Long page,
                             @PathVariable Long limit,
                             CourseQueryVo courseQueryVo){
        Page<Course> pageParam = new Page<>(page, limit);
        Map <String,Object>map = courseService.findPageCourse(pageParam,courseQueryVo);
        return  Result.ok(map);
    }

//    根据id获取课程信息
        @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }
//    修改课程信息
    @PostMapping("update")
    public Result update(@RequestBody CourseFormVo courseFormVo){
        courseService.updateCourseId(courseFormVo);
        return Result.ok(courseFormVo.getId());
    }

//    根据课程id查询发布课程信息
    @ApiOperation("id查询发布课程信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id){
       CoursePublishVo coursePublishVo =  courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    //  课程最终发布
    @ApiOperation("课程最终发布")
    @PutMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable Long id){
        courseService.publishCourse(id);
        return Result.ok(null);
    }
}

