package com.nowcoder.weibo.service;

import com.alibaba.fastjson.JSONObject;

import com.nowcoder.weibo.util.WeiboUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by nowcoder on 2016/7/7.
 */
@Service
public class QiniuService {
    private static final Logger logger = LoggerFactory.getLogger(QiniuService.class);

    String ACCESS_KEY = "6-wu02ScMJ8gIMdSIhxlQCuLJM0IPeM56UxYdVKZ";
    String SECRET_KEY = "GXUEf0NM2JEwVbixa0nRbppHhQD8a2BmiD-GOptA";

    String bucketname = "weibo";


    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

    UploadManager uploadManager = new UploadManager();

    private static String QINIU_IMAGE_DOMAIN = "http://otlikf27l.bkt.clouddn.com/";


    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    public String saveImage(MultipartFile file) throws IOException {
        try {
            int dotPos = file.getOriginalFilename().lastIndexOf(".");
            if (dotPos < 0) {
                return null;
            }
            String fileExt = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
            if (!WeiboUtil.isFileAllowed(fileExt)) {
                return null;
            }

            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileExt;
            //调用put方法上传
            Response res = uploadManager.put(file.getBytes(), fileName, getUpToken());
            //打印返回的信息
            System.out.println(res.bodyString());
            return null;
        } catch (QiniuException e) {
            // 请求失败时打印的异常的信息
            logger.error("七牛异常:" + e.getMessage());
            return null;
        }
    }

}