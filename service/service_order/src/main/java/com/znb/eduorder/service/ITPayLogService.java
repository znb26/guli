package com.znb.eduorder.service;

import com.znb.eduorder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
public interface ITPayLogService extends IService<TPayLog> {

    /**
     * 生成微信支付二维码接口
     * @param orderNo
     * @return
     */
    Map createNative(String orderNo);

    /**
     * 查询订单支付状态
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 向支付宝添加记录，更新订单状态
     * @param map
     */
    void updateOrdersStatus(Map<String, String> map);
}
