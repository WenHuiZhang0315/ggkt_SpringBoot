package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Course;
import com.atguigu.ggkt.model.vod.CourseDescription;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import com.atguigu.ggkt.vod.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ZRH
 * @since 2022-10-14
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService descriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;
//    点播课程列表
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {
//             获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long teacherId = courseQueryVo.getTeacherId();

//        判断条件，封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(subjectId)){
            wrapper.like("subject_id",subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)){
            wrapper.like("subject_parent_id",subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)){
            wrapper.like("teacher_id",teacherId);
        }

//        分页查询带分页
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);
        long totalCount = pages.getTotal();
        long totalPages = pages.getPages();
        List<Course> records = pages.getRecords();

        records.stream().forEach(item ->{
            this.getNameById(item);
        });
//        封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPages);
        map.put("records",records);
        return map;
    }

//    添加课程基本信息
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
//        添加课程基本信息，操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);

//        添加课程描述，操作course_decsription
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
//        设置课程id
        courseDescription.setId(course.getId());
        descriptionService.save(courseDescription);

        return course.getId();
    }

    //    根据id获取课程信息
    @Override
    public CourseFormVo getCourseInfoById(Long id) {
//        课程基本信息
        Course course = baseMapper.selectById(id);
        if(course == null){
            return null;
        }
//        课程描述信息
        CourseDescription courseDescription = descriptionService.getById(id);

//        封装信息
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);

//        封装描述信息
        if(courseDescription != null){
            courseFormVo.setDescription(courseDescription.getDescription());
        }
        return courseFormVo;
    }

    //    修改课程信息
    @Override
    public void updateCourseId(CourseFormVo courseFormVo) {
//        修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.updateById(course);
        //        修改课程描述信息
        CourseDescription description = new CourseDescription();
        description.setDescription(courseFormVo.getDescription());
        description.setId(course.getId());
        descriptionService.updateById(description);
    }

    //    根据课程id查询发布课程信息
    @Override
    public CoursePublishVo getCoursePublishVo(Long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }

//    删除课程
    @Override
    public void removeCourseId(Long id) {
//        根据课程id删除小结
        videoService.removeVideoByCourseId(id);
//        删除章节
        chapterService.removeChapterByCourseId(id);
//        删除描述
        descriptionService.removeById(id);
//        删除课程
        baseMapper.deleteById(id);
    }

    //添加课程基本信息
    private Course getNameById(Course course) {
//        根据讲师id获取将是名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher!=null){
            String name = teacher.getName();
            course.getParam().put("teacherName",name);
        }
//        根据课程分类id获取课程分类名称
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne!=null){
            subjectOne.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }

        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }
        return course;
    }
}
