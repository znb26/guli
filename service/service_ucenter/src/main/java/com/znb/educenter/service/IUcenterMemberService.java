package com.znb.educenter.service;

import com.znb.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.znb.commonutils.CommentUserVo;
import com.znb.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-31
 */
public interface IUcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     */
    String login(UcenterMember member);

    /**
     * 注册
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openid判断扫描用户是否已经存在
     * @param openid
     * @return
     */
    UcenterMember getOpenIdMember(String openid);

    /**
     * 根据用户id获取用户信息
     */
    CommentUserVo getCommentUserInfo(String memberId);

    /**
     * 查询某一天的注册人数
     */
    Integer countRegisterDay(String day);
}
