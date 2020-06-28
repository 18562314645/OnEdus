package com.root.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.root.utils.GuliException;
import com.root.utils.R;
import com.root.vod.service.VodService;
import com.root.vod.utils.ConstantPropertiesUtil;
import com.root.vod.utils.InitVodObject;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService{
    /**
     * 上传视频到阿里云
     * @param file
     * @return
     */
    @Override
    public String uploadVideo(MultipartFile file) {

        try {
            //输入流
            InputStream inputStream = file.getInputStream();
            //文件名
            String fileName = file.getOriginalFilename();

            //上传后的title
            String title=fileName.substring(0,fileName.lastIndexOf("."));

            UploadStreamRequest request = new UploadStreamRequest(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET, title, fileName, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId=null;
            if (response.isSuccess()) {
                videoId=response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId=response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据多个视频id删除阿里云视频
     * @param videoIdList
     */
    @Override
    public void deleteListId(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodObject.initVodClient(ConstantPropertiesUtil.KEY_ID, ConstantPropertiesUtil.KEY_SECRET);

            //创建request对象
            DeleteVideoRequest request=new DeleteVideoRequest();

            //将cideoListId转换成1，2，3格式的

            String ids = StringUtils.join(videoIdList.toArray(), ",");

            //设置id
            request.setVideoIds(ids);
            //调用方法删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001,"视频删除失败");
        }
    }


   /* public static void main(String[] args) {
        List list=new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
       *//* StringBuilder builder=new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i==list.size()-1){
                builder.append(list.get(i));
            }else {
                builder.append(list.get(i)+",");
            }

        }*//*
       //方法二
        String join = StringUtils.join(list.toArray(), ",");
        System.out.println(join);
    }*/

}


