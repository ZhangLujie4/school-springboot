package com.zjut.school.exception;

import com.zjut.school.enums.ResultEnum;
import lombok.Getter;

/**
 * Created by 张璐杰
 * 2018/4/16 11:13
 */

@Getter
public class SchoolException extends RuntimeException {

    private Integer code;

    public SchoolException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SchoolException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
