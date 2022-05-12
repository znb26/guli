package com.znb.oss.service.impl;

import com.znb.oss.service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;


import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-hangzhou.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "";
        String accessKeySecret = "";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "edu-guli-znb";

        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        //String filePath= "D:\\localpath\\examplefile.txt";
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        //String objectName = "exampledir/exampleobject.txt";
        // 获取文件名称
        String objectName = file.getOriginalFilename();

        //要求使得文件名称不同
        String s = UUID.randomUUID().toString().replaceAll("-", "");
        objectName = s + objectName;

        //把文件按照日期分类
        //获取当前日期
        String datePath = new DateTime().toString("yyyy/MM/dd");

        objectName = datePath + "/" + objectName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            // 创建PutObject请求。
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        // 返回上传路径
        String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
        // https://edu-guli-znb.oss-cn-hangzhou.aliyuncs.com/1.jpg
        return url;
    }

    public static void main(String[] args) {
        String s = new DateTime().toString("yyyy/MM/dd");
        System.out.println(s);
    }
}
