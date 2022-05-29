package com.znb.educms.service;

import com.znb.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-29
 */
public interface ICrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有banner
     * @return
     */
    List<CrmBanner> selectAllBanner();
}
