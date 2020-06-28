package com.root.eduservice.entity.chapters;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//章节
@Data
public class ChapterVo implements Serializable{

    private String id;

    private String title;

    //小节
    private List<VideoVo> children=new ArrayList<>();

}
