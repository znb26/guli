package com.znb.eduservice.client;

import com.znb.commonutils.CommentUserVo;
import com.znb.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name="service-ucenter")
public interface UcenterClient {
    //根据用户id获取用户信息
    @GetMapping("/educenter/member/getUcenter/{memberId}")
    CommentUserVo getUcenterById(@PathVariable("memberId") String memberId);
}

