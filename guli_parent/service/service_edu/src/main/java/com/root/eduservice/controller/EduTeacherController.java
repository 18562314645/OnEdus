package com.root.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.eduservice.entity.EduTeacher;
import com.root.eduservice.entity.vo.Teacher;
import com.root.eduservice.service.EduTeacherService;
import com.root.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-16
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询讲师列表
     * @return
     */
    @ApiOperation(value = "获取讲师列表")
    @GetMapping("findAll")
    public R getEduTeacher(){
        /*try {
            int i=1/0;
        }catch (Exception e){
            throw new GuliException(2001,"自定义异常");
        }*/

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("item",list);
    }

    @GetMapping("{page}/{limit}")
    public R getEduTeacherByPage(
            @ApiParam(name = "page",value = "当前页码",required = true) @PathVariable Long page,
            @ApiParam(name = "limit",value = "每页记录数",required = true) @PathVariable Long limit){
        Page<EduTeacher> pageParam=new Page<>(page,limit);
        eduTeacherService.page(pageParam,null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 逻辑删除
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeById(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        eduTeacherService.removeById(id);
        return R.ok();
    }

    /**
     * 条件查询讲师
     * @return
     */
    @ApiOperation(value = "条件查询讲师")
    @PostMapping("pageTeacherCondition/{page}/{limit}")
    public R queryTeacherByPage(@PathVariable Long page, @PathVariable Long limit, @RequestBody(required = false) Teacher queryTeacher){
        Page<EduTeacher> pageParam=new Page<>(page,limit);
        //构建查询条件
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper<>();
        //动态拼接sql
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        String begain = queryTeacher.getBegain();
        String end = queryTeacher.getEnd();
        //判断条件是否为空，不为空就拼接
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begain)){
            wrapper.ge("gmt_create",begain);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        eduTeacherService.page(pageParam,wrapper);
        long total = pageParam.getTotal();
        List<EduTeacher> records = pageParam.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addEdUTeacher(@ApiParam(name = "teacher",value = "讲师对象",required = true) @RequestBody  EduTeacher eduTeacher){
        eduTeacherService.save(eduTeacher);
        return R.ok();
    }

    @ApiOperation(value = "根据id查询讲师对象")
    @GetMapping("getTeacher/{id}")
    public R getTeacherById(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updataTeacher(@RequestBody EduTeacher eduTeacher){
        eduTeacherService.updateById(eduTeacher);
        return R.ok();
    }
}

