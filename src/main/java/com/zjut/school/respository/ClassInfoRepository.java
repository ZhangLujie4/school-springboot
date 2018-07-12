package com.zjut.school.respository;

import com.zjut.school.dataobject.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 3:31
 */
public interface ClassInfoRepository extends JpaRepository<ClassInfo, String> {

    ClassInfo findByClassNumAndMajorIdAndInstituteId(String classNum, String majorId, String instituteId);

    List<ClassInfo> findByMajorIdAndAndInstituteId(String majorId, String instituteId);
}
