package com.znb.eduservice.controller;


import com.znb.commonutils.R;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.service.IEduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private IEduCourseService courseService;

    /**
     * 添加课程基本信息
     */
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        // 需要返回添加的课程id为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

}
