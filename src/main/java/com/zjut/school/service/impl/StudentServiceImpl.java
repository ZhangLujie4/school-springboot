package com.zjut.school.service.impl;

import com.zjut.school.constant.TeacherConstant;
import com.zjut.school.dataobject.LessonInfo;
import com.zjut.school.dataobject.StudentUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.respository.LessonInfoRepository;
import com.zjut.school.respository.StudentUserRepository;
import com.zjut.school.service.StudentService;
import com.zjut.school.utils.ExcelUtil;
import com.zjut.school.utils.FormatUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 张璐杰
 * 2018/4/16 13:55
 */

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentUserRepository studentUserRepository;

    @Autowired
    private LessonInfoRepository lessonInfoRepository;

    @Override
    public StudentUser get(String username) {
        return studentUserRepository.findOne(username);
    }

    @Override
    public Integer classStuIdAmount(String stuId) {
        List<StudentUser> studentUserList = studentUserRepository.findByStuIdLike(stuId+"%");
        if (CollectionUtils.isEmpty(studentUserList)) {
            return 0;
        }
        return studentUserList.size();
    }

    @Override
    public List<StudentUser> importData(File file) {

        Workbook wb = ExcelUtil.workbookType(file);
        List<StudentUser> studentUserList = new ArrayList<>();

        Sheet sheet = wb.getSheetAt(0);
        for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
            if (i==0) {
                continue;
            }
            Row row = sheet.getRow(i);
            try {
                StudentUser studentUser = new StudentUser();
                String stuName = row.getCell(0).getStringCellValue();
                studentUser.setStuName(stuName);

                if (row.getCell(1) != null) {
                    String stuSex = row.getCell(1).getStringCellValue();
                    studentUser.setStuSex(stuSex);
                }

                if (row.getCell(2) != null && row.getCell(2).getDateCellValue() != null) {
                    Date date = row.getCell(2).getDateCellValue();
                    String stuBirth = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    studentUser.setStuBirth(stuBirth);
                }

                if (row.getCell(3) != null) {
                    Integer stuYear = (int)Math.round(row.getCell(3).getNumericCellValue());
                    studentUser.setStuYear(stuYear);
                }

                Integer c = (int)Math.round(row.getCell(4).getNumericCellValue());
                String classNum = FormatUtil.FormatTwo(c);
                studentUser.setClassNum(classNum);

                String instituteId = row.getCell(5).getStringCellValue();
                studentUser.setInstituteId(instituteId);

                String majorId = row.getCell(6).getStringCellValue();
                studentUser.setMajorId(majorId);

                studentUserList.add(studentUser);
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"表格属性格式不正确");
            }
        }
        return studentUserList;
    }

    @Override
    public Integer classStudentAmount(String classId) {
        return studentUserRepository.findByClassId(classId).size();
    }

    @Override
    public List<StudentUser> findAll() {
        return studentUserRepository.findAll();
    }

    @Override
    @Transactional
    public void delete(String stuId) {
        studentUserRepository.delete(stuId);
    }

    @Override
    @Transactional
    public void deleteList(List<String> stuIdList) {
        for (String stuId : stuIdList) {
            studentUserRepository.delete(stuId);
        }
    }

    @Override
    public List<StudentUser> getRandomList(String lessonId, int count) {
        LessonInfo lessonInfo = lessonInfoRepository.findOne(lessonId);
        if (lessonInfo == null) {
            return null;
        }
        List<StudentUser> studentUserList = studentUserRepository.findByClassId(lessonInfo.getClassId());
        if (CollectionUtils.isEmpty(studentUserList)) {
            return null;
        }
        if (studentUserList.size() < count) {
            return studentUserList;
        }
        Random random = new Random();
        List<Integer> tempList = new ArrayList<>();
        List<StudentUser> result = new ArrayList<>();
        int temp = 0;
        for (int i=0; i<count; i++) {
            temp = random.nextInt(studentUserList.size());
            if(!tempList.contains(temp)) {
                tempList.add(temp);
                result.add(studentUserList.get(temp));
            } else {
                i--;
            }
        }
        return result;
    }

    @Override
    public List<StudentUser> findByStuIdIn(List<String> stuIdList) {
        return studentUserRepository.findByStuIdIn(stuIdList);
    }

    @Override
    public List<StudentUser> findByClassId(String classId) {
        return studentUserRepository.findByClassId(classId);
    }

    @Override
    @Transactional
    public void save(StudentUser studentUser) {
        studentUserRepository.save(studentUser);
    }
}
