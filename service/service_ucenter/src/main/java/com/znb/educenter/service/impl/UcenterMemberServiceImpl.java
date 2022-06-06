package com.znb.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.commonutils.JwtUtils;
import com.znb.commonutils.MD5;
import com.znb.educenter.entity.UcenterMember;
import com.znb.commonutils.CommentUserVo;
import com.znb.educenter.entity.vo.RegisterVo;
import com.znb.educenter.mapper.UcenterMemberMapper;
import com.znb.educenter.service.IUcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znb.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-05-31
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements IUcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 登录
     */
    @Override
    public String login(UcenterMember member) {
        // 获取登录的手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();
        // 非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"登录失败");
        }
        // 手机号是否存在
        QueryWrapper<UcenterMember> wrapperMobile = new QueryWrapper<>();
        wrapperMobile.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapperMobile);
        // 是否为空
        if (mobileMember == null) {
            throw new GuliException(20001,"手机号不存在");
        }

        // 密码是否正确
        // 因为存储在数据库中的不是明文密码
        // 把我们输入的密码先进行加密在和数据库进行比较 MD5加密
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001,"密码错误");
        }

        // 判断用户是否禁用
        if (mobileMember.getIsDeleted()) {
            throw new GuliException(20001,"被封号了");
        }

        // 登录成功
        // 生成 token字符串
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
        return jwtToken;
    }

    /**
     * 注册
     */
    @Override
    public void register(RegisterVo registerVo) {
        // 获取注册的数据
        // 验证码
        String code = registerVo.getCode();
        System.out.println(code);
        // 手机号
        String mobile = registerVo.getMobile();
        System.out.println(mobile);
        // 昵称
        String nickName = registerVo.getNickName();
        System.out.println(nickName);
        // 密码
        String password = registerVo.getPassword();
        System.out.println(password);

        // 非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code) || StringUtils.isEmpty(nickName)) {
            throw new GuliException(20001,"注册失败");
        }
        // 判断验证码
        // 获取redis中的验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001,"验证码错误");
        }

        // 判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Long aLong = baseMapper.selectCount(wrapper);
        if (aLong > 0) {
            throw new GuliException(20001,"手机号重复");
        }

        //把数据添加到数据库
        UcenterMember ucenterMember = new UcenterMember();
        ucenterMember.setMobile(mobile);
        ucenterMember.setNickname(nickName);
        ucenterMember.setPassword(MD5.encrypt(password));
        ucenterMember.setIsDisabled(false);
        ucenterMember.setAvatar("https://edu-guli-znb.oss-cn-hangzhou.aliyuncs.com/2022/05/12/8a55d80f85d847e29e975e29627713a9file.png");
        baseMapper.insert(ucenterMember);
    }

    /**
     * 根据openid判断扫描用户是否已经存在
     * @param openid
     * @return
     */
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        return ucenterMember;
    }

    /**
     * 根据用户id获取用户信息
     */
    @Override
    public CommentUserVo getCommentUserInfo(String memberId) {
        return baseMapper.getCommentUserInfo(memberId);
    }
}
