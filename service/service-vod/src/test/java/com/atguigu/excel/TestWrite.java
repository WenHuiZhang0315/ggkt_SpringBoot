package com.atguigu.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shkstart
 * @create 2022-10-13 11:26
 */
public class TestWrite {

    public static void main(String[] args) {
        String fileName = "D://aa.xlsx";
        EasyExcel.write(fileName,User.class)
                .sheet("写操作")
                .doWrite(data());
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<User> data() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User data = new User();
            data.setId(i);
            data.setName("葛坤杰"+i);
            list.add(data);
        }
        return list;
    }
}
