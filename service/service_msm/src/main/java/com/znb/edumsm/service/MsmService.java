package com.znb.edumsm.service;

import java.util.Map;

public interface MsmService {
    /**
     * 发送短信的方法
     */
    Boolean send(String code, String phone) throws Exception;
}
