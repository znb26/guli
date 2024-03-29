package com.znb.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {

    // 一行一行读
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println("****" + demoData);
    }

    //读取表头
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
