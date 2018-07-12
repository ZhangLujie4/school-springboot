package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 3:01
 */

@Entity
@Data
public class InstituteInfo {

    @Id
    private String instituteId;

    private String instituteName;

    private String description;
}
