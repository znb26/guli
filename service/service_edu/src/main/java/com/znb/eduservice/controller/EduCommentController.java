package com.znb.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.commonutils.CommentUserVo;
import com.znb.commonutils.JwtUtils;
import com.znb.commonutils.R;
import com.znb.eduservice.client.UcenterClient;
import com.znb.eduservice.entity.EduComment;
import com.znb.eduservice.service.IEduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-06-05
 */
@Controller
@RequestMapping("/eduservice/comment")
@Api(value = "课程章节小节信息", tags = "课程章节小节信息")
public class EduCommentController {
    /**
     * TODO 完成评论功能
     * 分页查询课程评论的方法
     * 添加评论的方法
     *  需要添加的数据：评论内容、课程id、讲师id、用户id、用户昵称、用户头像
     *  用户信息需要调用ucenter中的方法 使用nacos进行远程调用
     *  必须登录才能进行评论
     *  在edu中获取用户信息
     */

    @Autowired
    private IEduCommentService commentService;

    @Autowired
    private UcenterClient ucenterClient;


    /**
     * 分根据课程id查询评论列表
     */
    @ApiOperation(value = "分根据课程id查询评论列表")
    @GetMapping("/getCommentPage/{current}/{limit}")
    public R getCommentPage(@PathVariable long current,
                           @PathVariable long limit,
                            @PathVariable String courseId) {
        Page<EduComment> pageParam = new Page<>(current,limit);

        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);

        commentService.page(pageParam, wrapper);
        List<EduComment> commentList = pageParam.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("items", commentList);
        map.put("current", pageParam.getCurrent());
        map.put("pages", pageParam.getPages());
        map.put("size", pageParam.getSize());
        map.put("total", pageParam.getTotal());
        map.put("hasNext", pageParam.hasNext());
        map.put("hasPrevious", pageParam.hasPrevious());
        return R.ok().data(map);
    }

    /**
     * 添加评论
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/addComment")
    public R addCommon(@RequestBody EduComment eduComment, HttpServletRequest request) {
        //根据token获取用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().message("请登录");
        }
        eduComment.setMemberId(memberId);
        CommentUserVo member = ucenterClient.getUcenterById(memberId);

        eduComment.setNickname(member.getNickname());
        eduComment.setAvatar(member.getAvatar());
        commentService.save(eduComment);
        return R.ok();
    }


}
