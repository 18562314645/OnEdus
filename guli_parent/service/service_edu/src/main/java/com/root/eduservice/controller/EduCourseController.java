package com.root.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.root.eduservice.entity.EduCourse;
import com.root.eduservice.entity.vo.CoursePublishVo;
import com.root.eduservice.entity.vo.CourseQuery;
import com.root.eduservice.entity.vo.CourseVo;
import com.root.eduservice.service.EduCourseService;
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
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程")
    @PostMapping("add")
    public R addCourse(@ApiParam(name = "courseVo",value = "课程包装类")@RequestBody CourseVo courseVo){
        String courseId = eduCourseService.addCourse(courseVo);
        return R.ok().data("courseId",courseId);
    }

    //查询课程信息
    @ApiOperation(value = "根据ID查询课程信息")
    @GetMapping("{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseVo courseVo=eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseVo",courseVo);
    }

    @PostMapping("updataCourse")
    @ApiOperation(value = "修改课程信息")
    public R updataCourse(@RequestBody CourseVo courseVo){
        eduCourseService.updataCourse(courseVo);
        return R.ok();
    }

    @GetMapping("publishCourse/{courseId}")
    @ApiOperation(value = "发布课程")
    public R publishCourse(@PathVariable String courseId){
        CoursePublishVo coursePublishVo = eduCourseService.publishCourse(courseId);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    @ApiOperation(value = "发布成功修改课程状态")
    @PostMapping("updataStatus/{courseId}")
    public R updataStatus(@PathVariable String courseId){
        EduCourse eduCourse=new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //课程列表
    @ApiOperation(value = "课程列表")
    @PostMapping("getCourseList/{page}/{limit}")
    public R getCourseList(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQuery courseQuery){
        //分页相关
        Page<EduCourse> pageParam=new Page<>();
        //构建查询条件
        QueryWrapper<EduCourse> queryWrapper=new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)){
            queryWrapper.eq("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            queryWrapper.eq("status",status);
        }

        eduCourseService.page(pageParam, queryWrapper);
        long total = pageParam.getTotal();
        List<EduCourse> records = pageParam.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "删除课程信息")
    @DeleteMapping("delete/{courseId}")
    public R deleteCourse(@PathVariable String courseId){

        eduCourseService.removeCourse(courseId);
        return R.ok();
    }
}

