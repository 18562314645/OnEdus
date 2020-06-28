package com.root.eduservice.controller;


import com.root.eduservice.entity.EduChapter;
import com.root.eduservice.entity.chapters.ChapterVo;
import com.root.eduservice.service.EduChapterService;
import com.root.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(description = "课程章节")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    @ApiOperation(value = "根据课程id获取章节")
    @GetMapping("getChapter/{courseId}")
    public R getChapter(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapter(courseId);
        return R.ok().data("list",list);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
         eduChapterService.save(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "修改章节")
    @PostMapping("updataChapter")
    public R updataChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean b=eduChapterService.deleteChapter(chapterId);
        if(b){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id获取章节信息")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);
    }
}

