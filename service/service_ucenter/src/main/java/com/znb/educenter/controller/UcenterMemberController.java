package com.znb.educenter.controller;


import com.znb.commonutils.JwtUtils;
import com.znb.commonutils.R;
import com.znb.educenter.entity.UcenterMember;
import com.znb.educenter.entity.vo.RegisterVo;
import com.znb.educenter.service.IUcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-05-31
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private IUcenterMemberService memberService;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember member){
        // 调用方法实现登录 返回 token值
        String token = memberService.login(member);
        System.out.println("token:" + token);
        return R.ok().data("token",token);
    }


    /**
     * 注册
     */
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        System.out.println(registerVo.getNickName());
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        // 调用jwt中的方法，根据request对象 返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户id获取用户信息
        UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo",member);
    }
}
