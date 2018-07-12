package com.zjut.school.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/4/15 3:20
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class DataInfo {

    @Id
    @GeneratedValue
    private Integer dataId;

    private String dataName;

    private String dataPath;

    private String classId;

    @CreatedDate
    private Date createTime;
}
