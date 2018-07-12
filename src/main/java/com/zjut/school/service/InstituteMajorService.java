package com.zjut.school.service;

import com.zjut.school.dataobject.ClassInfo;
import com.zjut.school.dataobject.InstituteInfo;
import com.zjut.school.dataobject.MajorInfo;
import com.zjut.school.dto.ParamDTO;

import java.io.File;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 13:14
 */
public interface InstituteMajorService {

    InstituteInfo findInstitute(String instituteId);

    MajorInfo findMajor(String majorId);

    ClassInfo findClass(String classNum, String major, String institute);

    String getInstituteId(String instituteName);

    String getMajorId(String instituteId, String majorName);

    List<InstituteInfo> importInstituteData(File file);

    List<MajorInfo> importMajorData(File file);

    void create(InstituteInfo instituteInfo);

    void create(MajorInfo majorInfo);

    InstituteInfo findByClass(String classId);

    List<ParamDTO> getSelect();
}
