package com.znb.eduorder.service;

import com.znb.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
public interface ITOrderService extends IService<TOrder> {

    /**
     * 生成订单的方法
     * @param courseId
     * @param
     * @return
     */
    String createOrders(String courseId, String memberIdByJwtToken);
}
