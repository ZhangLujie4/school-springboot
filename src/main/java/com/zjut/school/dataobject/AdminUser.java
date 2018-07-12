package com.zjut.school.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by 张璐杰
 * 2018/4/15 2:56
 */

@Data
@Entity
public class AdminUser {

    @Id
    private String username;

    private String password;
}
