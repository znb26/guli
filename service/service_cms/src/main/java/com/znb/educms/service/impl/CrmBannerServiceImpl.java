package com.znb.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.educms.entity.CrmBanner;
import com.znb.educms.mapper.CrmBannerMapper;
import com.znb.educms.service.ICrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-05-29
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements ICrmBannerService {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询所有banner
     * @return
     */
    @Cacheable(key = "'selectIndexList'",value = "banner")
    @Override
    public List<CrmBanner> selectAllBanner() {
        // 根据id降序排序 显示前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
