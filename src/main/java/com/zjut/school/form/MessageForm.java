package com.zjut.school.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 张璐杰
 * 2018/4/16 21:26
 */

@Data
public class MessageForm {

    @NotEmpty(message = "班级不能为空")
    private String classId;

    @NotEmpty(message = "发送者不能为空")
    private String sender;

    @NotEmpty(message = "接受者不能为空")
    private String receiver;

    private String message;
}
