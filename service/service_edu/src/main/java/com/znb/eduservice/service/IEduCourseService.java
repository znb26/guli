package com.znb.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.znb.eduservice.entity.frontvo.CourseFrontVo;
import com.znb.eduservice.entity.frontvo.CourseWebVo;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

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

    /**
     * 1 条件查询带分页查询课程
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    /**
     * 2 课程详情的方法
     * @param courseId
     * @return
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
