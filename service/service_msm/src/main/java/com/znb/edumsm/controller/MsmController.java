package com.znb.edumsm.controller;

import com.znb.commonutils.R;
import com.znb.edumsm.service.MsmService;
import com.znb.edumsm.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 发送短信的方法
     */
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) throws Exception {
        // 从缓存中取出验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }
        // 生成随机的值 ，传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        System.out.println(code);
        // 调用service中发送短信的方法
        Boolean b = msmService.send(code,phone);
        if (b) {
            // 发送成功,把发送成功的验证码放到redis中 同时设置过期时间 5 分钟
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        return R.error().message("短信发送失败");
    }
}
