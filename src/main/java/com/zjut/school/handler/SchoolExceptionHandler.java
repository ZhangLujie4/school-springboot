package com.zjut.school.handler;

import com.zjut.school.VO.ResultVO;
import com.zjut.school.exception.SchoolException;
import com.zjut.school.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 张璐杰
 * 2018/4/16 16:52
 */

@ControllerAdvice
public class SchoolExceptionHandler {

    @ExceptionHandler(value = SchoolException.class)
    @ResponseBody
    public ResultVO handlerSchoolException(SchoolException e) {
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
