package com.zjut.school.service.impl;

import com.zjut.school.dataobject.ClassInfo;
import com.zjut.school.dataobject.LessonInfo;
import com.zjut.school.dataobject.TeacherUser;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.respository.ClassInfoRepository;
import com.zjut.school.respository.LessonInfoRepository;
import com.zjut.school.respository.TeacherUserRepository;
import com.zjut.school.service.InstituteMajorService;
import com.zjut.school.service.LessonService;
import com.zjut.school.utils.ExcelUtil;
import com.zjut.school.utils.FormatUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/16 16:11
 */
@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonInfoRepository lessonInfoRepository;

    @Autowired
    private ClassInfoRepository classInfoRepository;

    @Autowired
    private TeacherUserRepository teacherUserRepository;

    @Autowired
    private InstituteMajorService instituteMajorService;

    @Override
    public LessonInfo getLessonInfo(String lessonId) {
        return lessonInfoRepository.findOne(lessonId);
    }


    @Override
    public List<String> getLessonIdList(String lessonNum) {
        return lessonInfoRepository.findByLessonNum(lessonNum)
                .stream().map(e->e.getLessonId()).collect(Collectors.toList());
    }

    @Override
    public List<LessonInfo> getByTeaId(String teaId) {

        List<LessonInfo> lessonInfoList = lessonInfoRepository.findByTeaId(teaId);

        return lessonInfoList;
    }

    @Override
    public List<LessonInfo> getByClassId(String classId) {

        List<LessonInfo> lessonInfoList = lessonInfoRepository.findByClassId(classId);

        if (CollectionUtils.isEmpty(lessonInfoList)) {
            throw new SchoolException(ResultEnum.CLASS_LESSON_EMPTY);
        }

        for (LessonInfo lessonInfo : lessonInfoList) {
            String teaName =
                    teacherUserRepository.findOne(lessonInfo.getTeaId()).getTeaName();
            lessonInfo.setTeaId(teaName);
        }

        return lessonInfoList;
    }

    @Override
    public void create(LessonInfo lessonInfo) {
        lessonInfoRepository.save(lessonInfo);
    }

    @Override
    public void create(ClassInfo classInfo) {
        classInfoRepository.save(classInfo);
    }

    @Override
    public List<ClassInfo> importClassData(File file) {
        Workbook wb = ExcelUtil.workbookType(file);
        Sheet sheet = wb.getSheetAt(0);

        List<ClassInfo> classInfoList = new ArrayList<>();
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            if (i==0) {
                continue;
            }
            Row row = sheet.getRow(i);
            try {
                ClassInfo classInfo = new ClassInfo();
                Integer c = (int)Math.round(row.getCell(0).getNumericCellValue());
                String classNum = FormatUtil.FormatTwo(c);
                classInfo.setClassNum(classNum);

                String majorName = row.getCell(1).getStringCellValue();
                classInfo.setMajorId(majorName);

                String instituteName = row.getCell(2).getStringCellValue();
                classInfo.setInstituteId(instituteName);

                classInfoList.add(classInfo);
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"表格属性格式不正确");
            }
        }
        return classInfoList;
    }

    @Override
    public LessonInfo getByLessonNum(String lessonNum) {
        if (lessonInfoRepository.findByLessonNum(lessonNum) == null) {
            return null;
        }
        return lessonInfoRepository.findByLessonNum(lessonNum).get(0);
    }

    @Override
    public List<LessonInfo> importLessonData(File file) {
        Workbook wb = ExcelUtil.workbookType(file);
        Sheet sheet = wb.getSheetAt(0);

        List<LessonInfo> lessonInfoList = new ArrayList<>();
        for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
            if (i==0) {
                continue;
            }
            Row row = sheet.getRow(i);
            try {
                Integer c = (int)Math.round(row.getCell(0).getNumericCellValue());
                String classNum = FormatUtil.FormatTwo(c);
                String instituteName = row.getCell(2).getStringCellValue();
                String instituteId = instituteMajorService.getInstituteId(instituteName);
                String majorName = row.getCell(1).getStringCellValue();
                String majorId = instituteMajorService.getMajorId(instituteId,majorName);
                String classId = classInfoRepository
                        .findByClassNumAndMajorIdAndInstituteId(classNum,majorId,instituteId)
                        .getClassId();
                Integer lessonWeekday = (int)Math.round(row.getCell(3).getNumericCellValue());
                Integer lessonStart = (int)Math.round(row.getCell(4).getNumericCellValue());
                Integer lessonSeveral = (int)Math.round(row.getCell(5).getNumericCellValue());
                String lessonPlace = row.getCell(6).getStringCellValue();
                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                String teaId = row.getCell(7).getStringCellValue();
                String lessonNum = classId + FormatUtil.subStringFour(teaId);
                String lessonId = lessonNum + lessonWeekday;
                String lessonName = row.getCell(8).getStringCellValue();
                lessonInfoList.add(new LessonInfo(lessonId, lessonNum, classId, lessonWeekday,
                        lessonStart, lessonSeveral, lessonPlace,
                        teaId, lessonName));
            } catch (Exception e) {
                throw new SchoolException(ResultEnum.EXCEL_ERROR.getCode(),"表格属性格式不正确");
            }
        }
        return lessonInfoList;
    }
}
