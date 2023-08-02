package com.projectdemo.springbootdemo.test.hso32;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/10/23
 *
 * @author mjt
 */
public class POIReadAndWriteExcel {

    public static void main(String[] args) {
        File f1 = new File("D:\\2023\\07\\恒生o32字段处理.xlsx");
        File[] f = f1.listFiles();
        File file = null;
        if (f != null) {
            for (int i = 0; i < f.length; i++) {
                file = f[i];
                System.out.println(file.getName());
            }
        } else {
            System.out.println(f1.getName());
        }

        //File file = new File("F://123.xlsx");
        List<List<String>> stringMap = null;
        if (f != null) {
             stringMap = readExcel(file);
        } else {
             stringMap = readExcel(f1);
        }
        System.out.println(JSONObject.toJSONString(stringMap));
        // 获得去重后的数据
        LinkedHashSet<List<String>> linkedHashSet = handleReadList(stringMap);
        System.out.println("去重后的数据长度：" + linkedHashSet.size());
        // 用去重后的数据匹配原数据，获得原数据中的注释
        LinkedHashSet<List<String>> list = handleList(linkedHashSet, stringMap);
        System.out.println("合并注释后的数据长度：" + list.size());
        // 将list中的数据按照顺序加入到linkedHashSet的顺序中
        LinkedHashSet<List<String>> listLinkedHashSet = handleListLinkedHashSet(linkedHashSet, list);
        System.out.println("合并注释后的数据长度：" + listLinkedHashSet.size());
        // 处理 listLinkedHashSet 中的数据
        LinkedHashSet<List<String>> linkedHashSet1 = handleData(listLinkedHashSet);
        System.out.println("处理后的数据长度：" + linkedHashSet1.size());

        // 将数据写入到excel中

        writeExcel(linkedHashSet1);

    }

    private static LinkedHashSet<List<String>> handleData(LinkedHashSet<List<String>> listLinkedHashSet) {

        for (List<String> subList : listLinkedHashSet) {
            if (subList.size() >= 5) { // 检查是否存在第四个元素
                String thirdItem = subList.get(3).replace("?", ""); // 去除第三个位置的问号
                String fourthItem = subList.get(4).replace("?", ""); // 去除第四个位置的问号

                // 比较第三个位置和第四个位置的元素的长度，并保留长度最长的
                if (thirdItem.length() >= fourthItem.length()) {
                    // 如果第三个元素长度大于等于第四个元素长度，删除第四个元素
                    subList.remove(4);
                } else {
                    // 如果第四个元素长度大于第三个元素长度，删除第三个元素
                    subList.remove(3);
                }
            }
        }
        System.out.println("&&&&&&&&&&&&&&&&&&&&&&");
        // 打印最终的列表
        //System.out.println(JSONObject.toJSONString(listLinkedHashSet));
        return listLinkedHashSet;
    }

    private static LinkedHashSet<List<String>> handleListLinkedHashSet(LinkedHashSet<List<String>> linkedHashSet, LinkedHashSet<List<String>> list) {
        // 将list中的数据按照顺序加入到linkedHashSet的顺序中

        List<List<String>> linkedHashSetList = new ArrayList<>(linkedHashSet);
        List<List<String>> listList = new ArrayList<List<String>>(list);

        // 按顺序将需要添加的数据加入到原始列表中
        for (int i = 0; i < linkedHashSetList.size() && i < listList.size(); i++) {
            for (String item : listList.get(i)) {
                linkedHashSetList.get(i).add(item);
            }
        }
        System.out.println("=================");
        //System.out.println(JSONObject.toJSONString(linkedHashSetList));
        LinkedHashSet<List<String>> listLinkedHashSet = new LinkedHashSet<>(linkedHashSetList);
       return listLinkedHashSet;
    }

    private static LinkedHashSet<List<String>> handleList(LinkedHashSet<List<String>> linkedHashSet, List<List<String>> stringMap) {
        LinkedHashSet<List<String>> list = new LinkedHashSet<>();
        // 去重后的数据
        for (List<String> strings : linkedHashSet) {
            List<String> list1 = new ArrayList<>();
            // 原数据
            for (List<String> stringList : stringMap) {
                // 去重后的数据中的第一个元素，即表名
                if (stringList.get(0).equals(strings.get(0)) && stringList.get(1).equals(strings.get(1)) && stringList.get(3).equals(strings.get(2))) {
                    list1.add(stringList.get(2));
                }
            }
            list.add(list1);
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        //System.out.println(JSONObject.toJSONString(list));

        return list;
    }

    private static  LinkedHashSet<List<String>> handleReadList(List<List<String>> stringMap) {
        List<List<String>> list = new ArrayList<>();
        List<String> listTabName = new ArrayList<>();

        List<List<String>> objects33 = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        // 循环 stringMap
        for (int i = 0; i < stringMap.size(); i++) {
            // 获得 List<Object>
            List<String> objects1 = stringMap.get(i);
            // 存储新的 List<Object>
            List<String> objects22 = new ArrayList<>();
            // 循环 List<Object>
            for (int j = 0; j < objects1.size(); j++) {
                // 获取 List<Object> 中的第四个元素，即表名
                //String  tabName = (String)objects1.get(3);
                if ( j == 0 || j == 1 || j == 3) {
                    objects22.add(objects1.get(j));
                }

            }
            objects33.add(objects22);
        }
        LinkedHashSet<List<String>> linkedHashSet = new LinkedHashSet<>(objects33);
        System.out.println("________________");
        //System.out.println(JSONObject.toJSONString(linkedHashSet));

        return linkedHashSet;

    }

    private static void writeExcel(LinkedHashSet<List<String>>  stringssss) {
        List<List<String>> strings = new ArrayList<>(stringssss);
        /**
         *[
         *     [
         *         "VC_ISIN",
         *         "ISIN编码",
         *         "THISSETTLEINSBONDVC_ISIN",
         *         "1ISINIdentifier\n\n描述：债券的ISIN编码。\n数据类型：ISINIdentifier\n格式：[A-Z0-9]{12,12}"
         *     ],
         *     [
         *         "C_STATUS",
         *         "状态",
         *         "THISSETTLEINSTRUCTIONC_STATUS",
         *         "1-待修改；2-待复核；3-复核通过；4-复核拒绝；5-无需复核；6-已撤销； 7 - 待撤"
         *     ]
         * ]
         *
         *
         */

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        String[] split = {"FIELD_NAME","FILED_DESC", "TABNAME", "FILED_MEMO","更新语句"};
        String sheetname = "字段信息";

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
            List<String> stringCell = strings.get(i-1);
            if (stringCell.size() == 4) {
             // 创建当前行的单元格，并赋予内容
                 for (int j = 0; j <= stringCell.size() ; j++) {
                     // 创建当前单元格
                     XSSFCell cell = nextRow.createCell(j);
                     // 赋予单元格内容
                     //cell.setCellValue(stringCell.get(j));
                     if (j == 4) {
                         String s = "UPDATE tn_meta_dmod_col_info  c\n" +
                                 "SET\n" +
                                 "`CUSTOM` = '"+ stringCell.get(1) +"', `REMARK` = '"+ stringCell.get(3) +"' \n" +
                                 "WHERE\n" +
                                 " c.DMOD_EN_NAME =  ( SELECT b.DMOD_EN_NAME  FROM tn_meta_dmod_info b WHERE b.ONS_DB = 'HS_O32_FMFADB'  and b.ONS_OWN = 'TRADE'  and b.DMOD_PURE_NAME = '"+stringCell.get(2)+"'   )\n" +
                                 " and c.COL_EN_NAME = '"+stringCell.get(0)+"';";
                         cell.setCellValue(s);
                     } else {
                         cell.setCellValue(stringCell.get(j));
                     }
                 }
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

        //获取输入流
        InputStream stream = null;
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

            stream = new FileInputStream(file);

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
            //int lastRowNum = sheetAt.getLastRowNum()+1;
            int rowCount = sheetAt.getPhysicalNumberOfRows();
            //System.out.println(sheetName + " 表格中有" + lastRowNum +" 行 ");
            System.out.println(sheetName + " 表格中有" + rowCount +" 行 ");
            //获取每一行封装成对象
            List<String> rowList = null;

            //  遍历每一行数据，获取HSSFRow对象
            for(int i = 1; i < rowCount; i++){
                rowList = new ArrayList<String>();
                XSSFRow row = sheetAt.getRow(i);
                if (row != null) {
                    //判断这行记录是否存在
                    if (isEmptyRow(row)) {
                        continue;
                    }

                    // 获取每一行的列数
                    short lastCellNum = row.getLastCellNum();
                    //StringBuilder sb = new StringBuilder();

                    Map<String, String> map = new HashMap<>();
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
                                //if (j == 2 ) {
                                //    String  cellvalue2 = cell.getStringCellValue();
                                //    if (StringUtils.isBlank(cellvalue2)) {
                                //        cellvalue2 = "";
                                //    }
                                //   map.put("remark", cellvalue2);
                                //} else if (j == 3 ) {
                                //    String   cellvalue3 = cell.getStringCellValue();
                                //    map.put("tableName", cellvalue3);
                                //    rowList.add(map);
                                //} else {
                                    rowList.add(stringCellValue);
                                //}
                                break;
                            case BLANK:

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
        } finally {
            // 关闭流
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return totalRowList;
    }



    public static boolean isEmptyRow(XSSFRow row) {
        if (row == null) {
            return true;
        }

        /**
         * 在Apache POI库中，Cell.CELL_TYPE_BLANK是一个标识符，用来表示一个单元格是空的，也就是说没有任何数据。
         *
         * cell.getCellType()方法返回单元格的数据类型。这些类型包括CELL_TYPE_NUMERIC（数字）、CELL_TYPE_STRING（字符串）、CELL_TYPE_FORMULA（公式）、
         * CELL_TYPE_BLANK（空）、CELL_TYPE_BOOLEAN（布尔值）和CELL_TYPE_ERROR（错误）。
         *
         * 所以，表达式cell.getCellType() != Cell.CELL_TYPE_BLANK的意思是：如果单元格的类型不是空（即，它包含一些数据），那么返回true。
         */
        //for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
        //    Cell cell = row.getCell(i);
        //    if (cell != null && cell.getCellType() != CellType.BLANK && StringUtils.isNotBlank(cell.toString())) {
        //        return false;
        //    }
        //}

        return false;
    }


}
