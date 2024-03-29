package com.znb.educenter.mapper;

import com.znb.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.znb.commonutils.CommentUserVo;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author znb
 * @since 2022-05-31
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 根据用户id获取用户信息
     */
    CommentUserVo getCommentUserInfo(String memberId);

    /**
     * 查询某一天的注册人数
     */
    Integer countRegisterDay(String day);
}
