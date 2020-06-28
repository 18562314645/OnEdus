package com.root.eduservice.service;

import com.root.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean deleteVideo(String videoId);

    //1 根据课程id删除小节
    void removeVideoByCourseId(String courseId);


}
