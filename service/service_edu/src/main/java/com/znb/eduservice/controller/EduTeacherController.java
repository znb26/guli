package com.znb.eduservice.controller;


import com.znb.eduservice.entity.EduTeacher;
import com.znb.eduservice.service.IEduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author znb
 * @since 2022-05-08
 */
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private IEduTeacherService service;
    // 查询所有数据
    @GetMapping("/findAll")
    public List<EduTeacher> findAll(){
        List<EduTeacher> list = service.list(null);
        return list;
    }

    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable String id){
        return service.removeById(id);
    }

}
