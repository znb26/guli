package com.znb.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.znb.eduservice.entity.EduSubject;
import com.znb.eduservice.entity.excel.SubjectData;
import com.znb.eduservice.entity.subject.OneSubject;
import com.znb.eduservice.entity.subject.TwoSubject;
import com.znb.eduservice.listener.SubjectExcelListener;
import com.znb.eduservice.mapper.EduSubjectMapper;
import com.znb.eduservice.service.IEduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author znb
 * @since 2022-05-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements IEduSubjectService {

    /**
     * 添加课程分类
     * @param file
     */
    @Override
    public void savaSubject(MultipartFile file,IEduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            // 调用方法进行读取 SubjectExcelListener
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService))
                    .sheet()
                    .doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 课程分类列表
     * @return
     */
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1. 查询所有一级分类 parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //2. 查询所有二级分类 parentid != 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        // 创建list集合， 用于存储最终存放的数据
        List<OneSubject> finalSubjectList = new ArrayList<>();
        //3. 封装一级分类
        // 查询出来所有的一级分类list集合遍历，得到每一个一级分类对象，获取每个一级分类对象值
        for (EduSubject eduSubject : oneSubjectList) {
            // 得到每一个eduSubject对象，放到oneSubject中

            //多个oneSubject放到finalSubject里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjectList.add(oneSubject);

            //4. 封装二级分类, 创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历
            for (EduSubject subject : twoSubjectList) {
                // 获取每个二级分类，封装,判断
                if (subject.getParentId().equals(eduSubject.getId())) {
                    // 封装
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(subject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            // 把一级下面所有的二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjectList;
    }
}
