package com.root.vod.api;

import com.root.utils.R;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 阿里云视频操作Api
 */
@RequestMapping("/eduvod/video")
public interface VideoApi {
    @DeleteMapping("deleteAliVideo/{videoId}")
    public R deleteAliVideo(@PathVariable String videoId);
}
