package com.znb.edustatistics.controller;


import com.znb.commonutils.R;
import com.znb.edustatistics.client.UcenterClient;
import com.znb.edustatistics.service.IStatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-06-18
 */
@RestController
@RequestMapping("/staservice/sta")
//@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private IStatisticsDailyService staService;



    /**
     * 统计某一天的注册人数
     * 生成统计数据
     */
    @PostMapping("/registerCount/{day}")
    public R registerCount(@PathVariable String day){
        staService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示
     * 返回两部分数据
     * 日期json数组
     * 数量json数组
     */
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                      @PathVariable String begin,
                      @PathVariable String end){
        Map<String,Object> map = staService.getShowData(type,begin,end);
        return R.ok().data(map);
    }

}
