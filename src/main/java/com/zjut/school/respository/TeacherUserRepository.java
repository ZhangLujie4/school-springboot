package com.zjut.school.respository;

import com.zjut.school.dataobject.TeacherUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 10:17
 */
public interface TeacherUserRepository extends JpaRepository<TeacherUser, String> {

    TeacherUser findByClassId(String classId);

    List<TeacherUser> findByTeaIdLike(String teaId);
}
