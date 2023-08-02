package com.projectdemo.springbootdemo.test.excelSixMonthFiveDayAfter;

import cn.hutool.core.lang.hash.Hash;
import com.alibaba.fastjson.JSONObject;
import jdk.internal.org.objectweb.asm.Handle;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * mjt 梅锦涛
 * 2023/6/5
 *
 * @author mjt
 */
public class TableReaderWriterTest {

    public static void main(String[] args) {

    //    D:\testExcel\20230605TestExcel\gszg.xlsx
        String str = "D:\\testExcel\\20230605TestExcel\\gszg.xlsx";
        File file = new File(str);
        // 该代码创建了一个try-with-resources块，使用FileInputStream从文件中打开输入流。这确保在使用后自动关闭流，即使发生异常也是如此
        try(InputStream inputStream = new FileInputStream(file);) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // 获取总sheet数
            int numberOfSheets = workbook.getNumberOfSheets();
            System.out.println("numberOfSheets = " + numberOfSheets);
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                System.out.println("sheetName = " + sheetName);

                if (sheetName.equals("11")) {
                    getSheetData(sheet,sheetName);
                } else {
                    getSheetData(sheet,sheetName);
                }
            }


        } catch (FileNotFoundException e) {
            System.err.println("文件未找到：" + str);
        }  catch (IOException e) {
            System.err.println("读取文件时发生错误：" + str);
        }
    }

    private static void getSheetData(Sheet sheet,String sheetName) {
        // 获取sheet总行数
        int rows = sheet.getPhysicalNumberOfRows();
        System.out.println("rows = " + rows);
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (int j = 0; j < mergedRegions.size(); j++) {
            int firstRow = mergedRegions.get(j).getFirstRow();
            int lastRow = mergedRegions.get(j).getLastRow();
            //System.out.println("firstRow = " + firstRow + 1);
            //System.out.println("lastRow = " + lastRow + 1);

            int firstColumn = mergedRegions.get(j).getFirstColumn();
            int lastColumn = mergedRegions.get(j).getLastColumn();

            // 打印合并区域的位置信息
            System.out.println("合并区域：第" + firstRow + "行至第" + lastRow + "行，第" + firstColumn + "列至第" + lastColumn + "列");

            // 遍历合并区域内的单元格
            for (int row = firstRow; row <= lastRow; row++) {
                for (int column = firstColumn; column <= lastColumn; column++) {
                    // 获取合并区域内的单元格
                    Cell cell = sheet.getRow(row).getCell(column);

                        String cellValue = cell.toString();
                        System.out.println("合并单元格内容：" + cellValue);
                        // 获取当前行中所有列
                        Row row1 = sheet.getRow(row);
                        //short lastCellNum = row1.getLastCellNum();
                        //System.out.println("lastCellNum = " + lastCellNum);

                            Cell cell1 = row1.getCell(1);
                            Cell cell2 = row1.getCell(2);
                            String cellValueTableNameCN = cell1.getStringCellValue();
                            String cellValueTableNameEN = cell2.getStringCellValue();
                            System.out.println("cellValue = " + cellValueTableNameEN);
                            System.out.println("cellValue = " + cellValueTableNameCN);

                            System.out.println("UPDATE `alioth_ydzq_testing_environment`.`tn_meta_dmod_info` " +
                                    " SET " +
                                    "`CUSTOM` = "+ cellValueTableNameCN +"" +
                                    "WHERE" +
                                    "  and ONS_DB = 'ht_zq_gs'     and DMOD_PURE_NAME = "+ cellValueTableNameEN   +";");
                            System.out.println();
                            System.out.println(" UPDATE `alioth_ydzq_testing_environment`.`tn_meta_dmod_info` " +
                            " SET " +
                            "`CUSTOM` = "+ cellValueTableNameCN +"" +
                                    " WHERE " +
                            "and ONS_DB = 'ht_zq_zg'     and DMOD_PURE_NAME =  "+ cellValueTableNameEN +";");


                }
            }
        }
    }

    public static boolean isexist(String strTableNameEN) {
        //     D:\testExcel\20230605TestExcel\需要比对有无的243张表.xlsx

        String excelFilePath = "D:\\testExcel\\20230605TestExcel\\需要比对有无的243张表.xlsx"; // 替换为你的Excel文件路径

        Map<String, Integer> map = new LinkedHashMap<>();
        try {
            Workbook workbook = new XSSFWorkbook(excelFilePath);
            Sheet sheet = workbook.getSheetAt(0); // 假设数据在第一个工作表中


            // 获取sheet总行数
            int rows = sheet.getPhysicalNumberOfRows();
            System.out.println("rows = " + rows);
            for (int i = 0; i < rows; i++) {
                Row row = sheet.getRow(i);
                // 获取每行的总列数
                int cells = row.getPhysicalNumberOfCells();
                System.out.println("cells = " + cells);
                for (int j = 0; j < cells; j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = cell.getStringCellValue();
                    System.out.println("cellValue = " + cellValue);
                    map.put(cellValue, i);
                }

            }

            workbook.close();

            String mapToJsonString = JSONObject.toJSONString(map);
            System.out.println("mapToJsonString = " + mapToJsonString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if ( map != null && map.size() > 0 && StringUtils.isNotBlank(strTableNameEN)) {
            Integer integer = map.get(strTableNameEN.trim().toUpperCase());
            if (integer != null && integer >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            System.out.println("strTableNameEN = " + strTableNameEN + " 为空，请检查您的excel");
        }

        return  false;

    }







}
