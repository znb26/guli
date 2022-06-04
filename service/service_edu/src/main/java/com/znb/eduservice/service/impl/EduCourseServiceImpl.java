package com.znb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.eduservice.entity.EduCourse;
import com.znb.eduservice.entity.EduCourseDescription;
import com.znb.eduservice.entity.EduTeacher;
import com.znb.eduservice.entity.frontvo.CourseFrontVo;
import com.znb.eduservice.entity.frontvo.CourseWebVo;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.entity.vo.CoursePublishVo;
import com.znb.eduservice.mapper.EduCourseMapper;
import com.znb.eduservice.service.IEduChapterService;
import com.znb.eduservice.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znb.eduservice.service.IEduVideoService;
import com.znb.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements IEduCourseService {

    /**
     * 课程描述注入
     */
    @Autowired
    private EduCourseDescriptionServiceImpl courseDescriptionService;

    /**
     * 注入小节和章节的service
     */
    @Autowired
    private IEduVideoService eduVideoService;

    @Autowired
    private IEduChapterService eduChapterService;
    /**
     * 添加课程基本信息
     */
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1. 向课程表中添加课程基本信息
        // courseInfoVo 转换为 eduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);

        if(insert <= 0) {
            //添加失败
            throw new GuliException(20001,"添加课程信息失败");
        }

        //获取到添加成功之后的课程id
        String cid = eduCourse.getId();

        //2. 向课程描述表中添加课程描述信息 edu_course_description
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);

        return cid;
    }

    /**
     * 根据id查询课程信息
     * @param courseId
     * @return
     */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        // 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        // 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    /**
     * 修改课程信息的接口
     */
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if (update == 0) {
            throw new GuliException(20001,"修改课程信息失败");
        }
        // 修改简介表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    /**
     * 根据课程id查询课程确认信息
     */
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    /**
     * 删除课程
     * @param courseId 课程id
     * @return
     */
    @Override
    public void removeCourse(String courseId) {
        // 1. 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        // 2. 根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        // 3. 根据课程id删除表示
        courseDescriptionService.removeById(courseId);
        // 4. 删除课程本身
        int i = baseMapper.deleteById(courseId);

        if (i == 0) {
            throw new GuliException(20001,"删除失败");
        }
    }

    /**
     * 1 条件查询带分页查询课程
     */
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        // 构建查询条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        // 判断条件值是否为空
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            // 一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            // 二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            // 关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            // 最新的
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            // 价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();

        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     *     2 课程详情的方法
     * @param courseId
     * @return
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
