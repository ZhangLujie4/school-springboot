package com.zjut.school.respository;

import com.zjut.school.dataobject.DataInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/15 10:08
 */
public interface DataInfoRepository extends JpaRepository<DataInfo, Integer>{

    List<DataInfo> findByClassId(String classId);
}
