package com.root.eduservice.service;

import com.root.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.root.eduservice.entity.chapters.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapter(String courseId);

    //根据章节id删除章节
    boolean deleteChapter(String chapterId);

    //根据课程id删除章节
    void delChapterByCourseId(String courseId);
}
