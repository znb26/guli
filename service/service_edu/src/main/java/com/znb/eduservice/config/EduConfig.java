package com.znb.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.znb.eduservice.mapper")
public class EduConfig {
}
