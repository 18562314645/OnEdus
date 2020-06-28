package com.root.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class ConstantPropertiesUtil implements InitializingBean{

    @Value("${aliyun.vod.file.keyid}")
    private String keyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String KEY_ID;
    public static String KEY_SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=keyId;
        KEY_SECRET=keysecret;
    }
}
