package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Chapter;
import com.atguigu.ggkt.model.vod.Video;
import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.vod.mapper.ChapterMapper;
import com.atguigu.ggkt.vod.service.ChapterService;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author ZRH
 * @since 2022-10-14
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {


    @Autowired
    private VideoService videoService;

//    大纲列表
    @Override
    public List<ChapterVo> getTreelist(Long courseid) {
//        利于封装定义最终结合
        List<ChapterVo> FinallList = new ArrayList();
//        1.根据课程id获取所有章节
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseid);
        List<Chapter>chapterList = baseMapper.selectList(chapterQueryWrapper);
//        2.根据课程id获取所有小节
        LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        videoLambdaQueryWrapper.eq(Video::getCourseId,courseid);
        List<Video> videoList = videoService.list(videoLambdaQueryWrapper);
//        3.封装章节
//        遍历所有章节
        for (int i = 0; i < chapterList.size(); i++) {
//            得到每个章节
            Chapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
//            将得到的每个章节放到最终对象里
            FinallList.add(chapterVo);

            //        4.封装所有小节
            List<VideoVo> videoVoList = new ArrayList<>();
            for (Video video:
                 videoList) {
//                判断小节属于哪个章节
                if(chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVoList.add(videoVo);
                }
            }

//            把所有章节里面的小节集合放到每个章节中
            chapterVo.setChildren(videoVoList);
        }
        return FinallList;
    }

    //        删除章节
    @Override
    public void removeChapterByCourseId(Long id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        baseMapper.delete(wrapper);
    }
}
