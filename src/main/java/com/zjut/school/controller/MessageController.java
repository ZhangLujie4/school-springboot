package com.zjut.school.controller;

import com.zjut.school.VO.MessageVO;
import com.zjut.school.VO.ResultVO;
import com.zjut.school.dataobject.MessageInfo;
import com.zjut.school.enums.ResultEnum;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.form.MessageForm;
import com.zjut.school.service.MessageService;
import com.zjut.school.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by 张璐杰
 * 2018/4/16 23:07
 */

@RestController
@RequestMapping("/message")
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 发送消息
     * @param messageForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/create")
    public ResultVO createMessage(@Valid MessageForm messageForm,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        if (messageForm.getClassId().equals("null")) {
            throw new SchoolException(ResultEnum.PARAM_ERROR.getCode(), "班级不能为空");
        }

        messageService.createMessage(messageForm);
        return ResultVOUtil.success();
    }

    /**
     * 发件箱
     * @param sender
     * @return
     */
    @GetMapping("/send")
    public ResultVO<List<MessageVO>> send(@RequestParam("sender") String sender) {

        return ResultVOUtil.success(messageService.sent(sender));
    }

    /**
     * 收件箱
     * @param receiver
     * @param classId
     * @return
     */
    @GetMapping("/receive")
    public ResultVO<List<MessageVO>> receive(@RequestParam("receiver") String receiver,
                                               @RequestParam("classId") String classId) {
        return ResultVOUtil.success(messageService.received(receiver, classId));
    }
}
