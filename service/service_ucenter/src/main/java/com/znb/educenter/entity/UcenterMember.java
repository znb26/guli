package com.znb.educenter.entity;

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
 * 会员表
 * </p>
 *
 * @author znb
 * @since 2022-05-31
 */
@TableName("ucenter_member")
@ApiModel(value = "UcenterMember对象", description = "会员表")
@Data
public class UcenterMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("会员id")
    private String id;

    @ApiModelProperty("微信openid")
    private String openid;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("性别 1 女，2 男")
    private Integer sex;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户签名")
    private String sign;

    @ApiModelProperty("是否禁用 1（true）已禁用，  0（false）未禁用")
    private Boolean isDisabled;

    @ApiModelProperty("逻辑删除 1（true）已删除， 0（false）未删除")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}
