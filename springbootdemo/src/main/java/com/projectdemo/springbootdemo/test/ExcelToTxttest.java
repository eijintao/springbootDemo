package com.projectdemo.springbootdemo.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * mjt 梅锦涛
 * 2023/5/29
 *
 * @author mjt
 */
public class ExcelToTxttest {



        public static void main(String[] args) {
            //String excelFilePath = "D:/testHT/exchangetest/exchangedate.xlsx"; // 替换为你的Excel文件路径
            String excelFilePath = "E:\\至恒融兴项目熟悉流程\\2023\\20230323以后的excel集中地\\衡泰债券\\信息补录.xlsx"; // 替换为你的Excel文件路径
            String txtFilePath = "E:\\至恒融兴项目熟悉流程\\2023\\20230323以后的excel集中地\\衡泰债券\\信息补录.txt"; // 替换为你要保存的文本文件路径

            try {
                Workbook workbook = new XSSFWorkbook(excelFilePath);
                Sheet sheet = workbook.getSheetAt(1); // 假设数据在第一个工作表中

                BufferedWriter writer = new BufferedWriter(new FileWriter(txtFilePath));

                for (Row row : sheet) {
                    Cell ocDateCell1 = row.getCell(14); // 假设oc_date数据在第2列中
                    Cell ocDateCell2 = row.getCell(15);
                    Cell ocDateCell3 = row.getCell(16);
                    Cell ocDateCell4 = row.getCell(17);

                        String ocDate = ocDateCell1.getStringCellValue();
                        String ocDate2 = ocDateCell2.getStringCellValue();
                        String ocDate3 = ocDateCell3.getStringCellValue();
                        String ocDate4 = ocDateCell4.getStringCellValue();
                        ocDate = ocDate + "  " + ocDate2 + "   " + ocDate3 + "    "  + ocDate4;
                        writer.write(ocDate);
                        writer.newLine();

                }

                writer.close();
                workbook.close();

                System.out.println("文本文件生成成功！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



}
