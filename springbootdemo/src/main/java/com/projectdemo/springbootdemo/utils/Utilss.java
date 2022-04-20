package com.projectdemo.springbootdemo.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/4/20
 *
 * @author mjt
 */
public class Utilss {


    public static <T>List<T> readExcel(File file, Map<String, String> alias, Class<T> record) {
        ExcelReader reader = ExcelUtil.getReader(file);
        alias.entrySet().stream().forEach(entry -> reader.addHeaderAlias(entry.getKey(),entry.getValue()));
        List<T> ts = reader.readAll(record);
        reader.close();
        return ts;
    }


    /**
     * 一次性快速读取excel，虽然设置了最大值，但是当excel过大时，有内存溢出风险
     * @param in
     * @param header
     * @param headerIndex
     * @return
     */
    public static List<Map<String, Object>> excelToBeanList (InputStream in, Map<String, String> header, int headerIndex) {
        List<Map<String, Object>> list = new ArrayList<>();
        // 注意这个0 ，这里指的是读取第一个sheet，如果是读取第二个，那么就是1
        ExcelReader reader = ExcelUtil.getReader(in, 0);
        // 设置map ， 启动别名
        reader.setHeaderAlias(header);
        // headerIndex:哪一行为标题。headerIndex + 1: 标题下一行开始读取数据。 Integer.MAX_VALUE：读到哪一行，这里默认不读空行
        List<Map<String, Object>> read = reader.read(headerIndex, headerIndex + 1, Integer.MAX_VALUE);
       /* for (Map<String, Object> stringObjectMap : read) {
            list.add(BeanUtil.mapToBean(stringObjectMap,tClass, false));
        }*/
        return read;
    }

}
