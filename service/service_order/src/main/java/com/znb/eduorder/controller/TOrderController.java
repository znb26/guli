package com.znb.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.commonutils.JwtUtils;
import com.znb.commonutils.R;
import com.znb.eduorder.entity.TOrder;
import com.znb.eduorder.service.ITOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    private ITOrderService orderService;

    /**
     * 生成订单的方法
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("/createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        // 创建订单返回订单号
        String orderNo =  orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId",orderNo);
    }

    /**
     * 根据订单号 查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("/getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }
}
