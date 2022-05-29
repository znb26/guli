package com.znb.eduservice.client;

import com.znb.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {

    /**
     * 定义要调用方法的路径
     */
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    R removeAlyVideo(@PathVariable("id") String id);

    /**
     * 定义调用删除多个视频的方法
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    R deleteBath(@RequestParam("videoIdList") List<String> videoIdList);
}
