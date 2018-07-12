package com.zjut.school.service.impl;

import com.zjut.school.dataobject.DataInfo;
import com.zjut.school.respository.DataInfoRepository;
import com.zjut.school.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/17 12:33
 */

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataInfoRepository dataInfoRepository;

    @Override
    @Transactional
    public void create(DataInfo dataInfo) {
        dataInfoRepository.save(dataInfo);
    }

    @Override
    public DataInfo findById(Integer dataId) {
        return dataInfoRepository.findOne(dataId);
    }

    @Override
    public List<DataInfo> findByClassId(String classId) {
        return dataInfoRepository.findByClassId(classId);
    }
}
