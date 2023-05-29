package com.projectdemo.springbootdemo.test.pdf;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

/**
 * mjt 梅锦涛
 * 2023/2/22
 *
 *  解决： 解析pdf时，是单层还是双层的代码。 至于什么是单层和双层，请搜索之
 *
 * @author mjt
 */
public class OneLayerAndTwoLayer {

    public static void main(String[] args) throws IOException {
        //String file="src/main/resources/正确扫描件.pdf";
        String file="src/main/resources/规章制度.pdf";
        // Load the PDF document
        PDDocument document = PDDocument.load(new File(file));

        // Get the number of pages in the document
        int pageCount = document.getNumberOfPages();

        // Check if the PDF document is single or multiple layer
        boolean isSingleLayer = true;
        for (int i = 0; i < pageCount; i++) {
            PDPage page = document.getPage(i);
            Iterable<COSName> extGStateNames = page.getResources().getExtGStateNames();
            boolean isHaveExtGState = extGStateNames.iterator().hasNext();
            if (isHaveExtGState){
                isSingleLayer=false;
                break;
            }
//            if (page.getResources().getProperties().contains("ExtGState")) {
//                isSingleLayer = false;
//                break;
//            }
        }
        // Close the PDF document
        document.close();
        // Print the result
        if (isSingleLayer) {
            System.out.println("The PDF document is single layer.");
        } else {
            System.out.println("The PDF document is multi layer.");
        }
    }

}
