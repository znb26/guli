package com.znb.eduservice.service.impl;

import com.znb.eduservice.entity.EduCourse;
import com.znb.eduservice.entity.EduCourseDescription;
import com.znb.eduservice.entity.vo.CourseInfoVo;
import com.znb.eduservice.mapper.EduCourseMapper;
import com.znb.eduservice.service.IEduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    //课程描述注入
    @Autowired
    private EduCourseDescriptionServiceImpl courseDescriptionService;
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
}
