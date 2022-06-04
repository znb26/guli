package com.znb.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.commonutils.R;
import com.znb.eduservice.entity.EduCourse;
import com.znb.eduservice.entity.EduTeacher;
import com.znb.eduservice.service.IEduCourseService;
import com.znb.eduservice.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 查询前端页面需要显示的教师信息
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private IEduTeacherService teacherService;

    @Autowired
    private IEduCourseService courseService;

    /**
     * 分页查询讲师信息
     */
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable long page,
                                 @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        Map<String,Object> map = teacherService.getTeacherFrontList(pageTeacher);
        // 返回所有数据
        return R.ok().data(map);
    }

    /**
     * 讲师详情查询
     */
    @GetMapping("/getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        // 根据讲师ID查询教师的基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        // 根据讲师ID查询讲师的课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }



}
