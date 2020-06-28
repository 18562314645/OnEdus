package com.root.eduservice.mapper;

import com.root.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.root.eduservice.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublishVo publishCourse(String courseId);
}
