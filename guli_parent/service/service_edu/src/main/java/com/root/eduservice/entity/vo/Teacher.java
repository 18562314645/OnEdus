package com.root.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 讲师条件查询包装类
 */
@Data
@ApiModel(value = "Teachar 查询对象",description = "讲师查询条件封装")
public class Teacher implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "讲师头衔")
    private Integer level;

    @ApiModelProperty(value = "查询时间",example = "2019-01-01 10:10:10")
    private String begain;

    @ApiModelProperty(value = "结束时间",example = "2019-12-01 10:10:10")
    private String end;
}
