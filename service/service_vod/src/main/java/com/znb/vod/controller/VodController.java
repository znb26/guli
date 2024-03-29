package com.znb.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.znb.commonutils.R;
import com.znb.servicebase.exceptionhandler.GuliException;
import com.znb.vod.service.VodService;
import com.znb.vod.utlis.InitVodClient;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    /**
     * 上传视频的方法
     */
    @PostMapping(value = "/uploadAlyiVideo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "上传视频", notes = "上传视频", httpMethod = "POST")
    public R uploadAlyiVideo(@ApiParam(value = "课程视频", required = true) @RequestPart("file") MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传视频的id值
        String videoId = vodService.uploadAlyiVideo(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     * 根据视频id删除视频
     */
    @DeleteMapping("/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            // 初始化对象
            String accessKeyId = "";
            String accessKeySecret = "";
            DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId,accessKeySecret);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request中设置id
            request.setVideoIds(id);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }

    /**
     * 删除多个视频的方法
     * 参数 多个视频id
     */
    @DeleteMapping("/delete-batch")
    public R deleteBath(@RequestParam("videoIdList") List<String> videoIdList){
        System.out.println(videoIdList);
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频id获取视频播放凭证
     */
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            // 创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient("", "");
            // 创建request 和 response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request中设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        }catch (Exception e) {
            throw new GuliException(20001,"获取视频播放凭证失败");
        }

    }

}
