package com.znb.eduservice.service;

import com.znb.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.znb.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-13
 */
public interface IEduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     * @param file
     */
    void savaSubject(MultipartFile file,IEduSubjectService subjectService);

    /**
     * 课程分类列表 (树形)
     * @return
     */
    List<OneSubject> getAllOneTwoSubject();
}
