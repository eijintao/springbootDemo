package com.projectdemo.springbootdemo.test;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/10/25
 *
 * @author mjt
 */
public class FieldTable {

    

    private static Logger logger = LogManager.getLogger(FieldTable.class);

    public static void main(String[] args) {
        List<String> fileNameList = new ArrayList<>();
        File f1 = new File("D:\\testtablefield");
        File[] f = f1.listFiles();
        for (int i = 0; i < f.length; i++) {
            fileNameList.add(f[i].toString());
        }
        logger.info("所有文件名是：" + JSONObject.toJSONString(fileNameList) );
        logger.info("所有文件个数是：" + fileNameList.size() );

        Map<String, List<List<String>>> stringListMap = readExcel(fileNameList);
        //logger.info("stringListMap：" + JSONObject.toJSONString(stringListMap)  );

        writeExcel(stringListMap);
    }

    private static void writeExcel(Map<String, List<List<String>>> stringListMap) {
        List<String> keyStr = new ArrayList<>();
        // 获取key并存放key，用作sheetName
        for (String strKey : stringListMap.keySet()) {
            keyStr.add(strKey);
        }
        for (int k = 0; k < keyStr.size(); k++) {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            // 创建第一个sheet，取db_name的内容
            XSSFSheet sheet = xssfWorkbook.createSheet("模型");
            // 创建第一行
            XSSFRow row0 = sheet.createRow(0);
            // 创建第一行
            XSSFRow row1 = sheet.createRow(1);
            // 将字段数据表的第一行和第二行写死
            String[] titleRow1={"表名","表类型","表中文名","注释","标签"};
            String[] titleRow2={"最大长度为128字符(表名)，必填","表类型，必填","表中文名，非必填",
                    "对表的备注，非必填","填写已经存在的标签，多个标签以英文逗号','分隔 非必填"};
            XSSFCell cell0 = null;
            for (int i = 0; i < titleRow1.length; i++) {
                cell0=row0.createCell(i);
                cell0.setCellValue(titleRow1[i]);
            }
            XSSFCell cell1 = null;
            for (int i = 0; i < titleRow2.length; i++) {
                cell1=row1.createCell(i);
                cell1.setCellValue(titleRow2[i]);
            }
            // 开始操作stringListMap中的List
            for (int i = 0; i < stringListMap.size(); i++) {
                List<List<String>> lists = stringListMap.get(keyStr.get(k));
                // 行
                for (int j = 1; j < lists.size(); j++) {
                    List<String> stringCell = lists.get(j);
                    // 创建当前行
                    XSSFRow nextRow = sheet.createRow(j+1);
                    // 列
                    for (int l = 0; l < stringCell.size(); l++) {
                        // 创建当前单元格
                        XSSFCell cell = nextRow.createCell(l);
                        // 赋予单元格内容
                        cell.setCellValue(stringCell.get(l));
                    }
                }

            }
            try {
                //创建一个文件
                File file=new File("F:\\ydzq\\table\\"+keyStr.get(k));
                file.createNewFile();
                FileOutputStream stream = FileUtils.openOutputStream(file);
                xssfWorkbook.write(stream);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param fileNameList
     */
    public static Map<String, List<List<String>>> readExcel(List<String> fileNameList )  {
        //获取输入流
        InputStream stream = null;
        String fileName = null ;
        String fileType = null;
        Map<String, List<List<String>>> map = new HashMap<>();
        try {
            // 第一层循环，循环文件
            for (int k = 0; k < fileNameList.size(); k++) {
                List<List<String>> currentRowList = new ArrayList<>();
                String strFile = fileNameList.get(k);
                // 获取文件名
                fileName = strFile.substring(strFile.indexOf("表"));
                //获取文件类型
                fileType = fileName.substring(fileName.lastIndexOf(".") + 1);

                //获取输入流
                stream = new FileInputStream(strFile);

                //获取工作薄
                XSSFWorkbook xssfWorkbook = null;

                if (fileType.equals("xlsx")) {
                    xssfWorkbook = new XSSFWorkbook(stream);
                } else {
                    System.out.println("您输入的excel格式不正确，需要xlsx格式");
                }

                // 获得到Sheet
                XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
                // 获得到Sheet的名字
                String sheetName = sheetAt.getSheetName();
                // 判断sheet中有多少行数据
                int lastRowNum = sheetAt.getLastRowNum()+1;
                logger.info(fileName + "文件中有" + lastRowNum +"行");

                List<String> rowList = null;

                // 第二层循环，循环 行
                //  遍历每一行数据，获取HSSFRow对象
                for(int i = 0; i < lastRowNum; i++){
                    XSSFRow row = sheetAt.getRow(i);
                    if (row != null) {
                        //判断这行记录是否存在
                        if (row.getLastCellNum() < 1) {
                            continue;
                        }
                        // 获取每一行的列数
                        short lastCellNum = row.getLastCellNum();
                        //StringBuilder sb = new StringBuilder();
                        //获取每一行封装成对象
                        rowList = new ArrayList<String>();

                        // 第三层循环，循环 当前行的列
                        // 遍历每一行中的每一列，获取到每个单元格中的值
                        for (int j = 3; j < lastCellNum; j++) {
                            XSSFCell cell = row.getCell(j);
                            // 处理空格
                            if (cell == null) {
                                cell = row.createCell(j);
                                cell.setCellValue("");
                            }
                            // 处理数据类型
                            CellType cellTypeEnum = cell.getCellType();
                            switch (cellTypeEnum) {
                                case STRING: // 字符串
                                    String stringCellValue = cell.getStringCellValue();
                                    if (stringCellValue == null) {
                                        stringCellValue = " ";
                                    }
                                    // todo 这个是数据表
                                    if (j == 4 || j== 5 || j== 8 || j == 13 ) {
                                        rowList.add(stringCellValue);
                                    }
                                    break;
                                case NUMERIC:// 数字
                                    break;
                            }
                        }
                    }
                    currentRowList.add(rowList);
                }
                map.put(fileName,currentRowList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
