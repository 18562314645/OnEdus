package com.root.eduservice.client;

import com.root.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VideoClientFeignImpl implements VideoClient{
    @Override
    public R deleteAliVideo(String videoId) {
        return R.error().message("删除视频失败了");
    }

    @Override
    public R deleteListId(List<String> videoIdList) {
        return R.error().message("删除多个视频失败了");
    }
}
