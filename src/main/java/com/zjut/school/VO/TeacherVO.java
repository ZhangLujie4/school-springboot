package com.zjut.school.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by 张璐杰
 * 2018/4/16 10:09
 */

@Data
public class TeacherVO {

    @JsonProperty("id")
    private String teaId;

    @JsonProperty("name")
    private String teaName;

    @JsonProperty("sex")
    private String teaSex;

    @JsonProperty("birth")
    private String teaBirth;

    @JsonProperty("index")
    private String teaNum;

    private String classId;

    private String classNum;

    @JsonProperty("major")
    private String majorId;

    @JsonProperty("majorName")
    private String majorName;

    @JsonProperty("year")
    private Integer teaYear;

    @JsonProperty("institute")
    private String instituteId;

    @JsonProperty("instituteName")
    private String instituteName;

    @JsonProperty("phone")
    private String teaPhone;

    @JsonProperty("email")
    private String teaEmail;
}
