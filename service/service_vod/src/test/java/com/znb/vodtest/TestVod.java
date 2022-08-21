package com.znb.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {

    public static void main(String[] args) throws ClientException {
        // 上传视频
        // 视频文件上传
        // 视频标题(必选)
        String title = "测试标题";
        // 1.本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        // 2.网络流上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 3.流式上传时，文件名称为源文件名，如文件名称.mp4(必选)。
        // 任何上传方式文件名必须包含扩展名
        String fileName = "F:\\其他视频\\myvideo.mp4";

        String accessKeyId = "";

        String accessKeySecret = "";

        // 本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);
    }

    /**
     * 本地文件上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     */
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     * 根据视频id获取视频播放凭证
     */
    public static void getPlayAuth() throws ClientException {
        // 1. 根据视频id获取视频播放凭证
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("", "");
        // 创建获取视频凭证的 request 和 response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        // 向 request对象中设置视频的id值
        request.setVideoId("d3f2c65865df4463b55faed3ebe31059");
        // 调用初始化对象的方法传递 request 获取播放凭证
        response = client.getAcsResponse(request);
        System.out.println("视频播放凭证 : " +response.getPlayAuth());
    }

    /**
     * 根据视频id获取视频播放地址
     * @throws ClientException
     */
    public static void getPlayUrl() throws ClientException {
        // 1.
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("", "");
        // 创建获取视频地址的 request 和 response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        // 向 request对象中设置视频的id值
        request.setVideoId("b9a1c7bfb2b449b29a93ab922f7b752e");
        // 调用初始化对象的方法传递 request 获取数据
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }
}
