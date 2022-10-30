package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.service.VodServise;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author ZRH
 * @since 2022-10-14
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private VodServise vodServise;

//    根据课程id小节以及视频
    @Override
    public void removeVideoByCourseId(Long id) {
        //        根据id删除小节
        QueryWrapper <Video>wrapper = new QueryWrapper();
        wrapper.eq("course_id",id);
//        根据课程id得到所有小节
        List <Video> videoList = baseMapper.selectList(wrapper);
//        遍历所有小节集合，获取视频id
        for (Video video:
             videoList) {
            //       判断是否为空进行删除
            String courseId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(courseId)){
                vodServise.removeVideo(courseId);
            }
        }
//        根据课程id删除所有小节
        baseMapper.delete(wrapper);
    }

//删除小节是顺带删除视频
    @Override
    public void removeVideoById(Long id) {
//        查询小节id
        Video video = baseMapper.selectById(id);
//        获取video里面的视频id
        String videoSourceId = video.getVideoSourceId();
//    判断是否为空
        if(!StringUtils.isEmpty(videoSourceId)){
//            不为空删除
            vodServise.removeVideo(videoSourceId);
        }
//        根据id删除小节
        baseMapper.deleteById(id);
    }

}
