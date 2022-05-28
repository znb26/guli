package com.znb.eduservice.service;

import com.znb.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
public interface IEduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    /**
     * 修改课程信息的接口
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程确认信息
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 删除课程
     * @param courseId 课程id
     * @return
     */
    void removeCourse(String courseId);
}
