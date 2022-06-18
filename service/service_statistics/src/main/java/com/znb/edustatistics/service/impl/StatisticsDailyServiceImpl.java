package com.znb.edustatistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.commonutils.R;
import com.znb.edustatistics.client.UcenterClient;
import com.znb.edustatistics.entity.StatisticsDaily;
import com.znb.edustatistics.mapper.StatisticsDailyMapper;
import com.znb.edustatistics.service.IStatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-06-18
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements IStatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        // 添加之前先删除相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);


        // 远程调用得到某一天的注册人数
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer)registerR.getData().get("countRegister");

        // 把获取到的数据添加到数据库
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister); //注册人数
        sta.setDateCalculated(day); //统计日期

        sta.setVideoViewNum(RandomUtils.nextInt(100,200));
        sta.setLoginNum(RandomUtils.nextInt(100,200));
        sta.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(sta);
    }

    /**
     * 图表显示
     * 返回两部分数据
     * 日期json数组
     * 数量json数组
     */
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        // 查询的限制条件
        wrapper.between("date_calculated",begin, end);
        // 查询指定的列
        wrapper.select("date_calculated",type);
        // 查询
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);
        //封装数据

        //日期json数组
        List<String> dateCalculatedList = new ArrayList<>();
        //数量json数组
        List<Integer> numDataList = new ArrayList<>();

        for (StatisticsDaily daily : staList) {
            //封装日期
            dateCalculatedList.add(daily.getDateCalculated());
            //封装数量
            switch (type){
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                default:
                    break;
            }

        }

        Map<String ,Object> map = new HashMap<>();
        map.put("date_calculatedList",dateCalculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
