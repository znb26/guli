package com.znb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.eduservice.entity.EduChapter;
import com.znb.eduservice.entity.EduVideo;
import com.znb.eduservice.entity.chapter.ChapterVo;
import com.znb.eduservice.entity.chapter.VideoVo;
import com.znb.eduservice.mapper.EduChapterMapper;
import com.znb.eduservice.service.IEduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znb.eduservice.service.IEduVideoService;
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
 * @author znb
 * @since 2022-05-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements IEduChapterService {

    @Autowired
    private IEduVideoService eduVideoService;

    /**
     * 课程大纲列表,根据课程id查询
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 根据课程id查询课程里的所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);
        // 根据课程id查询课程里的所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(wrapperVideo);

        //创建list集合用于最终返回的数据
        List<ChapterVo> finalList = new ArrayList<>();
        // 遍历查询出来的章节list集合 封装
        for (EduChapter eduChapter : eduChapterList) {
            ChapterVo chapterVo = new ChapterVo();
            // 把第一个参数的值 赋值到第二个参数
            BeanUtils.copyProperties(eduChapter,chapterVo);
            // 放入要返回的数据中
            finalList.add(chapterVo);

            // 创建集合封装 章节中的小节
            List<VideoVo> videoList = new ArrayList<>();
            // 遍历查询出来的小节list集合 封装
            for (EduVideo eduVideo : eduVideoList) {
                // 判断 小节中的chapterId和章节id是否一致
                if (eduVideo.getChapterId().equals(eduChapter.getId())) {
                    // 进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    // 放到集合中去
                    videoList.add(videoVo);
                }
            }
            // 把封装之后的小节list 放到章节对象中
            chapterVo.setChildren(videoList);
        }
        return finalList;
    }
}
