package com.znb.eduorder.service.impl;

import com.znb.commonutils.ordervo.CourseWebVoOrder;
import com.znb.commonutils.ordervo.UcenterMemberOrder;
import com.znb.eduorder.client.EduClient;
import com.znb.eduorder.client.UcenterClient;
import com.znb.eduorder.entity.TOrder;
import com.znb.eduorder.mapper.TOrderMapper;
import com.znb.eduorder.service.ITOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znb.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements ITOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;
    /**
     * 生成订单的方法
     * @param courseId
     * @param
     * @return
     */
    @Override
    public String createOrders(String courseId, String memberId) {
        // 通过远程调用获取用户信息 用户id
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        // 通过远程调用获取课程信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        // 创建order对象 向对象中设置值
        TOrder order = new TOrder();
        // 设置值
        // 订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        // 课程id
        order.setCourseId(courseId);
        // 课程标题
        order.setCourseTitle(courseInfoOrder.getTitle());
        // 课程封面
        order.setCourseCover(courseInfoOrder.getCover());
        // 讲师名称
        order.setTeacherName(courseInfoOrder.getTeacherName());
        // 课程价格
        order.setTotalFee(courseInfoOrder.getPrice());
        // 用户id
        System.out.println(memberId);
        order.setMemberId(memberId);
        // 用户手机号
        order.setMobile(userInfoOrder.getMobile());
        // 用户昵称
        order.setNickname(userInfoOrder.getNickname());
        // 支付状态
        order.setStatus(0);
        // 支付类型
        order.setPayType(1);

        baseMapper.insert(order);
        // 返回订单号
        return order.getOrderNo();
    }
}
