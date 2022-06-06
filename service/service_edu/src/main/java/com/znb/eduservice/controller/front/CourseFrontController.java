package com.znb.eduservice.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.commonutils.R;
import com.znb.commonutils.ordervo.CourseWebVoOrder;
import com.znb.eduservice.entity.EduCourse;
import com.znb.eduservice.entity.chapter.ChapterVo;
import com.znb.eduservice.entity.frontvo.CourseFrontVo;
import com.znb.eduservice.entity.frontvo.CourseWebVo;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.service.IEduChapterService;
import com.znb.eduservice.service.IEduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    private IEduCourseService courseService;

    @Autowired
    private IEduChapterService chapterService;

    /**
     * 1 条件查询带分页查询课程
     */
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        System.out.println(map);
        return R.ok().data(map);
    }

    /**
     * 2 课程详情的方法
     */
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);

        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    /**
     * 根据课程id查询课程信息
     * @param id
     * @return
     */
    @PostMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id){
        CourseInfoVo courseInfo = courseService.getCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}












