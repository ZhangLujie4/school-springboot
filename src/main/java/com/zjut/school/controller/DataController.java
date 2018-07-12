package com.zjut.school.controller;

import com.zjut.school.VO.ResultVO;
import com.zjut.school.constant.DataLocalConstant;
import com.zjut.school.constant.StudentConstant;
import com.zjut.school.constant.TeacherConstant;
import com.zjut.school.dataobject.*;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.service.*;
import com.zjut.school.utils.ExcelUtil;
import com.zjut.school.utils.FileUtil;
import com.zjut.school.utils.ResultVOUtil;
import com.zjut.school.utils.stuComparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/17 1:47
 */

@RestController
@Slf4j
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstituteMajorService instituteMajorService;

    @Autowired
    private LessonService lessonService;

    @Autowired
    private TeacherService teacherService;

    /**
     * 上传文件
     * @param file
     * @param classId
     * @param request
     * @return
     */
    @PostMapping("/data/upload")
    public ResultVO upload(@RequestParam("file") MultipartFile file,
                           @RequestParam("classId") String classId,
                           HttpServletRequest request) {
        if (classId == null || classId.equals("null")) {
            throw new SchoolException(ResultEnum.UPLOAD_FAIL.getCode(),"上传失败，班级编号不能为空");
        }

        //String contentType = file.getContentType();
        //String suffixName = fileName.substring(fileName.lastIndexOf(".");
        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/" + classId + "/";
        try {
            File currentFile = new File(filePath + fileName);
            if(!currentFile.getParentFile().exists()) {
                currentFile.getParentFile().mkdirs();
            }
            file.transferTo(currentFile);
        } catch (Exception e) {
            throw new SchoolException(ResultEnum.UPLOAD_FAIL);
        }

        DataInfo dataInfo = new DataInfo();
        dataInfo.setClassId(classId);
        dataInfo.setDataName(fileName);
        dataInfo.setDataPath(filePath);
        dataService.create(dataInfo);
        return ResultVOUtil.success();
    }

    /**
     * 班级资源列表
     * @param classId
     * @return
     */
    @GetMapping("/data/class/{classId}")
    public ResultVO<DataInfo> dataList(@PathVariable("classId") String classId) {

        List<DataInfo> dataInfoList = dataService.findByClassId(classId);
        return ResultVOUtil.success(dataInfoList);
    }

    /**
     * 下载模板excel
     * @param fileName
     * @param response
     * @return
     */
    @GetMapping("/data/template/{fileName}")
    public ResultVO downloadTemplate(@PathVariable("fileName") String fileName,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        fileName = fileName + ".xlsx";
        String filePath = DataLocalConstant.EXCEL_LOCAL_PATH;
        FileUtil.downloadFile(filePath, fileName, response);
        return ResultVOUtil.success();
    }

    /**
     * 下载文件
     * @param dataId
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/data/download/{dataId}")
    public ResultVO update(@PathVariable("dataId") Integer dataId,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        DataInfo dataInfo = dataService.findById(dataId);

        if (dataInfo == null) {
            throw new SchoolException(ResultEnum.FILE_NOT_EXIST);
        }

        String filePath = dataInfo.getDataPath();
        String fileName = dataInfo.getDataName();
        FileUtil.downloadFile(filePath, fileName, response);
        return ResultVOUtil.success();
    }

    /**
     * 上传专业的excel
     * @param file
     * @return
     */
    @PostMapping("/major/excel")
    public  ResultVO importMajorData(@RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/";
        File excelFile = FileUtil.uploadFile(file, fileName, filePath);

        List<MajorInfo> majorInfoList = instituteMajorService.importMajorData(excelFile);
        for (MajorInfo majorInfo : majorInfoList) {
            majorInfo.setInstituteId(instituteMajorService.getInstituteId(majorInfo.getInstituteId()));
            instituteMajorService.create(majorInfo);
        }
        if (excelFile.delete()) {
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.FILE_DELETE_FAIL);
        }
    }

    /**
     * 上传学院的excel
     * @param file
     * @return
     */
    @PostMapping("/institute/excel")
    public ResultVO importInstituteData(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/";
        File excelFile = FileUtil.uploadFile(file, fileName, filePath);

        List<InstituteInfo> instituteInfoList = instituteMajorService.importInstituteData(excelFile);
        for (InstituteInfo instituteInfo : instituteInfoList) {
            instituteMajorService.create(instituteInfo);
        }
        if (excelFile.delete()) {
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.FILE_DELETE_FAIL);
        }
    }

    /**
     * 上传班级的excel
     * @param file
     * @return
     */
    @PostMapping("/class/excel")
    public ResultVO importClassData(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/";
        File excelFile = FileUtil.uploadFile(file, fileName, filePath);

        List<ClassInfo> classInfoList = lessonService.importClassData(excelFile);
        for (ClassInfo classInfo : classInfoList) {
            String instituteId = instituteMajorService.getInstituteId(classInfo.getInstituteId());
            classInfo.setInstituteId(instituteId);

            String majorId = instituteMajorService.getMajorId(instituteId,classInfo.getMajorId());
            classInfo.setMajorId(majorId);

            classInfo.setClassId(instituteId+majorId+classInfo.getClassNum());
            lessonService.create(classInfo);
        }
        if (excelFile.delete()) {
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.FILE_DELETE_FAIL);
        }
    }

    /**
     * 上传课程的excel
     * @param file
     * @return
     */
    @PostMapping("/lesson/excel")
    public ResultVO importLessonData(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/";
        File excelFile = FileUtil.uploadFile(file, fileName, filePath);

        List<LessonInfo> lessonInfoList = lessonService.importLessonData(excelFile);
        log.info("{}", lessonInfoList);
        for (LessonInfo lessonInfo : lessonInfoList) {
            lessonService.create(lessonInfo);
        }
        if (excelFile.delete()) {
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.FILE_DELETE_FAIL);
        }
    }

    /**
     * 上传学生的excel
     * @param file
     * @return
     */
    @PostMapping("/student/excel")
    public ResultVO importStudentData(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/";
        File excelFile = FileUtil.uploadFile(file, fileName, filePath);

        List<StudentUser> studentUserList = studentService.importData(excelFile);

        for (StudentUser studentUser : studentUserList) {

            String instituteId = instituteMajorService.getInstituteId(studentUser.getInstituteId());
            studentUser.setInstituteId(instituteId);
            String majorId = instituteMajorService.getMajorId(instituteId, studentUser.getMajorId());
            studentUser.setMajorId(majorId);
            studentUser.setStuPassword(StudentConstant.defaultPassword);
            ClassInfo classInfo = instituteMajorService
                    .findClass(studentUser.getClassNum(), majorId, instituteId);
            if (classInfo == null) {
                throw new SchoolException(ResultEnum.CLASSID_ERROR);
            }
            studentUser.setClassId(classInfo.getClassId());
        }

        studentUserList.sort(new stuComparator());

        int num = 0;
        String stuNum = null;
        int n = studentUserList.size();
        StudentUser student = studentUserList.get(0);
        studentService.save(ExcelUtil.setStudent(student, ++num));
        for (int i = 1; i < studentUserList.size(); i++) {
            StudentUser studentUser = studentUserList.get(i);
            if (studentUser.getClassId() != studentUserList.get(i-1).getClassId()) {
                num = 0;
            }
            studentService.save(ExcelUtil.setStudent(studentUser, ++num));
        }

        if (excelFile.delete()) {
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.FILE_DELETE_FAIL);
        }
    }

    /**
     * 上传教师的excel
     * @param file
     * @return
     */
    @PostMapping("/teacher/excel")
    public ResultVO importTeacherData(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = DataLocalConstant.SCHOOL_LOCAL_PATH + "/";
        File excelFile = FileUtil.uploadFile(file, fileName, filePath);
        List<TeacherUser> teacherUserList = teacherService.importData(excelFile);

        for (TeacherUser teacherUser : teacherUserList) {

            String instituteId = instituteMajorService.getInstituteId(teacherUser.getInstituteId());
            teacherUser.setInstituteId(instituteId);
            String teaId = TeacherConstant.prefix + instituteId + teacherUser.getTeaNum();
            teacherUser.setTeaId(teaId);
            String majorId = instituteMajorService.getMajorId(instituteId, teacherUser.getMajorId());
            teacherUser.setMajorId(majorId);
            teacherUser.setTeaPassword(TeacherConstant.defaultPassword);
            ClassInfo classInfo = instituteMajorService
                    .findClass(teacherUser.getClassNum(), majorId, instituteId);
            if (classInfo != null) {
                teacherUser.setClassId(classInfo.getClassId());
            }
            teacherService.save(teacherUser);
        }

        if (excelFile.delete()) {
            return ResultVOUtil.success();
        } else {
            throw new SchoolException(ResultEnum.FILE_DELETE_FAIL);
        }
    }

    /**
     * 学院信息
     * @param classId
     * @return
     */
    @GetMapping("/institute/detail/{classId}")
    public ResultVO<InstituteInfo> instituteInfo(@PathVariable("classId") String classId)
    {
        InstituteInfo instituteInfo = instituteMajorService.findByClass(classId);
        if (instituteInfo == null) {
            return ResultVOUtil.success(new InstituteInfo());
        }
        return ResultVOUtil.success(instituteInfo);
    }

    /**
     * 学院介绍
     * @param instituteId
     * @return
     */
    @GetMapping("/institute/instruction/{instituteId}")
    public ResultVO<InstituteInfo> getInstituteInfo(@PathVariable("instituteId") String instituteId) {
        InstituteInfo instituteInfo = instituteMajorService.findInstitute(instituteId);
        if (instituteInfo == null) {
            return ResultVOUtil.success(new InstituteInfo());
        }
        return ResultVOUtil.success(instituteInfo);
    }
}
