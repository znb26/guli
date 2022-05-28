package com.znb.vod.controller;

import com.znb.commonutils.R;
import com.znb.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
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
}
