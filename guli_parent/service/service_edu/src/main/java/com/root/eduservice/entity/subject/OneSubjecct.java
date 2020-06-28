package com.root.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 一级分类
 */
@Data
public class OneSubjecct {

    private String id;

    private String title;

    private List<TwoSubject> children=new ArrayList<>();
}
