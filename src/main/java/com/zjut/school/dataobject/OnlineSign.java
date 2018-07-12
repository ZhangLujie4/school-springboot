package com.zjut.school.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/4/27 18:06
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class OnlineSign {

    @Id
    private String lessonId;

    @CreatedDate
    private Date createTime;

    private double teaLon;

    private double teaLat;

    private Integer distance;
}
