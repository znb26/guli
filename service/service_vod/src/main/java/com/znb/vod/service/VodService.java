package com.znb.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    /**
     *  上传视频的方法
     * @param file
     * @return
     */
    String uploadAlyiVideo(MultipartFile file);
}
