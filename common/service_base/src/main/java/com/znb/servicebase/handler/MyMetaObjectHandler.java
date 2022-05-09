package com.znb.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;
import org.joda.time.DateTime;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 类中的属性名称
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    public static void main(String[] args) {
        DateTime dateTime = new DateTime();
        LocalDateTime localDateTime = new LocalDateTime();
        System.out.println(localDateTime);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
