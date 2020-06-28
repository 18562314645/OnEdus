package com.root.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.root.utils.GuliException;
import com.root.utils.R;
import com.root.vod.service.VodService;
import com.root.vod.utils.ConstantPropertiesUtil;
import com.root.vod.utils.InitVodObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description = "视频相关")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    @ApiOperation(value = "上传视频到阿里云")
    public R uploadVideo(MultipartFile file){
        String videoId=vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }

    @ApiOperation(value = "删除阿里云视频")
    @DeleteMapping("deleteAliVideo/{videoId}")
    public R deleteAliVideo(@PathVariable String videoId){
        try {
            //初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);

            //创建request对象
            DeleteVideoRequest request=new DeleteVideoRequest();
            //设置id
            request.setVideoIds(videoId);
            //调用方法删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频删除失败");
        }
    }

    //删除多个视频id
    @DeleteMapping("/delete-batch")
    public R deleteListId(@RequestParam("videoIdList")List<String> videoIdList){
        vodService.deleteListId(videoIdList);
        return R.ok();
    }
}
