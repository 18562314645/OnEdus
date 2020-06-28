package com.root.eduservice.controller;


import com.root.eduservice.entity.subject.OneSubjecct;
import com.root.eduservice.service.EduSubjectService;
import com.root.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-21
 */
@Api(description = "课程分类管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService eduSubjectService;

    @PostMapping("import")
    @ApiOperation(value = "Excel批量导入")
    public R importSubject(MultipartFile file){
        eduSubjectService.importSubject(file,eduSubjectService);
        return R.ok();
    }

    @GetMapping("getSubject")
    public R getSubject(){
        List<OneSubjecct> list= eduSubjectService.getSubject();
        return R.ok().data("list",list);
    }
}

