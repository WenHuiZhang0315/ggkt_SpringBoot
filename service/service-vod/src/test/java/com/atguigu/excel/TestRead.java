package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

/**
 * @author shkstart
 * @create 2022-10-13 11:41
 */
public class TestRead {

    public static void main(String[] args) {
//        设置文件名称路径
        String fileName = "D://aa.xlsx";
//        调用方法进行读操作
        EasyExcel.read(fileName,User.class,new ExcelListenter()).sheet().doRead();
    }
}
