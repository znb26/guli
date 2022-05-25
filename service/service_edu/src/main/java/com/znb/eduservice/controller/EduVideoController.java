package com.znb.eduservice.controller;


import com.znb.commonutils.R;
import com.znb.eduservice.entity.EduVideo;
import com.znb.eduservice.service.IEduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private IEduVideoService videoService;

    // 添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        boolean save = videoService.save(eduVideo);
        return R.ok();
    }
    // 删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        // 待完善
        boolean b = videoService.removeById(id);
        return R.ok();
    }

    // 修改小节
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }
    // 根据id查询小节
    @GetMapping("/getVideoById/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("eduVideo",eduVideo);
    }


}
