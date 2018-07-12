package com.zjut.school.service;

import com.zjut.school.dataobject.StudentUser;

import java.io.File;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 10:30
 */
public interface StudentService {

    StudentUser get(String username);

    void save(StudentUser studentUser);

    void delete(String stuId);

    void deleteList(List<String> stuIdList);

    List<StudentUser> findAll();

    List<StudentUser> importData(File file);

    List<StudentUser> findByClassId(String classId);

    List<StudentUser> findByStuIdIn(List<String> stuIdList);

    Integer classStudentAmount(String classId);

    Integer classStuIdAmount(String stuId);

    List<StudentUser> getRandomList(String lessonId, int count);
}
