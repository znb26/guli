package com.znb.edustatistics.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 网站统计日数据
 * </p>
 *
 * @author znb
 * @since 2022-06-18
 */
@TableName("statistics_daily")
@ApiModel(value = "StatisticsDaily对象", description = "网站统计日数据")
@Data
public class StatisticsDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("统计日期")
    private String dateCalculated;

    @ApiModelProperty("注册人数")
    private Integer registerNum;

    @ApiModelProperty("登录人数")
    private Integer loginNum;

    @ApiModelProperty("每日播放视频数")
    private Integer videoViewNum;

    @ApiModelProperty("每日新增课程数")
    private Integer courseNum;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
