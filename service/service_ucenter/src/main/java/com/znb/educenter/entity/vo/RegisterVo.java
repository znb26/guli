package com.znb.educenter.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "注册对象",description = "注册对象")
public class RegisterVo {

    private String nickName;

    private String mobile;

    private String password;

    private String code;
}
