package com.root.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.root.eduservice.entity.EduChapter;
import com.root.eduservice.entity.EduVideo;
import com.root.eduservice.entity.chapters.ChapterVo;
import com.root.eduservice.entity.chapters.VideoVo;
import com.root.eduservice.mapper.EduChapterMapper;
import com.root.eduservice.mapper.EduVideoMapper;
import com.root.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.root.utils.GuliException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduChapterMapper eduChapterMapper;

    @Autowired
    private EduVideoMapper eduVideoMapper;
    @Override
    public List<ChapterVo> getChapter(String courseId) {
        List<ChapterVo> finalChapter=new ArrayList<>();

        //查出大章节的集合
        QueryWrapper<EduChapter> wrapperChapter=new QueryWrapper();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapters = eduChapterMapper.selectList(wrapperChapter);

        //查出小章节的集合
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideos = eduVideoMapper.selectList(wrapperVideo);

        //封装大章节
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalChapter.add(chapterVo);
            //封装小节
            List<VideoVo> videoVoList=new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                VideoVo videoVo=new VideoVo();
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVoList.add(videoVo);
                }

            }
            chapterVo.setChildren(videoVoList);

        }
        //返回集合
        return finalChapter;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapperChapter=new QueryWrapper<>();
        //章节下边有小节的话不能直接删除章节，应该先删除小节
        wrapperChapter.eq("chapter_id",chapterId);
        Integer count = eduVideoMapper.selectCount(wrapperChapter);
        if (count>0){
            //不能删除章节
            throw new GuliException(20001,"请先删除小节");
        }else {
            //删除章节
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }

    }

    //根据课程id删除章节
    @Override
    public void delChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        if (!StringUtils.isEmpty(courseId)){
            baseMapper.delete(queryWrapper);
        }

    }


}
