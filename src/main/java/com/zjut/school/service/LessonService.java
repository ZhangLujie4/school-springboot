package com.zjut.school.service;

import com.zjut.school.dataobject.ClassInfo;
import com.zjut.school.dataobject.LessonInfo;

import java.io.File;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 16:10
 */


public interface LessonService {

    List<LessonInfo> getByTeaId(String teaId);

    List<LessonInfo> getByClassId(String classId);

    List<ClassInfo> importClassData(File file);

    List<LessonInfo> importLessonData(File file);

    void create(LessonInfo lessonInfo);

    void create(ClassInfo classInfo);

    LessonInfo getLessonInfo(String lessonId);

    List<String> getLessonIdList(String lessonNum);

    LessonInfo getByLessonNum(String lessonNum);
}
