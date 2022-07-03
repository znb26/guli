package com.znb.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.commonutils.R;
import com.znb.eduservice.entity.EduCourse;
import com.znb.eduservice.entity.EduTeacher;
import com.znb.eduservice.service.IEduCourseService;
import com.znb.eduservice.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private IEduCourseService eduCourseService;

    @Autowired
    private IEduTeacherService eduTeacherService;

    /**
     * 查询前8条热门课程，查询前4条名师
     */
    @GetMapping("/index")
    public R index(){
        // 查询前8条热门课程
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> eduList = eduCourseService.list(wrapperCourse);

        // 查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = eduTeacherService.list(wrapperTeacher);

        return R.ok().data("eduList",eduList).data("teacherList",teacherList);
    }
}
