package com.projectdemo.springbootdemo.test;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/10/23
 *
 * @author mjt
 */
public class POIReadExcel {

    public static void main(String[] args) {
        File f1 = new File("D:\\testydzq");
        File[] f = f1.listFiles();
        for (int i = 0; i < f.length; i++) {
            File file = f[i];
            System.out.println(file.getName());
        }


        //File file = new File("F://123.xlsx");


        //List<List<String>> strings = readExcel(file);
        //System.out.println(strings.toString());
        //writeExcel(strings);
    }

    private static void writeExcel(List<List<String>> strings) {
        /**
         *
         * [
         * [tbl_name, tbl_type, COLUMN_NAME, COL_COMMENT, REMARK],
         * [t_bjhg_dymx_his, MANAGED_TABLE, cjbh, 成交编号, 胜多负少],
         * [t_bjhg_dymx_his, MANAGED_TABLE, cjrq, 成交日期]
         * ]
         *
         *
         */

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        String[] split = strings.get(0).get(0).split(",");
        String sheetname = " ";
        if (split.length > 0 && split[1].equals("db_name")) {
            String[] split1 = strings.get(1).get(1).split(",");
            sheetname = split1[1];
        }
        // 创建第一个sheet，取db_name的内容
        XSSFSheet sheet = xssfWorkbook.createSheet(sheetname);
        // 创建第一行
        XSSFRow row = sheet.createRow(0);
        XSSFCell xssfCell = null;
        // 设置第一行内容
        for (int i = 0; i < split.length; i++) {
            xssfCell = row.createCell(i);
            xssfCell.setCellValue(split[i]);
        }
        // 创建第二行及以后行数
        for (int i = 1; i < strings.size(); i++) {
            XSSFRow nextRow = sheet.createRow(i);
            // 第二行及以后行数的数据
            List<String> stringCell = strings.get(i);
             // 创建当前行的单元格，并赋予内容
             for (int j = 0; j < stringCell.size(); j++) {
                 // 创建当前单元格
                 XSSFCell cell = nextRow.createCell(j);
                 // 赋予单元格内容
                 cell.setCellValue(stringCell.get(j));
             }
        }
        try {
            //创建一个文件
            File file=new File("F://test/"+sheetname+".xlsx");
            file.createNewFile();
            FileOutputStream stream= FileUtils.openOutputStream(file);
            xssfWorkbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *  需要注意的点：
     *  1：空单元格的处理。
     *  2：数值类型和日期类型的处理。
     *  花了三个小时。
     *
     * @param file
     */
    public static List<List<String>> readExcel(File file)  {
        List<List<String>> totalRowList = new ArrayList<>();
        try {
            if (!file.exists()) {
                System.out.println("文件不存在");
            }
            System.out.println("文件路径：" +  file.getParentFile() + ", " +
                    "文件绝对路径：" + file.getAbsolutePath() +", file.getabsolutefile()是： "+ file.getAbsoluteFile());
            //获取文件名字
            String fileName = file.getName();
            //获取文件类型
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(" **** fileType:" + fileType);

            //获取输入流
            InputStream stream = new FileInputStream(file);

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
            System.out.println(sheetName + " 表格中有" + lastRowNum +" 行 ");
            List<String> rowList = null;

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
                    // 遍历每一行中的每一列，获取到每个单元格中的值
                    for (int j = 3; j < lastCellNum; j++) {
                        XSSFCell cell = row.getCell(j);
                        // 处理空格
                        if (cell == null) {
                            cell = row.createCell(j);
                            cell.setCellValue("空单元格");
                        }
                        // 处理数据类型
                        CellType cellTypeEnum = cell.getCellType();
                        switch (cellTypeEnum) {
                            case STRING: // 字符串
                                String stringCellValue = cell.getStringCellValue();
                                if (stringCellValue == null) {
                                    stringCellValue = " ";
                                }
                                // todo 这个是字段数据表
                                if (j == 3 || j== 4 || j== 9 || j == 11 || j==14) {
                                    rowList.add(stringCellValue);
                                }
                                break;
                            case NUMERIC:// 数字
                                // 处理日期
                                if ( DateUtil.isCellDateFormatted(cell)) { // 判断该数值是否是日期
                                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                    Date javaDate = DateUtil.getJavaDate(cell.getNumericCellValue());
                                    String format = sdf.format(javaDate);
                                    //rowList.add(format);
                                } else {
                                    // 处理数字
                                    String cellValue = String.valueOf(cell.getNumericCellValue());
                                    if (cellValue == null) {
                                        cellValue = " ";
                                    }
                                    //rowList.add(cellValue);
                                    break;
                                }
                        }
                    }
                }
                totalRowList.add(rowList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return totalRowList;
    }

}
