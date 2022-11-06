package com.projectdemo.springbootdemo.test;

import java.io.File;

/**
 * asus 梅锦涛
 * 2022/11/2
 *
 * @author mjt
 */
public class StatTableFiled {

    /**
     * 统计 excel中的表，字段，还有自带的系统
     *
     *
     *
     */
    public static void main(String[] args) {
        File f1 = new File("F:\\林文峰的英大文件/处理数据类的excel/原始表和字段经过sql过滤后的数据--至恒融兴(2)/至恒融兴/36个数据/表数据");
        File[] f = f1.listFiles();
        int count = 0;
        for (int i = 0; i < f.length; i++) {
            if (f[i].toString().contains("ods")) {
                String s = f[i].toString();
                int ju = s.lastIndexOf("据");
                String substring = s.substring(ju + 1);
                count++;
                System.out.println(substring);

            }
        }
        System.out.println(count);
    }



}
