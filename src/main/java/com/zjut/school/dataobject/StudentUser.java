package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 2:45
 */
@Data
@Entity
public class StudentUser {

    @Id
    private String stuId;

    private String stuPassword;

    private String stuName;

    private String stuSex;

    private String stuBirth;

    private Integer stuYear;

    private String stuNum;

    private String classId;

    private String classNum;

    private String instituteId;

    private String majorId;
}
