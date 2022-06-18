package com.znb.edustatistics.schedule;

import com.znb.edustatistics.service.IStatisticsDailyService;
import com.znb.edustatistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {

    @Autowired
    private IStatisticsDailyService staService;

    /**
     * 每天凌晨一点统计一次数据
     * 统计前一天的数据
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }


    /**
     * 每过五秒执行一次
     */
//    @Scheduled(cron = "0/5 * * * * ?")
    public void task1(){
        System.out.println("task1");
    }
}
