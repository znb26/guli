package com.znb.eduservice.controller;


import com.znb.eduservice.entity.EduTeacher;
import com.znb.eduservice.service.IEduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api("讲师管理")
public class EduTeacherController {

    @Autowired
    private IEduTeacherService service;
    // 查询所有数据
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("/findAll")
    public List<EduTeacher> findAll(){
        List<EduTeacher> list = service.list(null);
        return list;
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public boolean removeTeacher(@ApiParam(name = "id",value = "讲师ID", required = true) @PathVariable String id){
        return service.removeById(id);
    }

}
