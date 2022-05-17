package com.znb.oss.controller;

import com.znb.commonutils.R;
import com.znb.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像的方法
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "上传图片", notes = "上传图片", httpMethod = "POST")
    public R uploadOssFile(@ApiParam(value = "讲师图片", required = true) @RequestPart("file") MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

    /**
     * @PutMapping("/api/file",
     *        consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     * public String upload(
     *     @RequestPart("file")
     *             MultipartFile file
     * ) {}
     * ————————————————
     * 版权声明：本文为CSDN博主「路过君_P」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/zhoudingding/article/details/121392538
     */

}