package com.znb.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.znb.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;

@Service
public class VodServiceImpl implements VodService {


    /**
     *  上传视频的方法
     * @param file
     * @return
     */
    @Override
    public String uploadAlyiVideo(MultipartFile file) {
        // 上传文件的原始名称
        String fileName = file.getOriginalFilename();
        // 上传之后显示的名称
        String title = fileName.substring(0,fileName.lastIndexOf("."));

        String accessKeyId = "LTAI5tFzFPbw9GHBxzaJq2d5";
        String accessKeySecret = "W4uROGLA2pzEjSokWi3kRTT8ODKmLr";
        // 上传文件输入流
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            videoId = response.getVideoId();
        }
        return videoId;
    }

    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private void testUploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {

    }
}
