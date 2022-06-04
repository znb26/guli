package com.znb.eduservice.mapper;

import com.znb.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.znb.eduservice.entity.frontvo.CourseWebVo;
import com.znb.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 课程最终发布
     * @param courseId
     * @return
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     *     2 课程详情的方法
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
