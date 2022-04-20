package com.projectdemo.springbootdemo.upload;

import cn.hutool.core.io.FileUtil;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * asus 梅锦涛
 * 2022/4/20
 *
 * @author mjt
 */
public class ImportOnUpload {

    /**
     *
     * 前端会有一个文件传过来，
     * 导入的时候在整个项目的根目录下会生成一个临时文件，
     *
     * @RequestPart 这个注解用在multipart/form-data表单提交请求的方法上。多用于文件上传场景
     *
     */
    public Map<String, Object> importQuestion(@RequestPart("file")FilePart filePart, ServerWebExchange serverWebExchange) throws IOException {

       Path yqd = Files.createTempFile("yqd", filePart.filename());
       filePart.transferTo(yqd.toFile()).subscribe();
       // 新增数据库的方法
        /*
         需提前准备好的变量
         * bootstrap.yml中的变量key-value方式
            xxx:
                xxxtest: "{questionType:'问题类型',keyword:'关键字'....}"
         *
         *  @Value("#{${xxx.xxxtest}}")
            属性
         *
         * */
          // 伪代码
              /*
              * importResult(File resultFile, ....) {
              *    inputStream inputStream = new FileInputStream(resultFile);
              *   List<Map<String, Object>> maps = Utils.excelToBeanList(inputstream, map, 0);
              *    ....
              *
              *
              *  }
              *
              *
              *
              * */
        FileUtil.del(yqd);
        return null;
    }

}
