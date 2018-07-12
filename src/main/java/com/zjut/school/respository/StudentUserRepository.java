package com.zjut.school.respository;

import com.zjut.school.dataobject.StudentUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 3:24
 */
public interface StudentUserRepository extends JpaRepository<StudentUser, String> {

    List<StudentUser> findByClassId(String classId);

    List<StudentUser> findByStuIdIn(List<String> stuIdList);

    List<StudentUser> findByStuIdLike(String stuId);
}
