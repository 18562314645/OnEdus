package com.root.eduservice.service;

import com.root.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.root.eduservice.entity.vo.CoursePublishVo;
import com.root.eduservice.entity.vo.CourseVo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourse(CourseVo courseVo);

    CourseVo getCourseInfo(String courseId);

    void updataCourse(CourseVo courseVo);

    CoursePublishVo publishCourse(String courseId);

    void removeCourse(String courseId);
}
