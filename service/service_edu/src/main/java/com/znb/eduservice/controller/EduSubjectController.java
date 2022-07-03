package com.znb.eduservice.controller;


import com.znb.commonutils.R;
import com.znb.eduservice.entity.subject.OneSubject;
import com.znb.eduservice.service.IEduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-05-13
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private IEduSubjectService subjectService;

    /**
     * 添加课程分类
     * 获取上传的文件，把文件内容读取出来
     *
     */
    @PostMapping(value = "addSubject", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R addSubject(@RequestPart("file") MultipartFile file){
        // 上传过来的excel文件
        subjectService.savaSubject(file,subjectService);
        return R.ok();
    }

    /**
     * 课程分类列表功能
     * 树形结构
     * @param
     * @return
     */
    @GetMapping(value = "getAllSubject")
    public R getAllSubject(){
        /**
         * 1. 针对返回的数据创建实体类
         */
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}
