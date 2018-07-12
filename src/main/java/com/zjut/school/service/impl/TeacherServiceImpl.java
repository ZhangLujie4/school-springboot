package com.zjut.school.service.impl;

import com.zjut.school.dataobject.TeacherUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.respository.TeacherUserRepository;
import com.zjut.school.service.TeacherService;
import com.zjut.school.utils.ExcelUtil;
import com.zjut.school.utils.FormatUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 10:46
 */

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherUserRepository teacherUserRepository;


    @Override
    public TeacherUser get(String username) {
        return teacherUserRepository.findOne(username);
    }

    @Override
    public void create(TeacherUser teacherUser) {
        teacherUserRepository.save(teacherUser);
    }

    @Override
    public void delete(String teaId) {
        teacherUserRepository.delete(teaId);
    }

    @Override
    public Integer instituteTeacherAmount(String teaId) {
        List<TeacherUser> teacherUserList = teacherUserRepository.findByTeaIdLike(teaId+"%");
        if (CollectionUtils.isEmpty(teacherUserList)) {
            return 0;
        }
        return teacherUserList.size();
    }

    @Override
    public void deleteList(List<String> teaIdList) {
        for (String teaId : teaIdList) {
            teacherUserRepository.delete(teaId);
        }
    }

    @Override
    public List<TeacherUser> findAll() {
        return teacherUserRepository.findAll();
    }

    @Override
    public List<TeacherUser> importData(File file) {

        Workbook wb = ExcelUtil.workbookType(file);
        List<TeacherUser> teacherUserList = new ArrayList<>();

        Sheet sheet = wb.getSheetAt(0);//获取到第一张表
        for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
            if (i == 0) {
                continue;
            }
            Row row = sheet.getRow(i);//获得索引为i的行，从不是标题行开始
            try {
                TeacherUser teacherUser = new TeacherUser();
                String teaName = row.getCell(0).getStringCellValue();//不能为空
                teacherUser.setTeaName(teaName);

                if (row.getCell(1) != null) {
                    String teaSex = row.getCell(1).getStringCellValue();
                    teacherUser.setTeaSex(teaSex);//可以为空
                }

                if (row.getCell(2) != null && row.getCell(2).getDateCellValue() != null) {
                    Date date = row.getCell(2).getDateCellValue();
                    String teaBirth = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    teacherUser.setTeaBirth(teaBirth);
                }

                if (row.getCell(3) != null) {
                    Integer c = (int) Math.round(row.getCell(3).getNumericCellValue());
                    String classNum = FormatUtil.FormatTwo(c);
                    teacherUser.setClassNum(classNum);
                }

                String teaNum = FormatUtil.FormatFour(i);
                teacherUser.setTeaNum(teaNum);

                if (row.getCell(4) != null) {
                    String majorId = row.getCell(4).getStringCellValue();
                    teacherUser.setMajorId(majorId);
                }

                String instituteId = row.getCell(5).getStringCellValue();
                teacherUser.setInstituteId(instituteId);

                if (row.getCell(6) != null) {
                    Integer teaYear = (int) Math.round(row.getCell(6).getNumericCellValue());
                    teacherUser.setTeaYear(teaYear);
                }

                if (row.getCell(7) != null) {
                    row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                    String teaPhone = row.getCell(7).getStringCellValue();
                    teacherUser.setTeaPhone(teaPhone);
                }

                if (row.getCell(8) != null) {
                    row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                    String teaEmail = row.getCell(8).getStringCellValue();
                    teacherUser.setTeaEmail(teaEmail);
                }
                teacherUserList.add(teacherUser);
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"表格属性格式不正确");
            }
        }

        return teacherUserList;
    }

    @Override
    public TeacherUser findByClassId(String classId) {
        return teacherUserRepository.findByClassId(classId);
    }

    @Override
    @Transactional
    public void save(TeacherUser teacherUser) {
        teacherUserRepository.save(teacherUser);
    }
}
