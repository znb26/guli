package com.znb.eduservice.controller;


import com.znb.commonutils.R;
import com.znb.eduservice.client.VodClient;
import com.znb.eduservice.entity.EduVideo;
import com.znb.eduservice.service.IEduVideoService;
import com.znb.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private IEduVideoService videoService;

    /**
     * 注入接口
     * @param eduVideo
     * @return
     */
    @Autowired
    private VodClient vodClient;

    // 添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        System.out.println(eduVideo);
        boolean save = videoService.save(eduVideo);
        return R.ok();
    }
    // 删除小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        // 待完善
        // 根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (videoSourceId != null) {
            // 远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if (result.getCode() == 20001) {
                System.out.println("1111111");
                throw new GuliException(20001,"删除视频失败,熔断器");
            }
        }
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
