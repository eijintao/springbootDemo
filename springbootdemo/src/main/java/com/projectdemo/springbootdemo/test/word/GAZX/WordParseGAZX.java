package com.projectdemo.springbootdemo.test.word.GAZX;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public class WordParseGAZX {

    private static Logger log = LogManager.getLogger(WordParseGAZX.class);

    public static void main(String[] args) {
        // 解析多个文档
        //File f1 = new File("D:\\testWord");
        //File[] f = f1.listFiles();
        //for (int i = 0; i < f.length; i++) {
        //    File file = f[i];
        //    System.out.println(file.getName());
        //    getContentDocx(file);
        //}
        // 解析单个文档
        File f2 = new File("E:\\至恒融兴项目熟悉流程\\2023\\20230323以后的excel集中地\\港澳资讯\\港澳资讯英大证券.doc");
        System.out.println(f2.getName());
        // 获取目录层级的表名
        Map<String, String> mapTab = wordTableParse(f2);
        //    获取所有的表格中的表和字段
        List<Map<String, Object>> wordTable = getWordTable(f2);
        System.out.println("wordTable: " + JSONObject.toJSONString(wordTable));
        // 写入excel
        writeExcel(mapTab, wordTable);

    }

    public static Map<String, String> wordTableParse(File file) {
        Map<String, String> map = new HashMap<>();
        InputStream is = null;
        //Map<Integer, String> mapHead = new HashMap<>();
        try {
            is = new FileInputStream(file);
            HWPFDocument document = new HWPFDocument(is);
            // 获取文档的整个范围
            Range range = document.getRange();
            // 遍历文档中的所有段落，查找可能的标题
            for (int i = 0; i < range.numParagraphs(); i++) {
                Paragraph para = range.getParagraph(i);
                // 这里只是一个简单的检查，实际的检查可能需要更复杂
                if (para.isInTable()) {
                    continue;
                }
                // 这里对当前段落进行检查。它首先获取段落的第一个字符运行（character run），并检查该字符运行是否是粗体（bold）。
                // 然后，它还检查字符运行的字体大小是否大于12，这里可能假设12是标题的最小字体大小
                if (para.getCharacterRun(0).isBold() && para.getCharacterRun(0).getFontSize() > 12) {
                    String text = para.text();
                    if (text.contains(":")) {
                        String[] split = text.split(":");
                        String replace = split[1].replaceAll("\r", "");
                        map.put(replace, split[0]);
                    }
                    System.out.println("Possible title: " + para.text());
                }
            }
            System.out.println(JSONObject.toJSONString(map));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("" + e);
                }
            }
        }
        return map;
    }

    private static List<Map<String, Object>> getWordTable(File file) {
        List<Map<String, Object>> list = new ArrayList<>();
        InputStream is = null;
        //Map<Integer, String> mapHead = new HashMap<>();
        try {

            is = new FileInputStream(file);
            HWPFDocument document = new HWPFDocument(is);
            // 获取文档的整个范围
            Range range = document.getRange();
            int count = 0;
            Map<String, Object> stringTab_ColMap = new HashMap<>();
            TableIterator tableIterator = new TableIterator(range);
            // 遍历文档中的所有表格
            while (tableIterator.hasNext()) {
                //list = new ArrayList<>();

                // 获取当前表格
                Table table = tableIterator.next();
                int tableLevel = table.getTableLevel();
                System.out.println("Table level: " + tableLevel);
                int i = table.numRows();
                System.out.println(" table.numRows(); " + i);
                List<Map<String, Object>> listCol = new ArrayList<>();
                Map<String, Object> map_col = null;
                List<String> stringList_firstRow = new ArrayList<>();
                // 遍历当前表格中的所有行
                for (int rowIndex = 0; rowIndex < table.numRows(); rowIndex++) {
                    map_col = new HashMap<>();
                    TableRow row = table.getRow(rowIndex);
                    if (row.isTableHeader()) {
                        //   该行是表头
                        System.out.println("Row " + rowIndex + " is table header");
                    }
                    // 遍历当前表格中的当前行中的所有单元格
                    for (int cellIndex = 0; cellIndex < row.numCells(); cellIndex++) {

                        TableCell cell = row.getCell(cellIndex);
                        StringBuilder cellTextBuffer = new StringBuilder();
                        for (int pIndex = 0; pIndex < cell.numParagraphs(); pIndex++) {
                            Paragraph para = cell.getParagraph(pIndex);
                            cellTextBuffer.append(para.text());
                        }
                        String cellText = cellTextBuffer.toString().replaceAll("\u0007", "");
                        if (cellText.startsWith("数据库对象名称")) {
                            String[] split = cellText.split("：");
                            String replaceCellText = split[1];
                            stringTab_ColMap.put("table_Name", replaceCellText);
                        }

                        if (rowIndex == 0 && cellText.contains("字段名称") || cellText.contains("序号") ||
                                cellText.contains("字段类型") || cellText.contains("备注") ||
                                cellText.contains("可否为空") || cellText.contains("中文名称")) {
                            stringList_firstRow.add(cellText);
                        }
                        if (CollectionUtil.isNotEmpty(stringList_firstRow) && rowIndex != 0) {
                            if (cellIndex == 0) {
                                map_col.put("序号", cellText);
                            }
                            if (cellIndex == 1) {
                                map_col.put("字段名称", cellText);
                            }
                            if (cellIndex == 2) {
                                map_col.put("中文名称", cellText);
                            }
                            if (cellIndex == 3) {
                                map_col.put("字段类型", cellText);
                            }
                            if (cellIndex == 4) {
                                map_col.put("备注", cellText);
                            }
                            if (cellIndex == 5) {
                                map_col.put("可否为空", cellText);
                            }
                        }
                        System.out.println("Cell " + rowIndex + "," + cellIndex + ": " + cellText);
                    }
                    if (!map_col.isEmpty()) {
                        listCol.add(map_col);
                    }
                    if (!CollectionUtils.isEmpty(listCol)) {
                        stringTab_ColMap.put("col_detail", listCol);
                    }
                }

                count++;
                System.out.println("count: " + count);
                if (count >= 2) {
                    count = 0;
                    stringTab_ColMap = new HashMap<>();
                } else {
                    list.add(stringTab_ColMap);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("" + e);
                }
            }
        }
        return list;
    }

    private static void writeExcel(Map<String, String> mapTab, List<Map<String, Object>> wordTable) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        // 创建第一个sheet，存放mapTab
        XSSFSheet sheet1 = xssfWorkbook.createSheet("表");
        // 创建第二个sheet，存放wordTable
        XSSFSheet sheet2 = xssfWorkbook.createSheet("字段");
        String[] headTitle = {"表名","表中文名"};
        // 开始操作mapTab,放进sheet1
        XSSFRow row1 = sheet1.createRow(0);
        XSSFCell xssfCell = null;
        // 设置第一行内容
        for (int i = 0; i < headTitle.length; i++) {
            xssfCell = row1.createCell(i);
            xssfCell.setCellValue(headTitle[i]);
        }
        // 开始填入数据
        // 创建第二行及以后行数
        int rowIndex = 1; // 用于追踪当前行数
        for (Map.Entry<String, String> entry : mapTab.entrySet()) {
            XSSFRow nextRow = sheet1.createRow(rowIndex);
            // 将mapTab的key和value放入列中
            XSSFCell keyCell = nextRow.createCell(0);
            keyCell.setCellValue(entry.getKey());
            XSSFCell valueCell = nextRow.createCell(1);
            valueCell.setCellValue(entry.getValue());
            rowIndex++;
        }

        // 在sheet2中创建标题行
        String[] colTitles = {"表名", "序号", "字段名称", "中文名称", "字段类型", "备注", "可否为空"};
        XSSFRow titleRow = sheet2.createRow(0);
        for (int i = 0; i < colTitles.length; i++) {
            titleRow.createCell(i).setCellValue(colTitles[i]);
        }

// 添加数据行
        int rowNum = 1; // 行号，从1开始，因为已经创建了标题行
        for (Map<String, Object> tableData : wordTable) {
            // 获取表名
            String tableName = (String) tableData.get("table_Name");
            // 获取该表的字段详情列表
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> colDetails = (List<Map<String, Object>>) tableData.get("col_detail");
            // 对于每个字段详情，创建一行
            for (Map<String, Object> colData : colDetails) {
                XSSFRow row2 = sheet2.createRow(rowNum++);
                int colNum = 0; // 列号，从0开始
                // 表名
                row2.createCell(colNum++).setCellValue(tableName);
                // 序号
                row2.createCell(colNum++).setCellValue((String) colData.get("序号"));
                // 字段名称
                row2.createCell(colNum++).setCellValue((String) colData.get("字段名称"));
                // 中文名称
                row2.createCell(colNum++).setCellValue((String) colData.get("中文名称"));
                // 字段类型
                row2.createCell(colNum++).setCellValue((String) colData.get("字段类型"));
                // 备注
                row2.createCell(colNum++).setCellValue((String) colData.get("备注"));
                // 可否为空
                row2.createCell(colNum++).setCellValue((String) colData.get("可否为空"));
            }
        }

        try {
            //创建一个文件
            File file = new File("D:\\testWord\\港澳资讯\\港澳资讯表和字段.xlsx");
            file.createNewFile();
            FileOutputStream stream = FileUtils.openOutputStream(file);
            xssfWorkbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
