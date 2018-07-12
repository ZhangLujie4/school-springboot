package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 3:04
 */
@Entity
@Data
public class ClassInfo {

    @Id
    private String classId;

    private String classNum;

    private String majorId;

    private String instituteId;
}
