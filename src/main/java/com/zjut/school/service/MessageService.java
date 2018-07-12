package com.zjut.school.service;

import com.zjut.school.VO.MessageVO;
import com.zjut.school.dataobject.MessageInfo;
import com.zjut.school.form.MessageForm;

import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 21:35
 */
public interface MessageService {

    void createMessage(MessageForm messageForm);

    List<MessageVO> sent(String sender);

    List<MessageVO> received(String received, String classId);

    String getName(String id);
}
