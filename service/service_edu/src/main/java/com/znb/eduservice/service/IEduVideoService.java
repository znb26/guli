package com.znb.eduservice.service;

import com.znb.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
public interface IEduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     * @param courseId
     */
    void removeVideoByCourseId(String courseId);
}
