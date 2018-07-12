package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 2:52
 */
@Data
@Entity
public class TeacherUser {

    @Id
    private String teaId;

    private String teaPassword;

    private String teaName;

    private String teaSex;

    private String teaBirth;

    private String classId;

    private String classNum;

    private String majorId;

    private String teaNum;

    private Integer teaYear;

    private String instituteId;

    private String teaPhone;

    private String teaEmail;

}
