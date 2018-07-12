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
 * 2018/4/15 3:17
 */

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class MessageInfo {

    @Id
    @GeneratedValue
    private Integer messageId;

    private String sender;

    private String receiver;

    private String message;

    private String classId;

    @CreatedDate
    private Date createTime;
}
