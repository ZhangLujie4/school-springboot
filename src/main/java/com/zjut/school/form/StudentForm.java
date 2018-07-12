package com.zjut.school.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 张璐杰
 * 2018/6/3 12:50
 */

@Data
public class StudentForm {

    private String stuId;

    @NotEmpty(message = "学生姓名不能为空")
    private String stuName;

    private String stuSex;

    private String stuBirth;
    
    private Integer stuYear;

    private String stuNum;

    @NotEmpty(message = "班级不能为空")
    private String classId;

    private String classNum;

    @NotEmpty(message = "学院不能为空")
    private String instituteId;

    @NotEmpty(message = "专业不能为空")
    private String majorId;
}
