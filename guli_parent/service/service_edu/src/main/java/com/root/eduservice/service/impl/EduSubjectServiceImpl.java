package com.root.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.root.eduservice.entity.EduSubject;
import com.root.eduservice.entity.SubjectData;
import com.root.eduservice.entity.subject.OneSubjecct;
import com.root.eduservice.entity.subject.TwoSubject;
import com.root.eduservice.listener.SubjectExcelListener;
import com.root.eduservice.mapper.EduSubjectMapper;
import com.root.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void importSubject(MultipartFile file,EduSubjectService service) {

        try {
            //获取输入流
            InputStream inputStream = file.getInputStream();

            //读取文件
            EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(service)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubjecct> getSubject() {

        //查出一级分类的集合
        List<OneSubjecct> FinalSubject=new ArrayList();
        QueryWrapper<EduSubject> oneWrapper=new QueryWrapper<>();
        oneWrapper.eq("parent_id","0");
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);
        //查出二级分类的集合
        QueryWrapper<EduSubject> twoWrapper=new QueryWrapper<>();
        twoWrapper.ne("parent_id","0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);

        //封装一级分类
        for (int i = 0; i < oneSubjects.size(); i++) {
            EduSubject oneSubject = oneSubjects.get(i);
           OneSubjecct oneSubjecct=new OneSubjecct();
            BeanUtils.copyProperties(oneSubject,oneSubjecct);
            FinalSubject.add(oneSubjecct);
            List<TwoSubject> twoSubjectList=new ArrayList<>();

            for (int m = 0; m < twoSubjects.size(); m++) {
                EduSubject twoSubject=twoSubjects.get(m);
                if (twoSubject.getParentId().equals(oneSubjecct.getId())) {
                    TwoSubject twoSubject1 = new TwoSubject();
                    BeanUtils.copyProperties(twoSubject, twoSubject1);
                    twoSubjectList.add(twoSubject1);
                }
            }

            oneSubjecct.setChildren(twoSubjectList);
        }

        //在一级分类中遍历二级分类集合 拿到每一个twosubject放到二级分类的集合中
        //返回最终集合
        return FinalSubject;
    }
}
