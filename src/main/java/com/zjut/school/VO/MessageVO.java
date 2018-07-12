package com.zjut.school.VO;

import lombok.Data;

import java.util.Date;

/**
 * Created by 张璐杰
 * 2018/5/17 11:53
 */

@Data
public class MessageVO {

    private Integer messageId;

    private String sender;

    private String senderName;

    private String receiver;

    private String message;

    private String receiverName;

    private String classId;

    private Date createTime;
}
