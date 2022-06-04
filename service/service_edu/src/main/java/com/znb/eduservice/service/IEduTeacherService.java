package com.znb.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-08
 */
public interface IEduTeacherService extends IService<EduTeacher> {

    /**
     * 分页查询讲师信息
     */
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
