package com.znb.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.eduservice.entity.EduSubject;
import com.znb.eduservice.entity.excel.SubjectData;
import com.znb.eduservice.service.IEduSubjectService;
import com.znb.servicebase.exceptionhandler.GuliException;


/**
 * 因为SubjectExcelListener这个监听器不能交给spring容器管理
 * 需要自己new 所有就不能注入属性
 */
public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public IEduSubjectService subjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(IEduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //读取内容，加入数据库
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001,"文件数据为空");
        }

        //一行一行读
        //判断一级分类是否重复
        EduSubject existOneSubject = this.existOneSubject(subjectData.getOneSubjectName(), subjectService);
        if (existOneSubject == null) { // 表示不存在 添加
            existOneSubject = new EduSubject();
            existOneSubject.setParentId("0");
            existOneSubject.setTitle(subjectData.getOneSubjectName()); //设置一级分类名称
            subjectService.save(existOneSubject);
        }
        // 二级分类
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(subjectData.getTwoSubjectName(), pid, subjectService);
        if (existTwoSubject == null) { // 表示不存在 添加
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName()); //设置一级分类名称
            subjectService.save(existTwoSubject);
        }
    }

    //判断一级分类不能重复添加
    private EduSubject existOneSubject(String name,IEduSubjectService subjectService){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject one = subjectService.getOne(wrapper);
        return one;
    }
    //判断二级分类不能重复添加
    private EduSubject existTwoSubject(String name,String pid, IEduSubjectService subjectService){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = subjectService.getOne(wrapper);
        return two;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
