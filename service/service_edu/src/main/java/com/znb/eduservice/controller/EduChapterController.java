package com.znb.eduservice.controller;


import com.znb.commonutils.R;
import com.znb.eduservice.entity.EduChapter;
import com.znb.eduservice.entity.chapter.ChapterVo;
import com.znb.eduservice.service.IEduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "课程章节小节信息", tags = "课程章节小节信息")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private IEduChapterService chapterService;

    /**
     * 课程大纲列表,根据课程id查询
     */
    @ApiOperation(value = "课程大纲列表,根据课程id查询")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     * 添加章节
     */
    @ApiOperation(value = "添加章节")
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 根据id查询章节内容
     */
    @ApiOperation(value = "根据id查询章节内容")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = chapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }

    /**
     * 修改章节
     */
    @ApiOperation(value = "修改章节")
    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节
     */
    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean b = chapterService.deleteChapter(chapterId);
        if (b) {
            return R.ok();
        }
        return R.error();
    }



}
