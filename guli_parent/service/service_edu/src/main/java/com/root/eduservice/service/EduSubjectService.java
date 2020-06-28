package com.root.eduservice.service;

import com.root.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.root.eduservice.entity.subject.OneSubjecct;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-06-21
 */
public interface EduSubjectService extends IService<EduSubject> {

   public void importSubject(MultipartFile file,EduSubjectService service);

    List<OneSubjecct> getSubject();
}
