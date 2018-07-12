package com.zjut.school.service.impl;

import com.zjut.school.VO.MessageVO;
import com.zjut.school.constant.MessageConstant;
import com.zjut.school.dataobject.MessageInfo;
import com.zjut.school.dataobject.StudentUser;
import com.zjut.school.dataobject.TeacherUser;
import com.zjut.school.form.MessageForm;
import com.zjut.school.respository.MessageInfoRepository;
import com.zjut.school.respository.StudentUserRepository;
import com.zjut.school.respository.TeacherUserRepository;
import com.zjut.school.service.MessageService;
import com.zjut.school.service.WebSocket;
import com.zjut.school.utils.msgComparator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 张璐杰
 * 2018/4/16 21:38
 */

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private StudentUserRepository studentUserRepository;

    @Autowired
    private TeacherUserRepository teacherUserRepository;

    @Autowired
    private MessageInfoRepository messageInfoRepository;

    @Autowired
    private WebSocket webSocket;

    @Override
    public String getName(String id) {
        String name = null;
        if (studentUserRepository.findOne(id) != null) {
            name = studentUserRepository.findOne(id).getStuName();
        }
        if (teacherUserRepository.findOne(id) != null) {
            name = teacherUserRepository.findOne(id).getTeaName();
        }
        return name;
    }

    @Override
    public List<MessageVO> sent(String sender) {
        List<MessageVO> messageVOList = messageInfoRepository.findBySender(sender)
                .stream().map(e->{
                    MessageVO messageVO = new MessageVO();
                    BeanUtils.copyProperties(e, messageVO);
                    String name = getName(e.getReceiver());
                    messageVO.setReceiverName(name);
                    return messageVO;
                }).collect(Collectors.toList());
        messageVOList.sort(new msgComparator());
        return messageVOList;
    }

    @Override
    public List<MessageVO> received(String received, String classId) {
        List<MessageInfo> messageInfoList = messageInfoRepository.findByReceiver(received);
        List<MessageInfo> AllMessageList =
                messageInfoRepository.findByReceiverAndClassId(MessageConstant.SEND_TO_ALL, classId);
        messageInfoList.addAll(AllMessageList);
        List<MessageVO> messageVOList = messageInfoList.stream().map(e->{
              MessageVO messageVO = new MessageVO();
              BeanUtils.copyProperties(e, messageVO);
              String name = getName(e.getSender());
              messageVO.setSenderName(name);
              return messageVO;
        }).collect(Collectors.toList());
        messageVOList.sort(new msgComparator());
        return messageVOList;
    }

    @Override
    @Transactional
    public void createMessage(MessageForm messageForm) {

        MessageInfo messageInfo = new MessageInfo();
        BeanUtils.copyProperties(messageForm, messageInfo);
        messageInfoRepository.save(messageInfo);

        String receiver = messageForm.getReceiver();
        String sender = messageForm.getSender();
        String from = "";
        StudentUser studentUser = studentUserRepository.findOne(sender);
        if (studentUser!=null) {
            from = studentUser.getStuName();
        }
        TeacherUser teacherUser = teacherUserRepository.findOne(sender);
        if (teacherUser!=null) {
            from = teacherUser.getTeaName();
        }
        String message = "From "+ from +": "+ messageForm.getMessage();

        if (receiver.equals(MessageConstant.SEND_TO_ALL)) {
            List<String> receivers = studentUserRepository
                    .findByClassId(messageForm.getClassId()).stream()
                    .map(e -> e.getStuId()).collect(Collectors.toList());
            webSocket.sendAll(receivers, message);
        } else {
            webSocket.sendToUser(receiver, message);
        }
    }
}
