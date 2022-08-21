package com.znb.edumsm.service.impl;

import com.znb.edumsm.service.MsmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.aliyun.dysmsapi20170525.models.*;
import com.aliyun.teaopenapi.models.*;
import com.aliyun.teautil.models.*;

@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 发送短信的方法
     */
    @Override
    public Boolean send(String code, String phone) throws Exception {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        com.aliyun.dysmsapi20170525.Client client = createClient("", "");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers(phone)
                .setTemplateParam("{'code':'" + code + "'}");
        RuntimeOptions runtime = new RuntimeOptions();
        // 复制代码运行请自行打印 API 的返回值
        client.sendSmsWithOptions(sendSmsRequest, runtime);
        return true;
    }

    /**
     * 使用AK&SK初始化账号Client
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public com.aliyun.dysmsapi20170525.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 您的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new com.aliyun.dysmsapi20170525.Client(config);
    }


}
