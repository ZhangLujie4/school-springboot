package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 2:58
 */

@Data
@Entity
public class MajorInfo {

    @Id
    private String majorId;

    private String instituteId;

    private String majorName;
}
