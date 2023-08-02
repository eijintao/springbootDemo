package com.projectdemo.springbootdemo.test.word.GAZX;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        File f2 = new File("D:\\testWord\\表详细设计two.docx");
        //getContentDocx(f2);
        Map<String, List<List<String>>> integerListMap = wordTableParse(f2);
        writeExcel(integerListMap);
    }

    public static Map<String, List<List<String>>> wordTableParse(File file){
        InputStream is = null;
        //List<String> listRow =  new ArrayList<>();
        //List<List<String>> listRows = new ArrayList<>();
        //List<List<List<String>>> listTables = new ArrayList<>();
        Map<String, List<List<String>>> mapList = new HashMap<>();
        //Map<Integer, String> mapHead = new HashMap<>();
        List<String> listHead =  new ArrayList<>();
        try {
            is = new FileInputStream(file);
            XWPFDocument xwpfDocument = new XWPFDocument(is);
            List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
            if (paragraphs != null && paragraphs.size() > 0) {
                for (int i = 0; i < paragraphs.size(); i++) {
                    XWPFParagraph xwpfParagraph = paragraphs.get(i);
                    // 获取当前行，当前行是  客户信息(t_khxx) 这种结构，会形成四个字符串。"客户信息" "(" "t_khxx" ")"
                    List<XWPFRun> runs = xwpfParagraph.getRuns();
                    if (runs != null && runs.size() >= 1) {
                        // 获取到 t_khxx
                        String text = xwpfParagraph.getRuns().get(0).getText(0);
                        listHead.add(text);
                    }
                }

            }
            //根据解析文档我们需要获取文档中第一个表格
            List<XWPFTable> xwpfTables = xwpfDocument.getTables();
            // 当前数据获取到当前有多少个表格 就有多少个 标头
            for (int i = 0; i < listHead.size(); i++) {
                XWPFTable xwpfTable = xwpfTables.get(i);
                List<XWPFTableRow> rows = xwpfTable.getRows();
                List<List<String>> listTables = new ArrayList<>();
                // 表格中有多少个行
                for (int j = 0; j < rows.size(); j++) {
                    XWPFTableRow xwpfTableRow = rows.get(j);
                    List<String> listRows =  new ArrayList<>();
                    // 一行有多少个列
                    List<XWPFTableCell> tableCells = xwpfTableRow.getTableCells();
                    for (int k = 0; k < tableCells.size(); k++) {
                        XWPFTableCell xwpfTableCell = tableCells.get(k);
                        String text = xwpfTableCell.getText();
                        listRows.add(text);
                        System.out.println(text);
                    }
                    listTables.add(listRows);
                }
                mapList.put(listHead.get(i), listTables);
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
        return mapList;
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
            XSSFSheet sheet = xssfWorkbook.createSheet();
            // 开始操作stringListMap中的List
            for (int i = 0; i < stringListMap.size(); i++) {
                List<List<String>> lists = stringListMap.get(keyStr.get(k));
                // 行
                for (int j = 0; j < lists.size(); j++) {
                    List<String> stringCell = lists.get(j);
                    // 创建当前行
                    XSSFRow nextRow = sheet.createRow(j);
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
                File file=new File("d:\\testWord\\"+keyStr.get(k) + ".xlsx");
                file.createNewFile();
                FileOutputStream stream = FileUtils.openOutputStream(file);
                xssfWorkbook.write(stream);
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
