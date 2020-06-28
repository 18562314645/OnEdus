package com.root.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.root.eduservice.entity.EduSubject;
import com.root.eduservice.entity.SubjectData;
import com.root.eduservice.service.EduSubjectService;
import com.root.utils.GuliException;
import lombok.Data;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData>{

    public EduSubjectService service;

    public SubjectExcelListener(){};

    public SubjectExcelListener (EduSubjectService service){
        this.service=service;
    }


    //读取excel表头信息一行一行的读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行的读取文件，第一个值为一及分类，第二值个为二级分类
        //判断一级分类是否重复
        EduSubject oneSubject = this.existOneSubject(service, subjectData.getOneSubjectName());
        //如果数据库中一级分类为空，则保存，如果不为空则存储二级分类名称
        if (oneSubject==null){
            oneSubject=new EduSubject();
            oneSubject.setTitle(subjectData.getOneSubjectName());
            oneSubject.setParentId("0");
            service.save(oneSubject);
        }
        String pid = oneSubject.getId();    //获取一级分类id
        //一级分类不为空，则判断二级分类是否重复

        EduSubject twoSubject = this.existTwoSubject(service, subjectData.getTwoSubjectName(), pid);
        if (twoSubject==null){
            twoSubject=new EduSubject();
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            twoSubject.setParentId(pid);
            service.save(twoSubject);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


    //判断一级分类是否重复
    private EduSubject existOneSubject(EduSubjectService service,String name){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject = service.getOne(wrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加

    private EduSubject existTwoSubject(EduSubjectService service,String name,String pid){
        QueryWrapper<EduSubject> wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject = service.getOne(wrapper);
        return twoSubject;
    }
}
