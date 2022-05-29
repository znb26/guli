package com.znb.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    /**
     *  上传视频的方法
     * @param file
     * @return
     */
    String uploadAlyiVideo(MultipartFile file);

    /**
     * 删除多个视频的方法
     * 参数 多个视频id
     */
    void removeMoreAlyVideo(List<String> videoIdList);
}
