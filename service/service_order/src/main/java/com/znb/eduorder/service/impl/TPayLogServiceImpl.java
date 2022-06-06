package com.znb.eduorder.service.impl;

import com.znb.eduorder.entity.TPayLog;
import com.znb.eduorder.mapper.TPayLogMapper;
import com.znb.eduorder.service.ITPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements ITPayLogService {

}
