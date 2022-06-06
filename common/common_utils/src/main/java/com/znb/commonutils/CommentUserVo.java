package com.znb.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentUserVo {

    @ApiModelProperty("会员id")
    private String memberId;

    @ApiModelProperty("会员昵称")
    private String nickname;

    @ApiModelProperty("会员头像")
    private String avatar;
}
