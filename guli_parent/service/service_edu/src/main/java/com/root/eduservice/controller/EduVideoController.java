package com.root.eduservice.controller;


import com.root.eduservice.client.VideoClient;
import com.root.eduservice.entity.EduVideo;
import com.root.eduservice.service.EduVideoService;
import com.root.utils.GuliException;
import com.root.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-06-23
 */
@Api(description = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private VideoClient videoClient;

    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo video){
        eduVideoService.save(video);
        return R.ok();
    }

    @ApiOperation(value = "根据id获取小节信息")
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("video",eduVideo);
    }

    @PostMapping("updataVideo")
    public R updataVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    @ApiOperation(value = "删除小节")
    @DeleteMapping("{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        //根据小节id获取视频id
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String id = eduVideo.getVideoSourceId();
        //判断视频id是否为空
        if (!StringUtils.isEmpty(id)){
            //删除视频
            R result = videoClient.deleteAliVideo(id);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，触发了熔断");
            }
        }
        boolean b = eduVideoService.deleteVideo(videoId);
        if (b){
            return R.ok();
        }else {
            return R.error();
        }

    }


}

