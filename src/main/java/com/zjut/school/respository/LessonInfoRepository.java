package com.zjut.school.respository;

import com.zjut.school.dataobject.LessonInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 10:11
 */
public interface LessonInfoRepository extends JpaRepository<LessonInfo, String> {

    List<LessonInfo> findByClassId(String classId);

    List<LessonInfo> findByTeaId(String teaId);

    List<LessonInfo> findByLessonNum(String lessonNum);
}
