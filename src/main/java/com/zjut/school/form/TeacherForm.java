package com.zjut.school.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 张璐杰
 * 2018/6/3 12:50
 */

@Data
public class TeacherForm {

    private String teaId;

    @NotEmpty(message = "教师姓名不为空")
    private String teaName;

    private String teaSex;

    private String teaBirth;

    private String classId;

    private String classNum;

    private String majorId;

    private String teaNum;

    private Integer teaYear;

    @NotEmpty(message = "学院名称不为空")
    private String instituteId;

    private String teaPhone;

    private String teaEmail;
}
