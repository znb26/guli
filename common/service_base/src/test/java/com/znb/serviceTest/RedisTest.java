package com.znb.serviceTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

}
