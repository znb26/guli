package com.znb.eduservice.controller;


import com.znb.commonutils.R;
import com.znb.eduservice.entity.chapter.ChapterVo;
import com.znb.eduservice.service.IEduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private IEduChapterService chapterService;

    /**
     * 课程大纲列表,根据课程id查询
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }



}
