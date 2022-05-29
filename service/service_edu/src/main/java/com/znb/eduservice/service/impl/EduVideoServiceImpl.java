package com.znb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.eduservice.client.VodClient;
import com.znb.eduservice.entity.EduVideo;
import com.znb.eduservice.mapper.EduVideoMapper;
import com.znb.eduservice.service.IEduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements IEduVideoService {


    /**
     * 注入 vodClient
     */
    @Autowired
    private VodClient vodClient;

    /**
     * 根据课程id删除小节 TODO
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {

        // 根据课程id 查询出课程中所有的视频id
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        // 把List<EduVideo> -> List<String>
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideoList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        if (videoIds.size() > 0) {
            System.out.println(videoIds);
            // 根据多个视频id删除
            vodClient.deleteBath(videoIds);

        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
