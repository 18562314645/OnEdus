package com.root.eduservice.client;

import com.root.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod",fallback = VideoClientFeignImpl.class)
public interface VideoClient{
    @DeleteMapping("/eduvod/video/deleteAliVideo/{videoId}")
    public R deleteAliVideo(@PathVariable("videoId") String videoId);

    //删除多个视频id
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteListId(@RequestParam("videoIdList")List<String> videoIdList);

}
