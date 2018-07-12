package com.zjut.school.utils;

import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * Created by 张璐杰
 * 2018/4/18 13:21
 */
@Slf4j
public class FileUtil {


    public static File uploadFile(MultipartFile file, String fileName, String filePath) {

        File excelFile = null;
        try {
            excelFile = new File(filePath + fileName);
            if (!excelFile.getParentFile().exists()) {
                excelFile.getParentFile().mkdirs();
            }
            file.transferTo(excelFile);
        } catch (Exception e) {
            throw new SchoolException(ResultEnum.EXCEL_UPLOAD_FAIL);
        }

        return excelFile;
    }

    public static void downloadFile(String filePath, String fileName, HttpServletResponse response){
        File file = new File(filePath, fileName);
        byte[] buffer = new byte[1024];
        if (file.exists()) {
            response.setContentType("application/force-download"); //设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

            try {
                FileInputStream input = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(input);
                OutputStream out = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    out.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.DOWNLOAD_FAIL);
            }
        } else {
            throw new SchoolException(ResultEnum.DOWNLOAD_FAIL.getCode(), "找不到该文件");
        }
    }
}
