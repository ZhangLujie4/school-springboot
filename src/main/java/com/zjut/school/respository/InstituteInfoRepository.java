package com.zjut.school.respository;

import com.zjut.school.dataobject.InstituteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 张璐杰
 * 2018/4/15 10:09
 */
public interface InstituteInfoRepository extends JpaRepository<InstituteInfo, String>{

    InstituteInfo findByInstituteName(String instituteId);
}
