package com.zjut.school.dataobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/4/15 3:15
 */

@Entity
@Data
public class SignIn {

    @Id
    private String signId;

    private Date signDate;

    private String lessonId;

    private String lessonNum;

    private String stuName;

    private String stuId;

    private Boolean isSign;
}
