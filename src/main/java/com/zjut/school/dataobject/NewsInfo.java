package com.zjut.school.dataobject;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/4/15 3:02
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class NewsInfo {

    @Id
    @GeneratedValue
    private Integer newsId;

    private String newsTitle;

    private String newsContent;

    @CreatedDate
    private Date createTime;
}
