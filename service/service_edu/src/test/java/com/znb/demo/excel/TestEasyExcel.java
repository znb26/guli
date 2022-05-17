package com.znb.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {
        //实现excel写操作
//        // 1.设置写入文件夹的地址和excel文件名称
//        String filaname = "F:\\write1.xlsx";
//
//        // 2. 调用easyexcel里面的方法实现写操作
//        EasyExcel.write(filaname,DemoData.class)
//                .sheet("学生列表")
//                .doWrite(getData());

        //实现excel读操作
        String filaname = "F:\\write1.xlsx";
        EasyExcel.read(filaname,DemoData.class,new ExcelListener())
                .sheet()
                .doRead();
    }

    // 创建方法 返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy" + i);
            list.add(demoData);
        }

        return list;
    }
}
