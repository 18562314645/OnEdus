package com.root.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.root.oss.service.FileService;
import com.root.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传阿里的实现类
 */
@Service
public class fileServiceImpl implements FileService {
    @Override
    public String upLoad(MultipartFile file) {
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String url=null;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //获取文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //构建日期格式
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //构建文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName=uuid+fileName;
            fileName=datePath+"/"+fileName;
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, fileName, inputStream);

            url="https://"+bucketName+"."+endpoint+"/"+fileName;
            // 关闭OSSClient。
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return url;
    }
}
