package com.root.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务类
 */
public interface FileService {

    /**
     * 文件上传到阿里云
     * @param file
     * @return
     */
    public String upLoad(MultipartFile file);
}
