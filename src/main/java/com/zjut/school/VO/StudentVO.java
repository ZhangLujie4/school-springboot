package com.zjut.school.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by 张璐杰
 * 2018/4/16 13:00
 */

@Data
public class StudentVO {

    @JsonProperty("id")
    private String stuId;

    @JsonProperty("name")
    private String stuName;

    @JsonProperty("sex")
    private String stuSex;

    @JsonProperty("birth")
    private String stuBirth;

    @JsonProperty("year")
    private Integer stuYear;

    @JsonProperty("index")
    private String stuNum;

    private String classId;

    private String classNum;

    @JsonProperty("institute")
    private String instituteId;

    @JsonProperty("instituteName")
    private String instituteName;

    @JsonProperty("major")
    private String majorId;

    @JsonProperty("majorName")
    private String majorName;
}
