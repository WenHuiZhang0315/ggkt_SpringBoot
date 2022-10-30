package com.atguigu.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * @author shkstart
 * @create 2022-10-13 11:35
 */
public class ExcelListenter extends AnalysisEventListener<User> {

//    一行一行读取excel内容，每行内容封装到user对象
//    从excel第二行开始读取
    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        System.out.println(user);
    }

//    读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头是"+headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
