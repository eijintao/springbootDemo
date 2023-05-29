package com.projectdemo.springbootdemo.test.word;

import com.alibaba.fastjson.JSONObject;
import com.projectdemo.springbootdemo.test.YdzqExcelTableReadAndWrite;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.util.FileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mjt 梅锦涛
 * 2023/1/16
 *
 * @author mjt
 */
public class TextSql{

    private static Logger logger = LogManager.getLogger(TextSql.class);


    public static void main(String[] args) {

        List<String> fileNameList = new ArrayList<>();
        File f1 = new File("C:\\Users\\mjt\\Desktop\\测试所用\\excel\\exchangedate.xlsx");
        String locations = "D:\\testHT\\";
        File[] f = f1.listFiles();
        for (int i = 0; i < f.length; i++) {
            fileNameList.add(f[i].toString());
        }
        logger.info("所有文件名是：" + JSONObject.toJSONString(fileNameList) );
        logger.info("所有文件个数是：" + fileNameList.size() );

        Map<String, List<List<String>>> stringListMap = readExcel(fileNameList);
        writetoTxt(locations,stringListMap);

    }

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
                // 获取文件名
                String strFile = fileNameList.get(k);
                fileName = strFile.substring(strFile.lastIndexOf(File.separator)+1);
                int i1 = fileName.indexOf("(");
                int i2 = fileName.lastIndexOf(")");
                fileName = fileName.substring(i1+1,i2);
                //获取文件类型
                fileType = strFile.substring(strFile.lastIndexOf(".") + 1);

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
                for(int i = 1; i < lastRowNum; i++){
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
                        for (int j = 0; j < lastCellNum; j++) {
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
                                    System.out.println(stringCellValue);
                                    rowList.add(stringCellValue);
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



    public static void writetoTxt(String locations, Map<String, List<List<String>>> stringListMap) {
        /*
         * update tn_meta_dmod_col_info set COL_CN_NAME = 'xxx'
         *  ,CUSTOM = 'xxx' where DMOD_EN_NAME = 'HIVE_METASTORE.adp_dm.IDS_T_FP_TZSY' and COL_EN_NAME = 'KCRQ'
         *
         * */

        //String strSql = null;
        String s0 = null;
        String s1 = null;

        for (Map.Entry<String, List<List<String>>> s : stringListMap.entrySet()) {
            String fileNam = s.getKey();
            //filename = filename + File.separator + fileNam + ".xlsx";
            StringBuffer str = new StringBuffer();
            List<List<String>> value = s.getValue();
            for (int i = 0; i < value.size(); i++) {
                List<String> stringList = value.get(i);
                for (int j = 0; j < stringList.size(); j++) {
                    if ( j == 0 || j == 1) {
                        //StringBuffer str = new StringBuffer();
                        s0 = stringList.get(0);
                        s1 = stringList.get(1);
                        if (j == 0) {
                            continue;
                        }
                        if (s0 !=null && s1 != null) {
                            str.append(" update tn_meta_dmod_col_info set COL_CN_NAME =  '" + s0  + "' ,CUSTOM =  '" + s0 +"' "  );
                            str.append("  where DMOD_EN_NAME = 'HIVE_METASTORE.adp_dm." + fileNam + "'  and COL_EN_NAME =  '" + s1  + "'; "  );
                            str.append("\t\n");
                        }

                    }
                }
            }
            //locations = locations + fileNam + ".txt";
            String s2 = locations + fileNam + ".txt";

            try {
                BufferedWriter out  = new BufferedWriter(new FileWriter(new File(s2)));
                out.write(String.valueOf(str));
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


}
