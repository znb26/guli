package com.znb.educms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 首页banner表
 * </p>
 *
 * @author znb
 * @since 2022-05-29
 */
@TableName("crm_banner")
@ApiModel(value = "CrmBanner对象", description = "首页banner表")
@Data
public class CrmBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("链接地址")
    private String linkUrl;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    private Date gmtModified;

}
