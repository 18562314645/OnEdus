package com.root.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);


    void deleteListId(List videoIdList);
}
