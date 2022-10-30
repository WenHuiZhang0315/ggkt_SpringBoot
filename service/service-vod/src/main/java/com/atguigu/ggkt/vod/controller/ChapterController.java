package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.Result.Result;
import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vod.service.ChapterService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author ZRH
 * @since 2022-10-14
 */
@RestController
@RequestMapping("/admin/vod/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

//    1.大纲列表
    @ApiOperation("大纲列表")
    @GetMapping("getNestedTreeList/{courseid}")
    public Result getTreeList(@PathVariable Long courseid){
        List<ChapterVo> list = chapterService.getTreelist(courseid);
        return Result.ok(list);
    }
//    2.添加章节
    @ApiOperation("添加章节")
    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return Result.ok(null);
    }
//    3.修改--根据i的查询
    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }
//    4.修改--在最终修改
    @ApiOperation("修改")
    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return Result.ok(null);
    }

//    5.删除
    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@RequestBody Long id){
        chapterService.removeById(id);
        return Result.ok(null);
    }
}

