package com.znb.eduservice.service.impl;

import com.znb.eduservice.entity.EduCourse;
import com.znb.eduservice.entity.EduCourseDescription;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.entity.vo.CoursePublishVo;
import com.znb.eduservice.mapper.EduCourseMapper;
import com.znb.eduservice.service.IEduChapterService;
import com.znb.eduservice.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.znb.eduservice.service.IEduVideoService;
import com.znb.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}