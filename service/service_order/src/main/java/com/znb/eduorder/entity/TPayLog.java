package com.znb.eduorder.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 支付日志表
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
@TableName("t_pay_log")
@ApiModel(value = "TPayLog对象", description = "支付日志表")
@Data
public class TPayLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("支付完成时间")
    private LocalDateTime payTime;

    @ApiModelProperty("支付金额（分）")
    private BigDecimal totalFee;

    @ApiModelProperty("交易流水号")
    private String transactionId;

    @ApiModelProperty("交易状态")
    private String tradeState;

    @ApiModelProperty("支付类型（1：微信 2：支付宝）")
    private Integer payType;

    @ApiModelProperty("其他属性")
    private String attr;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;


}
