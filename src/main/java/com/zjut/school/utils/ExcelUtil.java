package com.zjut.school.utils;

import com.zjut.school.dataobject.StudentUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by 张璐杰
 * 2018/4/17 15:08
 */
public class ExcelUtil {

    public static boolean isExcel2003(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    public static String StringNull(String word) {
        return word==null ? null : word;
    }

    public static Workbook workbookType(File file) {
        Workbook wb = null;
        try {
            if (ExcelUtil.isExcel2007(file.getPath())) {
                wb = new XSSFWorkbook(new FileInputStream(file));
            } else {
                wb = new HSSFWorkbook(new FileInputStream(file));
            }
            return wb;
        } catch (Exception e) {
            throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"不是excel正常格式");
        }
    }

    public static StudentUser setStudent(StudentUser studentUser, Integer index) {
        String stuNum = FormatUtil.FormatTwo(index);
        studentUser.setStuNum(stuNum);
        studentUser.setStuId(studentUser.getStuYear()+studentUser.getInstituteId()
                +studentUser.getClassNum()+stuNum);
        return studentUser;
    }
}
