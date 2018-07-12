package com.zjut.school.service;

import com.zjut.school.dataobject.TeacherUser;
import com.zjut.school.dto.TeacherDTO;

import java.io.File;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 10:29
 */
public interface TeacherService {

    void create(TeacherUser teacherUser);

    void delete(String teaId);

    void deleteList(List<String> teaIdList);

    List<TeacherUser> findAll();

    //查询教师
    TeacherUser get(String username);

    //更新password
    void save(TeacherUser teacherUser);

    //excel数据导入List
    List<TeacherUser> importData(File file);

    //用classId查教师
    TeacherUser findByClassId(String classId);

    Integer instituteTeacherAmount(String teaId);
}
