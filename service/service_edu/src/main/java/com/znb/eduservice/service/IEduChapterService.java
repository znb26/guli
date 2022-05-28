package com.znb.eduservice.service;

import com.znb.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.znb.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author znb
 * @since 2022-05-17
 */
public interface IEduChapterService extends IService<EduChapter> {

    /**
     * 课程大纲列表,根据课程id查询
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    void removeChapterByCourseId(String courseId);
}
