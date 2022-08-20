package com.znb.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.znb.servicebase.exceptionhandler.GuliException;
import com.znb.vod.service.VodService;
import com.znb.vod.utlis.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

        String accessKeyId = "";
        String accessKeySecret = "";
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
        //System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            //System.out.print("VideoId=" + response.getVideoId() + "\n");
            //System.out.print("ErrorCode=" + response.getCode() + "\n");
            //System.out.print("ErrorMessage=" + response.getMessage() + "\n");
            videoId = response.getVideoId();
        }
        return videoId;
    }

    /**
     * 删除多个视频的方法
     * 参数 多个视频id
     */
    @Override
    public void removeMoreAlyVideo(List<String> videoIdList) {
        try {
            // 初始化对象
            String accessKeyId = "LTAI5tFzFPbw9GHBxzaJq2d5";
            String accessKeySecret = "W4uROGLA2pzEjSokWi3kRTT8ODKmLr";
            DefaultAcsClient client = InitVodClient.initVodClient(accessKeyId,accessKeySecret);
            // 创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            String videoIds = StringUtils.join(videoIdList.toArray(), ",");
            System.out.println(videoIds);

            // 向request中设置id
            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }


    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("55");

        String join = StringUtils.join(list.toArray(), ",");
        System.out.println(join);

    }
}
