package com.znb.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.znb.commonutils.R;
import com.znb.eduservice.entity.EduTeacher;
import com.znb.eduservice.entity.vo.TeacherQuery;
import com.znb.eduservice.service.IEduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public R findAll(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID", required = true) @PathVariable String id){
        boolean b = service.removeById(id);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询讲师信息")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current",value = "当前页", required = true)
                                 @PathVariable long current,
                                @PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        service.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        Map map = new HashMap();
        map.put("total",total);
        map.put("rows",records);
        return R.ok().data(map);
        //return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "条件查询分页")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页", required = true) @PathVariable long current,
                                  @ApiParam(name = "limit",value = "每页显示条数", required = true) @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<EduTeacher> page = new Page<>(current,limit);
        // 构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        // 判断条件是否为空
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified",end);
        }

        service.page(page,wrapper);
        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = service.save(eduTeacher);
        if (save) {
            return R.ok();
        }
        return R.error();
    }

    @ApiOperation(value = "根据id查询")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        int i = 10 / 0;
        EduTeacher byId = service.getById(id);
        return R.ok().data("teacher",byId);
    }

    @ApiOperation(value = "修改讲师信息")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = service.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }
        return R.error();
    }

}
