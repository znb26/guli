package com.znb.eduservice.controller;

import com.znb.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class EduLoginController {
    //记得加注解 @CrossOrigin
    // login
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("/info")
    public R info(){
        // roles, name, avatar, introduction
        return R.ok()
                .data("roles","111")
                .data("name","name")
                .data("avatar","https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg5.tianyancha.com%2Fsogou%2FWeChat%2F9158bf86b67682b61e0178207abc377a.png%40%21watermark01&refer=http%3A%2F%2Fimg5.tianyancha.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1654780697&t=04f009d49d33b38dbb36b49a0ff18097")
                .data("introduction","introduction");
    }

}
