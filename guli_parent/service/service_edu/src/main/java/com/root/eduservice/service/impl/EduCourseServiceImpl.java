package com.root.eduservice.service.impl;

import com.root.eduservice.entity.EduCourse;
import com.root.eduservice.entity.EduCourseDescription;
import com.root.eduservice.entity.vo.CoursePublishVo;
import com.root.eduservice.entity.vo.CourseVo;
import com.root.eduservice.mapper.EduCourseMapper;
import com.root.eduservice.service.EduChapterService;
import com.root.eduservice.service.EduCourseDescriptionService;
import com.root.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.eduservice.service.EduVideoService;
import com.root.utils.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;
    
    @Override
    public String addCourse(CourseVo courseVo) {

        //添加相关内容到course表
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseVo,eduCourse);
        int i = baseMapper.insert(eduCourse);
        if (i==0){
            throw new GuliException(20001,"添加课程失败");
        }
        String eduCourseId = eduCourse.getId();

        //添加简介到course_description表
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        BeanUtils.copyProperties(courseVo,eduCourseDescription);
        eduCourseDescription.setId(eduCourseId);
        eduCourseDescriptionService.save(eduCourseDescription);
        return eduCourseId;
    }

    //根据ID查询课程信息
    @Override
    public CourseVo getCourseInfo(String courseId) {

        //查询educourse表中的信息
        CourseVo courseVo=new CourseVo();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,courseVo);

        //查询eduCourseDescription表中的信息
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        courseVo.setDescription(courseDescription.getDescription());

        return courseVo;
    }

    //修改课程信息
    @Override
    public void updataCourse(CourseVo courseVo) {
        //修改educourse表中的信息
        EduCourse eduCourse=new EduCourse();
        BeanUtils.copyProperties(courseVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i==0){
            throw new GuliException(20001,"修改课程失败");
        }
        //修改eduCourseDescription表中的信息
        EduCourseDescription eduCourseDescription=new EduCourseDescription();
        eduCourseDescription.setId(courseVo.getId());
        eduCourseDescription.setDescription(courseVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    //最终发布课程
    @Override
    public CoursePublishVo publishCourse(String courseId) {
        if (courseId==null){
            throw new GuliException(20001,"课程Id不能为空");
        }
        CoursePublishVo coursePublishVo = baseMapper.publishCourse(courseId);
        return coursePublishVo;
    }

    //根据id删除课程
    @Override
    public void removeCourse(String courseId) {

        //根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //根据课程id删除章节
        eduChapterService.delChapterByCourseId(courseId);

        //根据课程id删除课程描述
        eduCourseDescriptionService.removeById(courseId);

        //根据课程id删除课程
        int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw new GuliException(20001,"删除课程失败");
        }
    }
}
