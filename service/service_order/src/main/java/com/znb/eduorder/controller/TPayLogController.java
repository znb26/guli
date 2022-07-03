package com.znb.eduorder.controller;


import com.znb.commonutils.R;
import com.znb.eduorder.service.ITPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
@RestController
@RequestMapping("/eduorder/paylog")
//@CrossOrigin
public class TPayLogController {

    @Autowired
    private ITPayLogService payLogService;

    /**
     * 生成微信支付二维码接口
     * @param orderNo
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo) {
        // 返回相关信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 查询订单支付状态
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        // 如果不为空
        if (map.get("trade_state").equals("SUCCESS")) {
            // 添加支付记录
            payLogService.updateOrdersStatus(map);
            return R.ok();
        }

        return R.ok().code(25000).message("支付中");
    }

}
