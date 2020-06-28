package com.root.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.root.eduservice.client.VideoClient;
import com.root.eduservice.entity.EduVideo;
import com.root.eduservice.mapper.EduVideoMapper;
import com.root.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VideoClient videoClient;

    @Override
    public boolean deleteVideo(String videoId) {
        int i = baseMapper.deleteById(videoId);
        return i>0;
    }

    //根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        //1.根据课程id查询所有的视频
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper);
        //遍历videoList获取视频id
        List videoIds=new ArrayList();
        for (EduVideo eduVideo : videoList) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoIds.add(videoSourceId);
            }
        }
        //根据多个视频id删除云端视频
        if(videoIds.size()>0) {
            videoClient.deleteListId(videoIds);
        }
        //删除小节
        QueryWrapper<EduVideo> eduVideoQueryWrapper=new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        baseMapper.delete(eduVideoQueryWrapper);
    }


}
