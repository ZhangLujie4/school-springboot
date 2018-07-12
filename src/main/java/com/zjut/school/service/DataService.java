package com.zjut.school.service;

import com.zjut.school.dataobject.DataInfo;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/17 12:31
 */
public interface DataService {

    void create(DataInfo dataInfo);

    DataInfo findById(Integer dataId);

    List<DataInfo> findByClassId(String classId);
}
