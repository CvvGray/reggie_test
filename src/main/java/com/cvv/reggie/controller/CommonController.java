package com.cvv.reggie.controller;

import com.cvv.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: cvv
 * @since: 1.0
 * @version: 1.0
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("/common")

public class CommonController {

    @Value("${reggie.img-path}")
    private String basePath;
    /**
     * 接收从浏览器上传过来的文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){

        String originalFilename = file.getOriginalFilename();

        //截取文件名的后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf('.'));

        //随机生成文件名，并且拼接后缀
        String fileName = UUID.randomUUID().toString() + suffix;

        //动态创建目录
        File dir = new File(basePath);
        if (!dir.exists()){
            dir.mkdirs();
        }

        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return R.success(fileName);
    }


    @GetMapping("/download")
    public void downland(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(basePath + name);
            //输出流，通过输出流将文件写道浏览器，在浏览器中展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1 ){
                outputStream.write(bytes,0,len);
            }

            outputStream.flush();
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }





}
