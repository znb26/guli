package com.znb.eduservice.client;

import com.znb.commonutils.R;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 *
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错");
    }

    public R deleteBath(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
