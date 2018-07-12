package com.zjut.school.respository;

import com.zjut.school.dataobject.MajorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 10:12
 */
public interface MajorInfoRepository extends JpaRepository<MajorInfo, String> {

    MajorInfo findByInstituteIdAndMajorName(String instituteId, String majorName);

    List<MajorInfo> findByInstituteId(String instituteId);
}
